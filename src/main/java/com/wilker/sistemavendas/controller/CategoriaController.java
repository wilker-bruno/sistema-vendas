package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.entity.Categoria;
import com.wilker.sistemavendas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
  @Autowired
  private CategoriaService categoriaService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Categoria save(@RequestBody Categoria categoria) {
    return categoriaService.salvar(categoria);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable Integer id, @RequestBody Categoria categoria) {
    categoriaService.atualizar(id, categoria);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Categoria getById(@PathVariable Integer id) {
    return categoriaService.recuperarPorID(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<Categoria> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
    return categoriaService.recuperarTodos(page, size);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    categoriaService.excluir(id);
  }

}
