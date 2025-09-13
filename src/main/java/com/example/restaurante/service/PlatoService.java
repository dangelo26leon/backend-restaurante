package com.example.restaurante.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurante.model.Plato;

@Service
public class PlatoService {
    private List<Plato> platos;
    public PlatoService(){
        platos = new ArrayList<>();
        platos.add(new Plato(1, "Spaghetti Carbonara", "Pasta con salsa de huevo, queso y panceta", 12.50, true, "Pasta", null));
        platos.add(new Plato(2, "Margherita Pizza", "Pizza con tomate, mozzarella y albahaca", 10.00, true, "Pizza", null));
        platos.add(new Plato(3, "Caesar Salad", "Ensalada con lechuga, pollo, crutones y aderezo César", 8.00, false, "Ensalada", null));
    }

    //Obtener todos los platos
    public List<Plato> getAllPlatos() {
        return platos;
    }

    //Obtener un plato aleatorio
    public Plato getPlatoRandom() {
        return platos.get((int) (Math.random() * platos.size()));
    }

    //Agregar un nuevo plato
    public void addPlato(Plato plato) {
        platos.add(plato);
    }

    //Obtener un plato por ID
    public Plato getPlato(int id_plato){
        for (Plato plato : platos) {
            if (plato.getId_plato() == id_plato) {
                return plato;
            }
        }
        return null;
    }

    //Actualizar un plato
    public void updatePlato(int id_plato, Plato platoActualizado) {
       for (int i = 0; i < platos.size(); i++) {
            if (platos.get(i).getId_plato() == id_plato) {
                platos.set(i, platoActualizado);
                return;
            }
        }
    }

    //Eliminar un plato
    public void deletePlato(int id_plato) {
        platos.removeIf(plato -> plato.getId_plato() == id_plato);
    }
    
}
