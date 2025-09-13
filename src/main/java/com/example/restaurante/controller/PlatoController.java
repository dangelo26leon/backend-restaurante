package com.example.restaurante.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurante.model.Plato;
import com.example.restaurante.service.PedidoService;
import com.example.restaurante.service.PlatoService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class PlatoController {
    private final PlatoService platoService;

    public PlatoController(PlatoService platoService, PedidoService pedidoService) {
        this.platoService = platoService;
    }

    //Endpoint para obtener todos los platos
    @GetMapping("/platos")
    public List<Plato> obtenerTodosLosPlatos() {
        return platoService.getAllPlatos();
    }

    // Endpoint para obtener un plato aleatorio
    @GetMapping("/platos/random")
    public Plato getPlatoRandom(){
        return platoService.getPlatoRandom();
    }

    // EndPoint para obtener un pedido por ID
    @GetMapping("/platos/{id}")
    public Plato getPlatoById(@RequestParam int id) {
        return platoService.getPlato(id);
    }

    // EndPoint para agregar un nuevo pedido
    @PostMapping("/platos")
    public void addPlato(@RequestBody Plato plato) {
        platoService.addPlato(plato);
    }

    // Endpoint para Actualizar el estado de un pedido
    @PutMapping("platos/{id}")
    public void updatePlato(@PathVariable int id, @RequestBody Plato platoActualizado) {        
        platoService.updatePlato(id, platoActualizado);
    }
    
    // Endopoint para eliminar un pedido
    @DeleteMapping("/platos/{id}")
    public void deletePlato(@PathVariable int id){
        platoService.deletePlato(id);
    } 

    
}
