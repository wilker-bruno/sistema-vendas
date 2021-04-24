package com.wilker.sistemavendas.service;

import com.wilker.sistemavendas.entity.Categoria;
import org.springframework.data.domain.Page;

public interface CategoriaService {
  Categoria salvar(Categoria categoria);

  Categoria atualizar(Integer categoriaID, Categoria categoria);

  Categoria recuperarPorID(Integer categoriaID);

  Page<Categoria> recuperarTodos(Integer page, Integer size);

  Categoria excluir(Integer categoriaID);
}
