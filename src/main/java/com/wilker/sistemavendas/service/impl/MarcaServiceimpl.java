package com.wilker.sistemavendas.service.impl;

import com.wilker.sistemavendas.entity.Marca;
import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.exception.errors.NotFoundException;
import com.wilker.sistemavendas.exception.errors.BadRequestException;
import com.wilker.sistemavendas.repository.MarcaRepository;
import com.wilker.sistemavendas.security.CustomUserDetailsService;
import com.wilker.sistemavendas.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarcaServiceimpl implements MarcaService {
    private final MarcaRepository marcaRepository;
    private final CustomUserDetailsService autenticacaoService;

    @Override
    public Marca salvar(Marca marca) {
        List<Marca> marcas = marcaRepository.findByNome(marca.getNome());

        if (!marcas.isEmpty())
            throw new BadRequestException("Já existe uma Marca cadastrada com o mesmo nome");

        return marcaRepository.save(marca);
    }

    @Override
    public Marca recuperarPorID(Integer marcaID) {
        Usuario usuarioLogado = this.autenticacaoService.buscarUsuarioLogado();

        return marcaRepository.findById(marcaID)
                .orElseThrow(() -> new NotFoundException("Não há Marca para o ID informado"));
    }

    @Override
    public Marca excluir(Integer marcaID) {
        Optional<Marca> marca = marcaRepository.findById(marcaID);

        if (!marca.isPresent())
            throw new NotFoundException("Não há Marca para o ID informado");

        marcaRepository.delete(marca.get());
        return marca.get();
    }

    @Override
    public Marca atualizar(Integer marcaID, Marca marca) {
        Optional<Marca> optionalMarca = marcaRepository.findById(marcaID);

        if (!optionalMarca.isPresent())
            throw new NotFoundException("Não há Marca para o ID informado");

        marca.setId(optionalMarca.get().getId());

        return marcaRepository.save(marca);
    }

    @Override
    public Page<Marca> recuperarTodos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return marcaRepository.findAll(pageRequest);
    }

}
