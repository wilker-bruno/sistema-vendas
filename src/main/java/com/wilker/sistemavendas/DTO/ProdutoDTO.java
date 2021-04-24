package com.wilker.sistemavendas.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProdutoDTO {
  private Integer marcaId;
  private String nome;
  private BigDecimal precoCusto;
  private BigDecimal precoVenda;
  private Integer quantidadeEstoque;
  private Set<Integer> categorias;
}
