package com.wilker.sistemavendas.service.impl;

import com.wilker.sistemavendas.entity.Categoria;
import com.wilker.sistemavendas.exception.errors.NotFoundException;
import com.wilker.sistemavendas.exception.errors.BadRequestException;
import com.wilker.sistemavendas.repository.CategoriaRepository;
import com.wilker.sistemavendas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria salvar(Categoria categoria) {
        List<Categoria> categorias = categoriaRepository.findByNome(categoria.getNome());

        if (!categorias.isEmpty())
            throw new BadRequestException("Já existe uma Categoria cadastrada com o mesmo nome");

        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria atualizar(Integer categoriaID, Categoria categoria) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaID);

        if (!optionalCategoria.isPresent())
            throw new NotFoundException("Não há Categoria para o ID informado");

        categoria.setId(optionalCategoria.get().getId());

        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria recuperarPorID(Integer categoriaID) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaID);

        if (!categoria.isPresent())
            throw new NotFoundException("Não há Categoria para o ID informado");

        return categoria.get();
    }

    @Override
    public Page<Categoria> recuperarTodos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return categoriaRepository.findAll(pageRequest);
    }

    @Override
    public Categoria excluir(Integer categoriaID) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaID);

        if (!categoria.isPresent())
            throw new NotFoundException("Não há Categoria para o ID informado");

        categoriaRepository.delete(categoria.get());

        return categoria.get();
    }
}
