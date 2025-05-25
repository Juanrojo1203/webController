package com.formulario.webformulario.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.*;

/**
 * Controlador para manejar la funcionalidad del carrusel de productos destacados
 */
@Controller
public class CarruselController {

    /**
     * Clase interna para representar un slide del carrusel
     */
    public static class SlideCarrusel {
        private String imagen;
        private String titulo;
        private String descripcion;
        private String enlace;
        private boolean activo;

        public SlideCarrusel(String imagen, String titulo, String descripcion, String enlace, boolean activo) {
            this.imagen = imagen;
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.enlace = enlace;
            this.activo = activo;
        }

        // Getters y Setters
        public String getImagen() { return imagen; }
        public void setImagen(String imagen) { this.imagen = imagen; }

        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public String getEnlace() { return enlace; }
        public void setEnlace(String enlace) { this.enlace = enlace; }

        public boolean isActivo() { return activo; }
        public void setActivo(boolean activo) { this.activo = activo; }
    }

    /**
     * Obtiene la lista de slides para el carrusel
     */
    public List<SlideCarrusel> obtenerSlidesCarrusel() {
        List<SlideCarrusel> slides = new ArrayList<>();

        slides.add(new SlideCarrusel(
            "https://images.unsplash.com/photo-1576671081837-49000212a370?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2126&q=80",
            "Medicamentos de Calidad",
            "Amplio catálogo de medicamentos genéricos y de marca",
            "/catalogo",
            true
        ));

        slides.add(new SlideCarrusel(
            "https://images.unsplash.com/photo-1559757148-5c350d0d3c56?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2126&q=80",
            "Vitaminas y Suplementos",
            "Fortalece tu sistema inmunológico",
            "/catalogo",
            false
        ));

        slides.add(new SlideCarrusel(
            "https://images.unsplash.com/photo-1631549916768-4119b2e5f926?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2126&q=80",
            "Cuidado Personal",
            "Productos de higiene para toda la familia",
            "/catalogo",
            false
        ));

        slides.add(new SlideCarrusel(
            "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2126&q=80",
            "Primeros Auxilios",
            "Kit completo para emergencias médicas",
            "/catalogo",
            false
        ));

        slides.add(new SlideCarrusel(
            "https://images.unsplash.com/photo-1585435557343-3b092031d4c1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2126&q=80",
            "Atención Profesional",
            "Farmacéuticos certificados para asesorarte",
            "/catalogo",
            false
        ));

        return slides;
    }

    // Endpoints REST eliminados - No son necesarios para un carrusel simple

    /**
     * Método para agregar slides al modelo (usado por otros controladores)
     */
    public void agregarSlidesAlModelo(Model model) {
        model.addAttribute("slidesCarrusel", obtenerSlidesCarrusel());
        model.addAttribute("totalSlides", obtenerSlidesCarrusel().size());
    }
}
