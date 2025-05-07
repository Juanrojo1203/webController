
package com.formulario.webformulario.services;


import org.springframework.stereotype.Service;

import com.formulario.webformulario.Model.Producto;

import java.util.*;

@Service
public class CarritoService {
    private final Map<Long, ItemCarrito> carrito = new HashMap<>();

    public void agregarProducto(Producto producto, int cantidad) {
        carrito.compute(producto.getId(), (id, item) -> {
            if (item == null) {
                return new ItemCarrito(producto, cantidad);
            } else {
                item.setCantidad(item.getCantidad() + cantidad);
                return item;
            }
        });
    }
    
    //Ver carrito
    public ItemCarrito verCarrito(Long id) {
        return carrito.get(id);
    }

    public void eliminarProducto(Long id) {
        carrito.remove(id);
    }

    public void actualizarCantidad(Long id, int cantidad) {
        if (carrito.containsKey(id)) {
            carrito.get(id).setCantidad(cantidad);
        }
    }

    public Collection<ItemCarrito> obtenerItems() {
        return carrito.values();
    }

    public double calcularTotal() {
        return carrito.values().stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }

    public void vaciarCarrito() {
        carrito.clear();
    }
}
