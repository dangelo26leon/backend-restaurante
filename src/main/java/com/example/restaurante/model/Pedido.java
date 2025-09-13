package com.example.restaurante.model;

import java.util.List;
import java.time.LocalDateTime;

public class Pedido {
    private int id_pedido;
    private String clienteNombre;
    private List<Plato> platos;
    private double total;
    private String estado;
    private LocalDateTime fechaHora;
    private String direccionEntrega;

    public Pedido() {
    }
    

    public Pedido(int id_pedido, String clienteNombre, List<Plato> platos, double total, String estado, LocalDateTime fechaHora, String direccionEntrega) {
        this.id_pedido = id_pedido;
        this.clienteNombre = clienteNombre;
        this.platos = platos;
        this.total = total;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.direccionEntrega = direccionEntrega;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public List<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(List<Plato> platos) {
        this.platos = platos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }
}
