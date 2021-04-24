package com.wilker.sistemavendas.security.autenticacao;

import lombok.Data;

@Data
public class AutenticacaoForm {
    private String email;
    private String senha;
}
