package com.formulario.webformulario.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ImagenController {

    private static final Logger logger = LoggerFactory.getLogger(ImagenController.class);

    @GetMapping("/imagen/{nombreImagen}")
    @ResponseBody
    public ResponseEntity<byte[]> getImagen(@PathVariable String nombreImagen) {
        try {
            logger.info("Solicitando imagen: {}", nombreImagen);
            Resource resource = new ClassPathResource("static/img/" + nombreImagen);

            if (!resource.exists()) {
                logger.error("Imagen no encontrada: {}", nombreImagen);
                return ResponseEntity.notFound().build();
            }

            byte[] bytes = Files.readAllBytes(resource.getFile().toPath());

            // Determinar el tipo de contenido basado en la extensión del archivo
            MediaType mediaType = MediaType.IMAGE_PNG;
            if (nombreImagen.toLowerCase().endsWith(".jpg") || nombreImagen.toLowerCase().endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (nombreImagen.toLowerCase().endsWith(".gif")) {
                mediaType = MediaType.IMAGE_GIF;
            }

            logger.info("Imagen encontrada: {} con tipo: {}", nombreImagen, mediaType);
            return ResponseEntity
                    .ok()
                    .contentType(mediaType)
                    .body(bytes);
        } catch (IOException e) {
            logger.error("Error al cargar la imagen: " + nombreImagen, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/debug/imagenes")
    @ResponseBody
    public String listarImagenes() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:static/img/**");

            StringBuilder sb = new StringBuilder();
            sb.append("<h1>Imágenes disponibles</h1>");
            sb.append("<ul>");

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename != null) {
                    sb.append("<li>")
                      .append(filename)
                      .append(" - <a href='/imagen/").append(filename).append("' target='_blank'>Ver imagen</a>")
                      .append("</li>");
                }
            }

            sb.append("</ul>");
            return sb.toString();
        } catch (IOException e) {
            logger.error("Error al listar imágenes", e);
            return "Error al listar imágenes: " + e.getMessage();
        }
    }

    @GetMapping("/test-imagen")
    @ResponseBody
    public String testImagen() {
        return "<html><body>" +
               "<h1>Prueba de imagen</h1>" +
               "<img src='/imagen/LOGO.png' alt='Logo'>" +
               "<p>Si puedes ver la imagen de arriba, la configuración está funcionando correctamente.</p>" +
               "</body></html>";
    }
}
