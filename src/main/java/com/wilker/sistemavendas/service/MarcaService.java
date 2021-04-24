package com.wilker.sistemavendas.service;

import com.wilker.sistemavendas.entity.Marca;
import org.springframework.data.domain.Page;

public interface MarcaService {
  Marca salvar(Marca marca);

  Marca atualizar(Integer marcaID, Marca marca);

  Marca recuperarPorID(Integer marcaID);

  Page<Marca> recuperarTodos(Integer page, Integer size);

  Marca excluir(Integer marcaID);
}
