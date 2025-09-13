package com.example.restaurante.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.restaurante.model.Cliente;
import com.example.restaurante.service.ClienteService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Endpoint para obtener todos los clientes
    @GetMapping("/clientes")
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteService.getAllClientes();
    }
    
    // Endpoint para obtener un cliente aleatorio
    @GetMapping("/clientes/random")
    public Cliente getClienteRandom() {
        return clienteService.getClienteRandom();
    }

    // Endpoint para obtener un cliente por ID
    @GetMapping("/clientes/{id}")
    public Cliente getClienteById(@PathVariable int id) {
        return clienteService.getCliente(id);
    }

    // Endpoint para agregar un nuevo cliente
    @PostMapping("/clientes")
    public void addCliente(@RequestBody Cliente cliente) {
        clienteService.addCliente(cliente);
    }

    //Endpoint para actualizar un cliente
    @PutMapping("/clientes/{id}")
    public void updateCliente(@PathVariable int id, @RequestBody Cliente clienteActualizado) {
        clienteService.updateCliente(id, clienteActualizado);
    }

    //Endpoint para eliminar un cliente
    @DeleteMapping("/clientes/{id}")
    public void deleteCliente(@PathVariable int id) {
        clienteService.deleteCliente(id);
    }
    
}
