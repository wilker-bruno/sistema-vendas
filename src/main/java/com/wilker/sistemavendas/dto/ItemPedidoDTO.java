package com.wilker.sistemavendas.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTO {
    private Integer produtoId;
    private Integer quantidade;
    private BigDecimal precoUnidade;
    private BigDecimal total;
}
