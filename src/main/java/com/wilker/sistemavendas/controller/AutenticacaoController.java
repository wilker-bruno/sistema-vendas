package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.dto.AutenticacaoDTO;
import com.wilker.sistemavendas.form.AutenticacaoForm;
import com.wilker.sistemavendas.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {
    @Autowired
    private CustomUserDetailsService autenticacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AutenticacaoDTO autenticar(@RequestBody AutenticacaoForm autenticacaoForm) {
        return autenticacaoService.autenticar(autenticacaoForm);
    }

    @GetMapping("/{token}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario obterUsuario(@PathVariable String token) {
        return autenticacaoService.obterUsuario(token);
    }
}
