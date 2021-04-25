package com.wilker.sistemavendas.dto;

import com.wilker.sistemavendas.entity.enuns.UsuarioEnum;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nome;
    private String email;
    private UsuarioEnum tipo;

    public UsuarioDTO(Integer id, String nome, String email, UsuarioEnum tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }
}
