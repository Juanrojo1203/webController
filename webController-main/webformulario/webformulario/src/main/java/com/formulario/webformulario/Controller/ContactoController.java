package com.formulario.webformulario.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para la página de contactos.
 *
 * @author Sistema de Farmacia
 * @version 1.0
 */
@Controller
public class ContactoController {

    /**
     * Mostrar la página de contactos
     */
    @GetMapping("/contacto")
    public String contacto(Model model) {
        // Información de contacto de la farmacia
        model.addAttribute("nombreFarmacia", "La SuperFarmacia");
        model.addAttribute("direccion", "Av. Salud 123, Ciudad Bienestar");
        model.addAttribute("telefono", "(301) 413-4587");
        model.addAttribute("email", "info@superfarmacia.com");
        model.addAttribute("horario", "Lunes a Sábado de 8:00 a 9:00");
        
        return "contacto";
    }
}
