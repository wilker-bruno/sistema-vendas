package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.entity.Marca;
import com.wilker.sistemavendas.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {
  @Autowired
  private MarcaService marcaService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Marca save(@Valid @RequestBody Marca marca) {
    return marcaService.salvar(marca);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable Integer id, @RequestBody Marca marca) {
    marcaService.atualizar(id, marca);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Marca getById(@PathVariable Integer id) {
    return marcaService.recuperarPorID(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    marcaService.excluir(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<Marca> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
    return marcaService.recuperarTodos(page, size);
  }
}
