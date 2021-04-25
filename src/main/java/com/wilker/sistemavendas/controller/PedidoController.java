package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.dto.PedidoDTO;
import com.wilker.sistemavendas.entity.Pedido;
import com.wilker.sistemavendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido save(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.salvar(pedidoDTO);
    }
}
