package com.example.restaurante.controller;

import org.springframework.web.bind.annotation.*;
import com.example.restaurante.service.PedidoService;
import com.example.restaurante.model.Pedido;
import java.util.List;

@RestController
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    //Endpoint para obtener todos los pedidos
    @GetMapping("/pedidos")
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoService.getAllPedidos();
    }

    // Endpoint para obtener un pedido aleatorio
    @GetMapping("/pedidos/random")
    public Pedido getPedidoRandom() {
        return pedidoService.getPedidoRandom();
    }

    // Endpoint para obtener un pedido por ID
    @GetMapping("/pedidos/{id}")
    public Pedido getPedidoById(@PathVariable int id) {
        return pedidoService.getPedido(id);
    }

    // Endpoint para agregar un nuevo pedido
    @PostMapping("/pedidos")
    public void addPedido(@RequestBody Pedido pedido) {
        pedidoService.addPedido(pedido);
    }


    

    // Endpoint para actualizar el estado de un pedido
    @PutMapping("/pedidos/{id}")
    public void updatePedido(@PathVariable int id, @RequestBody Pedido pedidoActualizado) {
        pedidoService.updatePedido(id, pedidoActualizado);
    }

    // Endpoint para eliminar un pedido
    @DeleteMapping("/pedidos/{id}")
    public void deletePedido(@PathVariable int id) {
        pedidoService.deletePedido(id);
    }
}
