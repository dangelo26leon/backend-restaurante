package com.example.restaurante.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.restaurante.model.Pedido;

@Service
public class PedidoService {
    private List<Pedido> pedidos;

    public PedidoService() {
        pedidos = new ArrayList<>();
        pedidos.add(new Pedido(1, "Juan Perez", new ArrayList<>(), 25.50, "En preparación", null, "Calle Falsa 123"));
    }

    public List<Pedido> getAllPedidos() {
        return pedidos;
    }

    public Optional<Pedido> getPedidoById(int id) {
        return pedidos.stream().filter(p -> p.getId_plato() == id).findFirst();
    }

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public boolean updateEstadoPedido(int id, String nuevoEstado) {
        Optional<Pedido> pedidoOpt = getPedidoById(id);
        if (pedidoOpt.isPresent()) {
            pedidoOpt.get().setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    public boolean deletePedido(int id) {
        return pedidos.removeIf(p -> p.getId_plato() == id);
    }

    public Pedido getPedidoRandom() {
        if (pedidos.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(pedidos.size());
        return pedidos.get(index);
    }
}