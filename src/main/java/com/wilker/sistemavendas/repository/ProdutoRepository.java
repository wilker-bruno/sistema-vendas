package com.wilker.sistemavendas.repository;

import com.wilker.sistemavendas.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
