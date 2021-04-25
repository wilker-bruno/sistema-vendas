package com.wilker.sistemavendas.dto;

import com.wilker.sistemavendas.entity.enuns.PedidoEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {
    private Integer usuarioId;
    private PedidoEnum tipo;
    private LocalDateTime dataHora;
    private BigDecimal total;
    private List<ItemPedidoDTO> itens;
}
