package com.formulario.webformulario.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad para representar una compra confirmada en la base de datos.
 * Esta tabla almacena permanentemente las compras realizadas.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "confirmaciones")
public class Confirmacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    
    @Column(name = "precio_unitario", nullable = false)
    private int precioUnitario;
    
    @Column(name = "subtotal", nullable = false)
    private int subtotal;
    
    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;
    
    @Column(name = "numero_orden", nullable = false)
    private String numeroOrden;
    
    // Constructor personalizado para crear confirmación desde ItemCarrito
    public Confirmacion(ItemCarrito itemCarrito, String numeroOrden) {
        this.producto = itemCarrito.getProducto();
        this.cantidad = itemCarrito.getCantidad();
        this.precioUnitario = itemCarrito.getProducto().getPrecio();
        this.subtotal = itemCarrito.getSubtotal();
        this.fechaCompra = LocalDateTime.now();
        this.numeroOrden = numeroOrden;
    }
    
    // Método para calcular subtotal automáticamente
    public int calcularSubtotal() {
        return precioUnitario * cantidad;
    }
}
