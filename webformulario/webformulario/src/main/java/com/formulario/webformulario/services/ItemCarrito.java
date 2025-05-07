
package com.formulario.webformulario.services;

import com.formulario.webformulario.Model.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}