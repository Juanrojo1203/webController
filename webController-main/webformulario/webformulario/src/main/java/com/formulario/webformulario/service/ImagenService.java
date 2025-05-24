package com.formulario.webformulario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Servicio para gestionar la carga y manejo de imágenes de productos.
 * 
 * Este servicio se encarga de:
 * - Buscar imágenes en múltiples ubicaciones
 * - Manejar diferentes formatos de imagen
 * - Proporcionar fallbacks cuando las imágenes no se encuentran
 * - Logging detallado para depuración
 * 
 * @author Sistema de Farmacia
 * @version 1.0
 */
@Service
public class ImagenService {
    
    private static final Logger logger = LoggerFactory.getLogger(ImagenService.class);
    
    /**
     * Ubicaciones donde buscar las imágenes, en orden de prioridad
     */
    private static final List<String> UBICACIONES_IMAGENES = Arrays.asList(
        "static/css/img/",
        "static/img/"
    );
    
    /**
     * Extensiones de archivo soportadas, en orden de prioridad
     */
    private static final List<String> EXTENSIONES_SOPORTADAS = Arrays.asList(
        ".jpg", ".jpeg", ".png", ".gif"
    );
    
    /**
     * Busca una imagen por su nombre en todas las ubicaciones configuradas.
     * 
     * @param nombreImagen El nombre base de la imagen (sin extensión)
     * @return Resource de la imagen encontrada, o null si no se encuentra
     */
    public Resource buscarImagen(String nombreImagen) {
        logger.info("Buscando imagen: {}", nombreImagen);
        
        // Si el nombre ya incluye extensión, buscar directamente
        if (tieneExtension(nombreImagen)) {
            return buscarImagenConRuta(nombreImagen);
        }
        
        // Buscar con diferentes extensiones
        for (String extension : EXTENSIONES_SOPORTADAS) {
            String nombreCompleto = nombreImagen + extension;
            Resource resource = buscarImagenConRuta(nombreCompleto);
            if (resource != null) {
                logger.info("Imagen encontrada: {} en ubicación: {}", nombreCompleto, resource.getDescription());
                return resource;
            }
        }
        
        logger.warn("Imagen no encontrada: {}", nombreImagen);
        return null;
    }
    
    /**
     * Busca una imagen con ruta completa en todas las ubicaciones.
     * 
     * @param nombreCompleto Nombre completo de la imagen con extensión
     * @return Resource de la imagen encontrada, o null si no se encuentra
     */
    private Resource buscarImagenConRuta(String nombreCompleto) {
        for (String ubicacion : UBICACIONES_IMAGENES) {
            try {
                String rutaCompleta = ubicacion + nombreCompleto;
                Resource resource = new ClassPathResource(rutaCompleta);
                
                if (resource.exists() && resource.isReadable()) {
                    logger.debug("Imagen encontrada en: {}", rutaCompleta);
                    return resource;
                }
            } catch (Exception e) {
                logger.debug("Error al buscar imagen en ubicación: {}{} - {}", ubicacion, nombreCompleto, e.getMessage());
            }
        }
        return null;
    }
    
    /**
     * Verifica si un nombre de archivo tiene extensión.
     * 
     * @param nombreArchivo Nombre del archivo a verificar
     * @return true si tiene extensión, false en caso contrario
     */
    private boolean tieneExtension(String nombreArchivo) {
        return nombreArchivo.contains(".") && 
               EXTENSIONES_SOPORTADAS.stream()
                   .anyMatch(ext -> nombreArchivo.toLowerCase().endsWith(ext));
    }
    
    /**
     * Obtiene el contenido de una imagen como array de bytes.
     * 
     * @param resource Resource de la imagen
     * @return Array de bytes del contenido de la imagen
     * @throws IOException Si hay error al leer la imagen
     */
    public byte[] obtenerContenidoImagen(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }
    
    /**
     * Determina el tipo MIME de una imagen basado en su extensión.
     * 
     * @param nombreArchivo Nombre del archivo
     * @return Tipo MIME correspondiente
     */
    public String obtenerTipoMime(String nombreArchivo) {
        String nombreLower = nombreArchivo.toLowerCase();
        
        if (nombreLower.endsWith(".jpg") || nombreLower.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (nombreLower.endsWith(".png")) {
            return "image/png";
        } else if (nombreLower.endsWith(".gif")) {
            return "image/gif";
        }
        
        // Por defecto, asumir PNG
        return "image/png";
    }
    
    /**
     * Verifica si una imagen existe.
     * 
     * @param nombreImagen Nombre de la imagen a verificar
     * @return true si la imagen existe, false en caso contrario
     */
    public boolean existeImagen(String nombreImagen) {
        return buscarImagen(nombreImagen) != null;
    }
}
