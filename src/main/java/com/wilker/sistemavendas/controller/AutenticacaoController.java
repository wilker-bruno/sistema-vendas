package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.security.autenticacao.AutenticacaoDTO;
import com.wilker.sistemavendas.security.autenticacao.AutenticacaoForm;
import com.wilker.sistemavendas.security.autenticacao.AutenticacaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {
    @Autowired
    private AutenticacaoServiceImpl autenticacaoService;

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
