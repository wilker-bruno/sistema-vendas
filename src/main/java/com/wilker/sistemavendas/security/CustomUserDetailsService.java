package com.wilker.sistemavendas.security;

import com.wilker.sistemavendas.DTO.AutenticacaoDTO;
import com.wilker.sistemavendas.DTO.UsuarioDTO;
import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.exception.errors.NotFoundException;
import com.wilker.sistemavendas.exception.errors.UnauthorizedException;
import com.wilker.sistemavendas.form.AutenticacaoForm;
import com.wilker.sistemavendas.repository.UsuarioRepository;
import com.wilker.sistemavendas.security.TokenService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;
    private AuthenticationManager authenticationManager;

    private CustomUserDetailsService(UsuarioRepository usuarioRepository,
                                     TokenService tokenService, @Lazy AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Não há Usuário para o email informado"));
    }

    public AutenticacaoDTO autenticar(AutenticacaoForm form) {
        UsernamePasswordAuthenticationToken dadosLogin
                = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getSenha());

        try {
            authenticationManager.authenticate(dadosLogin);
            return criarAutenticacaoDTO(form.getEmail());
        } catch (Exception e) {
            throw new UnauthorizedException("Usuário não autorizado");
        }
    }

    public Usuario obterUsuario(String token) {
        if (!tokenService.verificarToken(token)) {
            throw new UnauthorizedException("Token inválido");
        }

        return usuarioRepository.findById(tokenService.getUsuarioID(token)).get();
    }

    private AutenticacaoDTO criarAutenticacaoDTO(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).get();

        AutenticacaoDTO dto = new AutenticacaoDTO();

        dto.setUsuario(new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipo()));
        dto.setToken(tokenService.gerarToken(usuario));

        return dto;
    }

    public Usuario buscarUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
