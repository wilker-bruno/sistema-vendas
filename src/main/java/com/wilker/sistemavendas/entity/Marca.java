package com.wilker.sistemavendas.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "marca")
public class Marca {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, unique = true)
  @NotBlank(message = "O nome da Marca deve conter pelo menos 1 caractere")
  private String nome;

  public Marca() {
  }

  public Marca(String nome) {
    this.nome = nome;
  }

  public Marca(Integer id, String nome) {
    this.id = id;
    this.nome = nome;
  }
}
