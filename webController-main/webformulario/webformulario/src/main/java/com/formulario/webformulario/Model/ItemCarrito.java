
package com.formulario.webformulario.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    public int getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}