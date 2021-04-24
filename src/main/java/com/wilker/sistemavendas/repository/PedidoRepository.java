package com.wilker.sistemavendas.repository;

import com.wilker.sistemavendas.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
