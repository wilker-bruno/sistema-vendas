package com.wilker.sistemavendas.service;

import com.wilker.sistemavendas.entity.Usuario;

public interface UsuarioService {
    Usuario salvar(Usuario usuario);

    Usuario atualizar(Integer id, Usuario usuario);

    Usuario recuperarPorId(Integer id);
}
