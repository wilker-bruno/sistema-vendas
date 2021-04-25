package com.wilker.sistemavendas.service;

import com.wilker.sistemavendas.dto.ProdutoDTO;
import com.wilker.sistemavendas.entity.Produto;
import org.springframework.data.domain.Page;

public interface ProdutoService {
  Produto salvar(ProdutoDTO produtoDTO);

  Produto atualizar(Integer id, ProdutoDTO produtoDTO);

  Produto recuperarPorID(Integer produtoID);

  Page<Produto> recuperarTodos(Integer page, Integer size);

  Produto excluir(Integer produtoID);
}
