package com.formulario.webformulario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.formulario.webformulario.service.CarritoService;
import com.formulario.webformulario.service.ConfirmacionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ConfirmacionService confirmacionService;

    @Autowired
    private CatalogoController catalogoController;

    private static final Logger logger = LoggerFactory.getLogger(CarritoController.class);

    @PostMapping("/carrito/agregar")
    public String agregarProducto(@RequestParam Long id, @RequestParam int cantidad, RedirectAttributes redirectAttributes) {
        try {
            // Verificar que el catálogo no sea nulo y obtener el producto
            catalogoController.obtenerProductos().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .ifPresentOrElse(
                        p -> {
                            carritoService.agregarProducto(p, cantidad);
                            logger.info("✅ Producto agregado al carrito (BD): {} - Cantidad: {}", p.getNombre(), cantidad);
                            redirectAttributes.addFlashAttribute("mensaje", "Producto agregado al carrito exitosamente");
                        },
                        () -> {
                            logger.warn("❌ Producto no encontrado en el catálogo: ID {}", id);
                            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
                        }
                    );
        } catch (Exception e) {
            logger.error("❌ Error al agregar producto al carrito: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al agregar producto al carrito");
        }
        return "redirect:/carrito"; // Redirigir al carrito
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        try {
            // Obtener items del carrito desde la base de datos
            var itemsCarrito = carritoService.obtenerItems();
            var total = carritoService.calcularTotal();
            var cantidadItems = carritoService.contarItems();

            model.addAttribute("carrito", itemsCarrito); // Lista de productos en el carrito
            model.addAttribute("total", total); // Total del carrito
            model.addAttribute("cantidadItems", cantidadItems); // Cantidad de items
            model.addAttribute("carritoVacio", carritoService.estaVacio()); // Si está vacío

            logger.info("🛒 Carrito cargado desde BD: {} items, Total: ${}", cantidadItems, total);

        } catch (Exception e) {
            logger.error("❌ Error al cargar carrito: {}", e.getMessage());
            model.addAttribute("error", "Error al cargar el carrito");
        }

        return "carrito"; // Renderizar la plantilla carrito.html
    }

    @PostMapping("/carrito/actualizar")
    public String actualizarCantidad(@RequestParam Long id, @RequestParam int cantidad, RedirectAttributes redirectAttributes) {
        try {
            carritoService.actualizarCantidad(id, cantidad);
            logger.info("✅ Cantidad actualizada en BD: Producto ID {} - Nueva cantidad: {}", id, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada exitosamente");
        } catch (Exception e) {
            logger.error("❌ Error al actualizar cantidad: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al actualizar cantidad");
        }
        return "redirect:/carrito";
    }

    @PostMapping("/carrito/eliminar")
    public String eliminarProducto(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            carritoService.eliminarProducto(id);
            logger.info("✅ Producto eliminado del carrito (BD): ID {}", id);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado del carrito");
        } catch (Exception e) {
            logger.error("❌ Error al eliminar producto: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar producto");
        }
        return "redirect:/carrito";
    }

    @PostMapping("/pagar")
    public String pagar(Model model, RedirectAttributes redirectAttributes) {
        try {
            // Verificar que el carrito no esté vacío
            if (carritoService.estaVacio()) {
                redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
                return "redirect:/carrito";
            }

            // Confirmar la compra (esto transfiere items del carrito a confirmaciones y vacía el carrito)
            String numeroOrden = confirmacionService.confirmarCompra();

            // Obtener los datos de la confirmación para mostrar
            var confirmaciones = confirmacionService.obtenerConfirmacionPorOrden(numeroOrden);
            var totalOrden = confirmacionService.calcularTotalOrden(numeroOrden);

            // Pasar los datos al modelo
            model.addAttribute("confirmaciones", confirmaciones);
            model.addAttribute("numeroOrden", numeroOrden);
            model.addAttribute("total", totalOrden);
            model.addAttribute("fechaCompra", confirmaciones.get(0).getFechaCompra());

            logger.info("🎉 Compra confirmada exitosamente: Orden {} - Total: ${}", numeroOrden, totalOrden);

            return "confirmacion"; // Renderizar la plantilla confirmacion.html

        } catch (Exception e) {
            logger.error("❌ Error al procesar pago: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al procesar el pago: " + e.getMessage());
            return "redirect:/carrito";
        }
    }

}
