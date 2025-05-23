package com.formulario.webformulario.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.formulario.webformulario.Model.Item;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private List<Item> items = new ArrayList<>(); // Asegurar que la lista esté correctamente inicializada

    // Mostrar la página principal
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", items);  // Pasar la lista de items a la vista principal
        return "index";  // Página principal
    }



    public String getMethodName(@RequestParam String param) {
        return new String();
    }



    // Eliminar un item de la lista
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        items.remove(id);  // Eliminar el carro de la lista
        return "redirect:/lista";  // Redirigir a la página de lista después de eliminar
    }
}