package com.formulario.webformulario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formulario.webformulario.Model.ItemCarrito;
import com.formulario.webformulario.Model.Producto;
import com.formulario.webformulario.repository.ItemCarritoRepository;

import java.util.*;

/**
 * Servicio para manejar operaciones del carrito de compras.
 * Ahora usa la base de datos para persistir los items del carrito.
 */
@Service
@Transactional
public class CarritoService {

    private static final Logger logger = LoggerFactory.getLogger(CarritoService.class);

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    /**
     * Agregar producto al carrito (base de datos)
     */
    public void agregarProducto(Producto producto, int cantidad) {
        Optional<ItemCarrito> itemExistente = itemCarritoRepository.findByProductoId(producto.getId());

        if (itemExistente.isPresent()) {
            // Si ya existe, actualizar cantidad
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
            item.setSubtotal(item.calcularSubtotal());
            itemCarritoRepository.save(item);
        } else {
            // Si no existe, crear nuevo item
            ItemCarrito nuevoItem = new ItemCarrito(producto, cantidad);
            nuevoItem.setSubtotal(nuevoItem.calcularSubtotal());
            itemCarritoRepository.save(nuevoItem);
        }
    }

    /**
     * Ver item específico del carrito por ID del producto
     */
    public ItemCarrito verCarrito(Long productoId) {
        return itemCarritoRepository.findByProductoId(productoId).orElse(null);
    }

    /**
     * Eliminar producto del carrito por ID del producto
     */
    public void eliminarProducto(Long productoId) {
        itemCarritoRepository.deleteByProductoId(productoId);
    }

    /**
     * Actualizar cantidad de un producto en el carrito
     */
    public void actualizarCantidad(Long productoId, int cantidad) {
        Optional<ItemCarrito> itemOpt = itemCarritoRepository.findByProductoId(productoId);
        if (itemOpt.isPresent()) {
            ItemCarrito item = itemOpt.get();
            if (cantidad <= 0) {
                // Si la cantidad es 0 o negativa, eliminar el item
                itemCarritoRepository.delete(item);
            } else {
                item.setCantidad(cantidad);
                item.setSubtotal(item.calcularSubtotal());
                itemCarritoRepository.save(item);
            }
        }
    }

    /**
     * Obtener todos los items del carrito
     */
    public List<ItemCarrito> obtenerItems() {
        return itemCarritoRepository.findAllByOrderByIdAsc();
    }

    /**
     * Calcular total del carrito
     */
    public int calcularTotal() {
        return itemCarritoRepository.calcularTotalCarrito();
    }

    /**
     * Vaciar completamente el carrito y reiniciar IDs
     */
    public void vaciarCarrito() {
        itemCarritoRepository.deleteAll();
        reiniciarIdsCarrito();
    }

    /**
     * Reiniciar los IDs del carrito para que empiecen desde 1
     */
    public void reiniciarIdsCarrito() {
        try {
            // Solo reiniciar si la tabla está vacía
            if (itemCarritoRepository.count() == 0) {
                itemCarritoRepository.reiniciarAutoIncrement();
                logger.info("🔄 IDs del carrito reiniciados - próximo ID será: 1");
            }
        } catch (Exception e) {
            logger.warn("⚠️ No se pudo reiniciar AUTO_INCREMENT del carrito: {}", e.getMessage());
        }
    }

    /**
     * Vaciar carrito usando TRUNCATE (más eficiente y reinicia IDs automáticamente)
     */
    public void vaciarCarritoConTruncate() {
        try {
            itemCarritoRepository.truncarTablaCarrito();
            logger.info("🗑️ Carrito vaciado con TRUNCATE - IDs reiniciados automáticamente");
        } catch (Exception e) {
            logger.warn("⚠️ Error con TRUNCATE, usando método alternativo: {}", e.getMessage());
            // Fallback al método tradicional
            itemCarritoRepository.deleteAll();
            reiniciarIdsCarrito();
        }
    }

    /**
     * Obtener items para confirmación (copia de los items actuales)
     */
    public List<ItemCarrito> obtenerItemsParaConfirmacion() {
        return new ArrayList<>(itemCarritoRepository.findAllByOrderByIdAsc());
    }

    /**
     * Contar items en el carrito
     */
    public long contarItems() {
        return itemCarritoRepository.contarItemsEnCarrito();
    }

    /**
     * Verificar si el carrito está vacío
     */
    public boolean estaVacio() {
        return contarItems() == 0;
    }
}
