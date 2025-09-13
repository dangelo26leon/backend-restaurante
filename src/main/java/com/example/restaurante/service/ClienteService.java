package com.example.restaurante.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurante.model.Cliente;

@Service
public class ClienteService {
    private List<Cliente> clientes;
    public ClienteService(){
        clientes = new ArrayList<>();
        clientes.add(new Cliente(1, "Juan Pérez", "Masculino", 12345678, 30, null, 987654321));
        clientes.add(new Cliente(2, "María Gómez", "Femenino", 87654321, 25, null, 123456789));
        clientes.add(new Cliente(3, "Carlos López", "Masculino", 11223344, 40, null, 456789123));
    }

    //Obtener todos los clientes
    public List<Cliente> getAllClientes() {
        return clientes;
    }

    //Obtener un plato aleatorio
    public Cliente getClienteRandom() {
        return clientes.get((int) (Math.random() * clientes.size()));
    }

    //Agregar un nuevo cliente
    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    //Obtener un cliente por ID
    public Cliente getCliente(int id_cliente){
        for (Cliente cliente : clientes) {
            if (cliente.getId_cliente() == id_cliente) {
                return cliente;
            }
        }
        return null;
    }

    //Actualizar un cliente
    public void updateCliente(int id_cliente, Cliente clienteActualizado) {
       for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId_cliente() == id_cliente) {
                clientes.set(i, clienteActualizado);
                return;
            }
        }
    }

    //Eliminar un cliente
    public void deleteCliente(int id_cliente) {
        clientes.removeIf(cliente -> cliente.getId_cliente() == id_cliente);
    }

}
