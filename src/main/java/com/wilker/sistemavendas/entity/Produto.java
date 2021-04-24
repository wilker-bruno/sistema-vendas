package com.wilker.sistemavendas.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "marca_id")
  private Marca marca;

  @ManyToMany(mappedBy = "produtos")
  private Set<Categoria> categorias;

  @Column(length = 100)
  @NotBlank
  private String nome;

  @Column(name = "preco_custo", precision = 20, scale = 2)
  private BigDecimal precoCusto;

  @Column(name = "preco_venda", precision = 20, scale = 2)
  private BigDecimal precoVenda;

  @Column(name = "quantidade_estoque")
  private Integer quantidadeEstoque;
}
