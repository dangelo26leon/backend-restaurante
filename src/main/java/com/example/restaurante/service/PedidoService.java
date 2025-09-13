package com.example.restaurante.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurante.model.Pedido;

@Service
public class PedidoService {
    private List<Pedido> pedidos;
    public PedidoService() {
        pedidos = new ArrayList<>();
        pedidos.add(new Pedido(1, "Dangelo León", new ArrayList<>(), 25.50, "En preparación", null, "Calle Falsa 123"));
        pedidos.add(new Pedido(2, "Andy Gómez", new ArrayList<>(), 40.00, "Entregado", null, "Avenida Siempre Viva 742"));
    }

    // Obtener todos los pedidos
    public List<Pedido> getAllPedidos() {
        return pedidos;
    }

    // Obtener un pedido aleatorio
    public Pedido getPedidoRandom(){
        return pedidos.get((int) (Math.random() * pedidos.size()));
    }

    //Agregar un nuevo pedido
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    // Obtener un pedido por ID
    public Pedido getPedido(int id_pedido){
        for (Pedido pedido : pedidos) {
            if (pedido.getId_pedido() == id_pedido) {
                return pedido;
            }
        }
        return null;
    }

    //Actualizar el estado de un pedido
    public void updatePedido(int id_pedido, Pedido pedidoActualizado) {
       for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getId_pedido() == id_pedido) {
                pedidos.set(i, pedidoActualizado);
                return;
            }
        }
    }

    //Eliminar un pedido
    public void deletePedido(int id_pedido) {
        pedidos.removeIf(pedido -> pedido.getId_pedido() == id_pedido);
    }

}