package com.formulario.webformulario.repository;

import com.formulario.webformulario.Model.Confirmacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para manejar operaciones CRUD de Confirmacion en la base de datos.
 * Esta tabla almacena permanentemente las compras confirmadas.
 */
@Repository
public interface ConfirmacionRepository extends JpaRepository<Confirmacion, Long> {
    
    /**
     * Buscar confirmaciones por número de orden
     */
    List<Confirmacion> findByNumeroOrden(String numeroOrden);
    
    /**
     * Buscar confirmaciones por rango de fechas
     */
    @Query("SELECT c FROM Confirmacion c WHERE c.fechaCompra BETWEEN :fechaInicio AND :fechaFin ORDER BY c.fechaCompra DESC")
    List<Confirmacion> findByFechaCompraBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                               @Param("fechaFin") LocalDateTime fechaFin);
    
    /**
     * Obtener todas las confirmaciones ordenadas por fecha (más recientes primero)
     */
    List<Confirmacion> findAllByOrderByFechaCompraDesc();
    
    /**
     * Calcular total de ventas por día
     */
    @Query("SELECT COALESCE(SUM(c.subtotal), 0) FROM Confirmacion c WHERE DATE(c.fechaCompra) = DATE(:fecha)")
    int calcularVentasDelDia(@Param("fecha") LocalDateTime fecha);
    
    /**
     * Obtener productos más vendidos
     */
    @Query("SELECT c.producto.nombre, SUM(c.cantidad) as totalVendido FROM Confirmacion c GROUP BY c.producto.id, c.producto.nombre ORDER BY totalVendido DESC")
    List<Object[]> obtenerProductosMasVendidos();
    
    /**
     * Contar total de órdenes
     */
    @Query("SELECT COUNT(DISTINCT c.numeroOrden) FROM Confirmacion c")
    long contarTotalOrdenes();
}
