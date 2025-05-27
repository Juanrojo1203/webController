package com.formulario.webformulario.Controller;

import com.formulario.webformulario.Model.Producto;
import com.formulario.webformulario.repository.ProductoRepository;
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

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        List<Producto> productos = obtenerProductos();
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
