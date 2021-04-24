package com.wilker.sistemavendas.service.impl;

import com.wilker.sistemavendas.DTO.ProdutoDTO;
import com.wilker.sistemavendas.entity.Categoria;
import com.wilker.sistemavendas.entity.Marca;
import com.wilker.sistemavendas.entity.Produto;
import com.wilker.sistemavendas.exception.errors.NotFoundException;
import com.wilker.sistemavendas.repository.CategoriaRepository;
import com.wilker.sistemavendas.repository.MarcaRepository;
import com.wilker.sistemavendas.repository.ProdutoRepository;
import com.wilker.sistemavendas.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {
    private final MarcaRepository marcaRepository;
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Produto salvar(ProdutoDTO produtoDTO) {
        Marca marca = marcaRepository.findById(produtoDTO.getMarcaId())
                .orElseThrow(() -> new NotFoundException("Não há marca para o ID informado"));

        Set<Categoria> categorias = new HashSet<>();

        Produto produto = new Produto();
        for (Integer id : produtoDTO.getCategorias()) {
            Optional<Categoria> categoria = categoriaRepository.findById(id);

            if (!categoria.isPresent())
                throw new NotFoundException("Não há Categoria para o ID informado");

            Categoria c = categoria.get();
            c.getProdutos().add(produto);

            categorias.add(categoria.get());
        }

        produto.setMarca(marca);
        produto.setCategorias(categorias);
        produto.setNome(produtoDTO.getNome());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());

        return produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public Produto excluir(Integer produtoID) {
        Optional<Produto> optionalProduto = produtoRepository.findById(produtoID);

        if (!optionalProduto.isPresent())
            throw new NotFoundException("Não há Produto para o ID informado");

        Produto produto = optionalProduto.get();
        for (Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }

        produtoRepository.delete(optionalProduto.get());

        return produto;
    }

    @Override
    public Produto recuperarPorID(Integer produtoID) {
        Optional<Produto> optionalProduto = produtoRepository.findById(produtoID);

        if (!optionalProduto.isPresent())
            throw new NotFoundException("Não há Produto para o ID informado");

        return optionalProduto.get();
    }

    @Override
    public Page<Produto> recuperarTodos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return produtoRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public Produto atualizar(Integer id, ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não há Produto para o ID informado"));

        Marca marca = marcaRepository.findById(produtoDTO.getMarcaId())
                .orElseThrow(() -> new NotFoundException("Não há marca para o ID informado"));

        Set<Categoria> categorias = new HashSet<>();
        for (Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }

        for (Integer categoriaID : produtoDTO.getCategorias()) {
            Optional<Categoria> categoria = categoriaRepository.findById(categoriaID);

            if (!categoria.isPresent())
                throw new NotFoundException("Não há Categoria para o ID informado");

            Categoria c = categoria.get();
            c.getProdutos().add(produto);

            categorias.add(c);
        }

        produto.setMarca(marca);
        produto.setCategorias(categorias);
        produto.setNome(produtoDTO.getNome());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());

        return produtoRepository.save(produto);
    }
}
