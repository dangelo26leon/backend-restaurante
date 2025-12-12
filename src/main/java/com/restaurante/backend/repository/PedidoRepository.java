package com.restaurante.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restaurante.backend.models.Pedido;
import com.restaurante.backend.models.enums.EstadoPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByMeseroId(Long meseroId);
    
    List<Pedido> findByMesaId(Long mesaId);
    
    List<Pedido> findByEstado(EstadoPedido estado);
    
    @Query("SELECT p FROM Pedido p WHERE p.estado IN :estados ORDER BY p.fecha ASC")
    List<Pedido> findByEstadoIn(@Param("estados") List<EstadoPedido> estados);
    
    @Query("SELECT p FROM Pedido p WHERE p.estado IN ('PENDIENTE', 'EN_PROCESO') ORDER BY p.fecha ASC")
    List<Pedido> findPedidosPendientesYEnProceso();
    
    List<Pedido> findByMeseroIdAndEstado(Long meseroId, EstadoPedido estado);
}
