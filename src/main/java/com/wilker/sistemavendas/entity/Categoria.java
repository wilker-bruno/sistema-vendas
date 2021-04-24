package com.wilker.sistemavendas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categoria")
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100)
  @NotBlank
  private String nome;

  @JsonIgnore // NÃO CARREGA OS DADOS DOS PRODUTOS
  @ManyToMany
  @JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "categoria_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
  private Set<Produto> produtos;
}
