package com.formulario.webformulario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formulario.webformulario.Model.Confirmacion;
import com.formulario.webformulario.Model.ItemCarrito;
import com.formulario.webformulario.repository.ConfirmacionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para manejar las confirmaciones de compra.
 * Se encarga de transferir items del carrito a confirmaciones permanentes.
 */
@Service
@Transactional
public class ConfirmacionService {
    
    @Autowired
    private ConfirmacionRepository confirmacionRepository;
    
    @Autowired
    private CarritoService carritoService;

    /**
     * Confirmar compra: transferir items del carrito a confirmaciones y vaciar carrito
     */
    public String confirmarCompra() {
        List<ItemCarrito> itemsCarrito = carritoService.obtenerItemsParaConfirmacion();
        
        if (itemsCarrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede confirmar la compra.");
        }
        
        // Generar número de orden único
        String numeroOrden = generarNumeroOrden();
        
        // Crear confirmaciones para cada item del carrito
        for (ItemCarrito item : itemsCarrito) {
            Confirmacion confirmacion = new Confirmacion(item, numeroOrden);
            confirmacionRepository.save(confirmacion);
        }
        
        // Vaciar el carrito después de confirmar
        carritoService.vaciarCarrito();
        
        return numeroOrden;
    }
    
    /**
     * Generar número de orden único
     */
    private String generarNumeroOrden() {
        LocalDateTime ahora = LocalDateTime.now();
        String timestamp = ahora.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + timestamp + "-" + uuid;
    }
    
    /**
     * Obtener confirmación por número de orden
     */
    public List<Confirmacion> obtenerConfirmacionPorOrden(String numeroOrden) {
        return confirmacionRepository.findByNumeroOrden(numeroOrden);
    }
    
    /**
     * Obtener todas las confirmaciones (historial de compras)
     */
    public List<Confirmacion> obtenerTodasLasConfirmaciones() {
        return confirmacionRepository.findAllByOrderByFechaCompraDesc();
    }
    
    /**
     * Calcular total de una orden específica
     */
    public int calcularTotalOrden(String numeroOrden) {
        List<Confirmacion> confirmaciones = obtenerConfirmacionPorOrden(numeroOrden);
        return confirmaciones.stream()
                .mapToInt(Confirmacion::getSubtotal)
                .sum();
    }
    
    /**
     * Obtener confirmaciones por rango de fechas
     */
    public List<Confirmacion> obtenerConfirmacionesPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return confirmacionRepository.findByFechaCompraBetween(fechaInicio, fechaFin);
    }
    
    /**
     * Calcular ventas del día actual
     */
    public int calcularVentasDelDia() {
        return confirmacionRepository.calcularVentasDelDia(LocalDateTime.now());
    }
    
    /**
     * Obtener productos más vendidos
     */
    public List<Object[]> obtenerProductosMasVendidos() {
        return confirmacionRepository.obtenerProductosMasVendidos();
    }
    
    /**
     * Contar total de órdenes
     */
    public long contarTotalOrdenes() {
        return confirmacionRepository.contarTotalOrdenes();
    }
    
    /**
     * Verificar si existe una orden
     */
    public boolean existeOrden(String numeroOrden) {
        return !confirmacionRepository.findByNumeroOrden(numeroOrden).isEmpty();
    }
}
