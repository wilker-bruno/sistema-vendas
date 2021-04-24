package com.wilker.sistemavendas.service.impl;

import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.exception.errors.NotFoundException;
import com.wilker.sistemavendas.repository.UsuarioRepository;
import com.wilker.sistemavendas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario atualizar(Integer id, Usuario usuario) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent())
            throw new NotFoundException("Não há Usuário para o ID informado");

        usuario.setId(optionalUsuario.get().getId());

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario recuperarPorId(Integer id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent())
            throw new NotFoundException("Não há Usuário para o ID informado");

        return optionalUsuario.get();
    }
}
