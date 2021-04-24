package com.wilker.sistemavendas.repository;

import com.wilker.sistemavendas.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
  List<Marca> findByNome(String nome);
}
