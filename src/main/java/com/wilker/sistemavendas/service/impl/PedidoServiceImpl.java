package com.wilker.sistemavendas.service.impl;

import com.wilker.sistemavendas.DTO.ItemPedidoDTO;
import com.wilker.sistemavendas.DTO.PedidoDTO;
import com.wilker.sistemavendas.entity.ItemPedido;
import com.wilker.sistemavendas.entity.Pedido;
import com.wilker.sistemavendas.entity.Produto;
import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.exception.errors.NotFoundException;
import com.wilker.sistemavendas.exception.errors.BadRequestException;
import com.wilker.sistemavendas.repository.ItemPedidoRepository;
import com.wilker.sistemavendas.repository.PedidoRepository;
import com.wilker.sistemavendas.repository.ProdutoRepository;
import com.wilker.sistemavendas.repository.UsuarioRepository;
import com.wilker.sistemavendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Usuario usuario = usuarioRepository.findById(pedidoDTO.getUsuarioId()).orElseThrow(() -> new NotFoundException("Não há Usuário para o ID informado"));

        Pedido pedido = new Pedido();
        pedido.setTipo(pedidoDTO.getTipo());
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataHora(pedidoDTO.getDataHora());
        pedido.setUsuario(usuario);

        List<ItemPedido> itens = itemPedidoFromDTO(pedido, pedidoDTO.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itens);
        pedido.setItens(itens);

        return pedido;
    }

    private List<ItemPedido> itemPedidoFromDTO(Pedido pedido, List<ItemPedidoDTO> itensDTO) {
        if (itensDTO.isEmpty()) throw new BadRequestException("Não é possível realizar um pedido sem itens.");

        return itensDTO.stream().map(itemPedidoDTO -> {
            Produto produto = produtoRepository.findById(itemPedidoDTO.getProdutoId()).orElseThrow(() -> new NotFoundException("Não há Produto para o ID informado"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setTotal(itemPedidoDTO.getTotal());
            itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
            itemPedido.setPrecoUnidade(itemPedidoDTO.getPrecoUnidade());
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
