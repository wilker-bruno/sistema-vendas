package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.dto.ProdutoDTO;
import com.wilker.sistemavendas.entity.Produto;
import com.wilker.sistemavendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.salvar(produtoDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody ProdutoDTO produtoDTO) {
        produtoService.atualizar(id, produtoDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getById(@PathVariable Integer id) {
        return produtoService.recuperarPorID(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Produto> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return produtoService.recuperarTodos(page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        produtoService.excluir(id);
    }
}
