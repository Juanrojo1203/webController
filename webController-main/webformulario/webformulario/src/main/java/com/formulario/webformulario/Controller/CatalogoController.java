package com.formulario.webformulario.Controller;

import com.formulario.webformulario.Model.Producto;
import com.formulario.webformulario.service.ProductoValidacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controlador para el catálogo de productos de la farmacia.
 *
 * Este controlador maneja:
 * - Visualización del catálogo de productos
 * - Validación de imágenes de productos
 * - Provisión de datos de productos para otros controladores
 *
 * @author Sistema de Farmacia
 * @version 2.0
 */
@Controller
public class CatalogoController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogoController.class);

    @Autowired
    private ProductoValidacionService validacionService;

    private final List<Producto> productos = List.of(
        new Producto(1L, "Acetaminofén", "Caja x 20 tabletas 500mg", 3500, "acetaminofen"),
        new Producto(2L, "Ibuprofeno", "Caja x 10 tabletas 400mg", 4000, "ibuprofeno"),
        new Producto(3L, "Loratadina", "Caja x 10 tabletas 10mg", 4500, "loratadina"),
        new Producto(4L, "Omeprazol", "Caja x 14 cápsulas 20mg", 6000, "omeprazol"),
        new Producto(5L, "Amoxicilina", "Caja x 21 cápsulas 500mg", 7800, "amoxicilina"),
        new Producto(6L, "Dolex Forte", "Caja x 24 tabletas", 9500, "dolex"),
        new Producto(7L, "Salbutamol", "Inhalador 100 dosis", 18000, "salbutamol"),
        new Producto(8L, "Diclofenaco MK", "Caja x 10 tabletas 50mg", 4200, "diclofenaco"),
        new Producto(9L, "Paracetamol", "Caja x 20 tabletas 500mg", 3800, "paracetamol"),
        new Producto(10L, "Azitromicina", "Caja x 3 tabletas 500mg", 9000, "azitromicina")
    );

    public List<Producto> obtenerProductos() {
        return productos;
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        logger.info("Mostrando catálogo con {} productos", productos.size());

        // Validar productos antes de mostrarlos
        ProductoValidacionService.ReporteValidacion reporte = validacionService.validarProductos(productos);

        if (!reporte.todosSonValidos()) {
            logger.warn("Algunos productos no tienen imágenes válidas. Validez: {:.1f}%",
                       reporte.getPorcentajeValidez());
        }

        model.addAttribute("productos", productos);
        return "catalogo";
    }
}
