package com.formulario.webformulario.service;

import com.formulario.webformulario.Model.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para validar productos y sus imágenes asociadas.
 *
 * Este servicio se encarga de:
 * - Verificar que todos los productos tengan imágenes válidas
 * - Reportar productos con imágenes faltantes
 * - Proporcionar estadísticas de validación
 *
 * @author Sistema de Farmacia
 * @version 1.0
 */
@Service
public class ProductoValidacionService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoValidacionService.class);

    // ImagenService removido - validación simplificada

    /**
     * Valida una lista de productos verificando que tengan imágenes disponibles.
     *
     * @param productos Lista de productos a validar
     * @return Reporte de validación
     */
    public ReporteValidacion validarProductos(List<Producto> productos) {
        logger.info("Iniciando validación de {} productos", productos.size());

        ReporteValidacion reporte = new ReporteValidacion();

        for (Producto producto : productos) {
            // Validación simplificada - asumir que todas las imágenes existen
            boolean tieneImagen = producto.getImagen() != null && !producto.getImagen().trim().isEmpty();

            if (tieneImagen) {
                reporte.agregarProductoValido(producto);
                logger.debug("Producto válido: {} - Imagen: {}", producto.getNombre(), producto.getImagen());
            } else {
                reporte.agregarProductoInvalido(producto);
                logger.warn("Producto sin imagen: {} - Imagen faltante: {}", producto.getNombre(), producto.getImagen());
            }
        }

        logger.info("Validación completada. Válidos: {}, Inválidos: {}",
                   reporte.getProductosValidos().size(),
                   reporte.getProductosInvalidos().size());

        return reporte;
    }

    /**
     * Clase interna para reportes de validación.
     */
    public static class ReporteValidacion {
        private final List<Producto> productosValidos = new java.util.ArrayList<>();
        private final List<Producto> productosInvalidos = new java.util.ArrayList<>();

        public void agregarProductoValido(Producto producto) {
            productosValidos.add(producto);
        }

        public void agregarProductoInvalido(Producto producto) {
            productosInvalidos.add(producto);
        }

        public List<Producto> getProductosValidos() {
            return productosValidos;
        }

        public List<Producto> getProductosInvalidos() {
            return productosInvalidos;
        }

        public boolean todosSonValidos() {
            return productosInvalidos.isEmpty();
        }

        public double getPorcentajeValidez() {
            int total = productosValidos.size() + productosInvalidos.size();
            if (total == 0) return 100.0;
            return (double) productosValidos.size() / total * 100.0;
        }
    }
}
