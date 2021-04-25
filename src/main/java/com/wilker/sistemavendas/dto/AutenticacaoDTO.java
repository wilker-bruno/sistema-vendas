package com.wilker.sistemavendas.dto;

import lombok.Data;

@Data
public class AutenticacaoDTO {
    private UsuarioDTO usuario;
    private String token;
}
