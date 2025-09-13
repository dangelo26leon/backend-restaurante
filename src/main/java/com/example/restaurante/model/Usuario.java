package com.example.restaurante.model;

public class Usuario {
    private int id_usuario;
    private String nombre;
    private int dni;
    private String email;
    private String password;
    private String rol;
    private int telefono;
    
    public Usuario(int id_usuario, String nombre, int dni, String email, String password, String rol, int telefono) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.telefono = telefono;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    
}
