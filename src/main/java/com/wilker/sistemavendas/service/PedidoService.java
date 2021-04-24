package com.wilker.sistemavendas.service;

import com.wilker.sistemavendas.DTO.PedidoDTO;
import com.wilker.sistemavendas.entity.Pedido;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);
}
