package com.wilker.sistemavendas.DTO;

import com.wilker.sistemavendas.entity.Usuario;
import lombok.Data;

@Data
public class AutenticacaoDTO {
    private UsuarioDTO usuario;
    private String token;
}
