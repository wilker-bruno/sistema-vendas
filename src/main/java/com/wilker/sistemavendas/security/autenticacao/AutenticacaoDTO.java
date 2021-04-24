package com.wilker.sistemavendas.security.autenticacao;

import com.wilker.sistemavendas.entity.Usuario;
import lombok.Data;

@Data
public class AutenticacaoDTO {
    private Usuario usuario;
    private String token;
}
