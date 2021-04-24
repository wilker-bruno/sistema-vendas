package com.wilker.sistemavendas.repository;

import com.wilker.sistemavendas.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
  List<Categoria> findByNome(String nome);
}
