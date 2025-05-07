package com.formulario.webformulario.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.formulario.webformulario.Model.Producto;

import java.util.List;

@Controller
public class CatalogoController {

    private final List<Producto> productos = List.of(
        new Producto(1L, "Acetaminofén", "Caja x 20 tabletas 500mg", 3500, "acetaminofen.jpg"),
        new Producto(2L, "Ibuprofeno", "Caja x 10 tabletas 400mg", 4000, "ibuprofeno.jpg"),
        new Producto(3L, "Loratadina", "Caja x 10 tabletas 10mg", 4500, "loratadina.jpg"),
        new Producto(4L, "Omeprazol", "Caja x 14 cápsulas 20mg", 6000, "omeprazol.jpg"),
        new Producto(5L, "Amoxicilina", "Caja x 21 cápsulas 500mg", 7800, "amoxicilina.jpg"),
        new Producto(6L, "Dolex Forte", "Caja x 24 tabletas", 9500, "dolex.jpg"),
        new Producto(7L, "Salbutamol", "Inhalador 100 dosis", 18000, "salbutamol.jpg"),
        new Producto(8L, "Diclofenaco MK", "Caja x 10 tabletas 50mg", 4200, "diclofenaco.jpg"),
        new Producto(9L, "Paracetamol", "Caja x 20 tabletas 500mg", 3800, "paracetamol.jpg"),
        new Producto(10L, "Azitromicina", "Caja x 3 tabletas 500mg", 9000, "azitromicina.jpg")
    );

    public List<Producto> obtenerProductos() {
        return productos;
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        model.addAttribute("productos", productos);
        return "catalogo";
    }
}
