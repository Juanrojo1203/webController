package com.formulario.webformulario.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.formulario.webformulario.services.CarritoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ConfirmacionController {

    @Autowired
    private CarritoService carritoService;

    private static final Logger logger = LoggerFactory.getLogger(ConfirmacionController.class);

    @GetMapping("/confirmacion")
    public String verConfirmacion(Model model) {
        var items = carritoService.obtenerItemsParaConfirmacion(); // Use obtenerItemsParaConfirmacion instead of obtenerItems
        var total = carritoService.calcularTotal();

        logger.info("Items for confirmation: {}", items);
        logger.info("Total amount: {}", total);
        logger.info("Items for confirmation (detailed):");
        items.forEach(item -> logger.info("Producto: {}, Cantidad: {}, Precio: {}",
            item.getProducto().getNombre(), item.getCantidad(), item.getProducto().getPrecio()));

        model.addAttribute("carrito", items); // Lista de productos en el carrito
        model.addAttribute("total", total); // Total del carrito
        return "confirmacion"; // Renderizar la plantilla confirmacion.html
    }
}
