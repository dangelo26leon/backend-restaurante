package com.example.restaurante.model;

import java.time.LocalDate;
import java.util.List;

public class Cliente {
    private int id_cliente;
    private String nombre;
    private String genero;
    private int dni;
    private int edad;
    private LocalDate fecha_nac;
    private int telefono;
    private List<Pedido> pedidos;


    public Cliente(int id_cliente, String nombre, String genero, int dni, int edad, LocalDate fecha_nac, int telefono, List<Pedido> pedidos) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.genero = genero;
        this.dni = dni;
        this.edad = edad;
        this.fecha_nac = fecha_nac;
        this.telefono = telefono;
        this.pedidos = pedidos;
    }


    public int getId_cliente() {
        return id_cliente;
    }


    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getGenero() {
        return genero;
    }


    public void setGenero(String genero) {
        this.genero = genero;
    }


    public int getDni() {
        return dni;
    }


    public void setDni(int dni) {
        this.dni = dni;
    }


    public int getEdad() {
        return edad;
    }


    public void setEdad(int edad) {
        this.edad = edad;
    }


    public LocalDate getFecha_nac() {
        return fecha_nac;
    }


    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }


    public int getTelefono() {
        return telefono;
    }


    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }


    public List<Pedido> getPedidos() {
        return pedidos;
    }


    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    

}
