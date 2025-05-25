package com.formulario.webformulario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.formulario.webformulario.service.CarritoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CatalogoController catalogoController;

    private static final Logger logger = LoggerFactory.getLogger(CarritoController.class);

    @PostMapping("/carrito/agregar")
    public String agregarProducto(@RequestParam Long id, @RequestParam int cantidad) {
        // Verificar que el catálogo no sea nulo y obtener el producto
        catalogoController.obtenerProductos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                    p -> {
                        carritoService.agregarProducto(p, cantidad);
                        logger.info("Producto agregado al carrito y preparado para confirmación: {}", p.getNombre());
                    },
                    () -> logger.warn("Producto no encontrado en el catálogo")
                );
        return "redirect:/carrito"; // Redirigir al carrito
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        // Cambiar el nombre de la variable para que coincida con la vista
        model.addAttribute("carrito", carritoService.obtenerItems()); // Lista de productos en el carrito
        model.addAttribute("total", carritoService.calcularTotal()); // Total del carrito
        return "carrito"; // Renderizar la plantilla carrito.html
    }

    @PostMapping("/carrito/actualizar")
    public String actualizarCantidad(@RequestParam Long id, @RequestParam int cantidad) {
        carritoService.actualizarCantidad(id, cantidad);
        return "redirect:/carrito";
    }

    @PostMapping("/carrito/eliminar")
    public String eliminarProducto(@RequestParam Long id) {
        carritoService.eliminarProducto(id);
        return "redirect:/carrito";
    }

    @PostMapping("/pagar")
    public String pagar(Model model) {
        // Obtener los items y el total antes de vaciar el carrito
        var items = carritoService.obtenerItemsParaConfirmacion(); // Obtener los productos del carrito para confirmación
        var total = carritoService.calcularTotal(); // Calcular el total del carrito

        // Pasar los datos al modelo
        model.addAttribute("carrito", items); // Lista de productos en el carrito
        model.addAttribute("total", total); // Total del carrito

        // Vaciar el carrito después de procesar el pago
        carritoService.vaciarCarrito();

        return "confirmacion"; // Renderizar la plantilla confirmacion.html
    }

}
