
package com.formulario.webformulario.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private int precio;
    private String imagen;


public Producto(Long id, String nombre, String descripcion, int precio, String imagen) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.imagen = imagen;
}
}