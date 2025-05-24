package com.formulario.webformulario.Controller;

import com.formulario.webformulario.service.ImagenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Controlador para manejar la carga y visualización de imágenes de productos.
 *
 * Este controlador proporciona endpoints para:
 * - Servir imágenes de productos desde múltiples ubicaciones
 * - Depuración y listado de imágenes disponibles
 * - Pruebas de funcionamiento del sistema de imágenes
 *
 * @author Sistema de Farmacia
 * @version 2.0
 */
@Controller
public class ImagenController {

    private static final Logger logger = LoggerFactory.getLogger(ImagenController.class);

    @Autowired
    private ImagenService imagenService;

    /**
     * Endpoint principal para servir imágenes de productos.
     *
     * @param nombreImagen Nombre de la imagen solicitada
     * @return ResponseEntity con el contenido de la imagen o error 404
     */
    @GetMapping("/imagen/{nombreImagen}")
    @ResponseBody
    public ResponseEntity<byte[]> getImagen(@PathVariable String nombreImagen) {
        try {
            logger.info("Solicitando imagen: {}", nombreImagen);

            Resource resource = imagenService.buscarImagen(nombreImagen);

            if (resource == null) {
                logger.error("Imagen no encontrada: {}", nombreImagen);
                return ResponseEntity.notFound().build();
            }

            byte[] bytes = imagenService.obtenerContenidoImagen(resource);
            String tipoMime = imagenService.obtenerTipoMime(nombreImagen);

            logger.info("Imagen servida exitosamente: {} ({})", nombreImagen, tipoMime);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", tipoMime);
            headers.add("Cache-Control", "max-age=3600"); // Cache por 1 hora

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            logger.error("Error al cargar la imagen: " + nombreImagen, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint de depuración para listar todas las imágenes disponibles.
     *
     * @return HTML con la lista de imágenes disponibles
     */
    @GetMapping("/debug/imagenes")
    @ResponseBody
    public String listarImagenes() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html><html><head><title>Imágenes Disponibles</title></head><body>");
            sb.append("<h1>Sistema de Imágenes - Diagnóstico</h1>");

            // Listar imágenes en static/css/img/
            sb.append("<h2>Imágenes en static/css/img/</h2>");
            sb.append("<ul>");
            Resource[] resourcesCss = resolver.getResources("classpath:static/css/img/**");
            for (Resource resource : resourcesCss) {
                String filename = resource.getFilename();
                if (filename != null && !filename.isEmpty()) {
                    sb.append("<li>")
                      .append(filename)
                      .append(" - <a href='/imagen/").append(filename.replace(".jpg", "").replace(".png", "")).append("' target='_blank'>Ver imagen</a>")
                      .append("</li>");
                }
            }
            sb.append("</ul>");

            // Listar imágenes en static/img/
            sb.append("<h2>Imágenes en static/img/</h2>");
            sb.append("<ul>");
            Resource[] resourcesImg = resolver.getResources("classpath:static/img/**");
            for (Resource resource : resourcesImg) {
                String filename = resource.getFilename();
                if (filename != null && !filename.isEmpty()) {
                    sb.append("<li>")
                      .append(filename)
                      .append(" - <a href='/imagen/").append(filename.replace(".jpg", "").replace(".png", "")).append("' target='_blank'>Ver imagen</a>")
                      .append("</li>");
                }
            }
            sb.append("</ul>");

            sb.append("</body></html>");
            return sb.toString();
        } catch (IOException e) {
            logger.error("Error al listar imágenes", e);
            return "Error al listar imágenes: " + e.getMessage();
        }
    }

    /**
     * Endpoint de prueba para verificar el funcionamiento del sistema de imágenes.
     *
     * @return HTML con una página de prueba que incluye imágenes
     */
    @GetMapping("/test-imagen")
    @ResponseBody
    public String testImagen() {
        return "<!DOCTYPE html><html><head><title>Prueba de Imágenes</title></head><body>" +
               "<h1>Prueba del Sistema de Imágenes</h1>" +
               "<h2>Logo:</h2>" +
               "<img src='/imagen/LOGO' alt='Logo' style='border: 1px solid #ccc; margin: 10px;'>" +
               "<h2>Productos de prueba:</h2>" +
               "<div style='display: flex; flex-wrap: wrap;'>" +
               "<div style='margin: 10px; text-align: center;'>" +
               "<img src='/imagen/acetaminofen' alt='Acetaminofén' style='width: 100px; height: 100px; border: 1px solid #ccc;'>" +
               "<p>Acetaminofén</p>" +
               "</div>" +
               "<div style='margin: 10px; text-align: center;'>" +
               "<img src='/imagen/ibuprofeno' alt='Ibuprofeno' style='width: 100px; height: 100px; border: 1px solid #ccc;'>" +
               "<p>Ibuprofeno</p>" +
               "</div>" +
               "<div style='margin: 10px; text-align: center;'>" +
               "<img src='/imagen/omeprazol' alt='Omeprazol' style='width: 100px; height: 100px; border: 1px solid #ccc;'>" +
               "<p>Omeprazol</p>" +
               "</div>" +
               "</div>" +
               "<p><strong>Si puedes ver las imágenes de arriba, el sistema está funcionando correctamente.</strong></p>" +
               "<p><a href='/debug/imagenes'>Ver todas las imágenes disponibles</a></p>" +
               "</body></html>";
    }

    /**
     * Endpoint de diagnóstico completo del sistema de imágenes.
     *
     * @return HTML con diagnóstico completo del sistema
     */
    @GetMapping("/diagnostico/sistema")
    @ResponseBody
    public String diagnosticoCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><title>Diagnóstico del Sistema</title>");
        sb.append("<style>body{font-family:Arial,sans-serif;margin:20px;} .ok{color:green;} .error{color:red;} .warning{color:orange;}</style>");
        sb.append("</head><body>");
        sb.append("<h1>Diagnóstico Completo del Sistema de Imágenes</h1>");

        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            // Verificar ubicaciones de imágenes
            sb.append("<h2>Estado de Ubicaciones de Imágenes</h2>");
            sb.append("<ul>");

            Resource[] resourcesCss = resolver.getResources("classpath:static/css/img/**");
            sb.append("<li>static/css/img/: <span class='ok'>").append(resourcesCss.length).append(" archivos encontrados</span></li>");

            Resource[] resourcesImg = resolver.getResources("classpath:static/img/**");
            sb.append("<li>static/img/: <span class='ok'>").append(resourcesImg.length).append(" archivos encontrados</span></li>");

            sb.append("</ul>");

            // Verificar productos específicos
            sb.append("<h2>Verificación de Productos</h2>");
            sb.append("<table border='1' style='border-collapse:collapse;'>");
            sb.append("<tr><th>Producto</th><th>Imagen</th><th>Estado</th><th>Prueba</th></tr>");

            String[] productos = {"acetaminofen", "ibuprofeno", "loratadina", "omeprazol",
                                "amoxicilina", "dolex", "salbutamol", "diclofenaco",
                                "paracetamol", "azitromicina"};

            for (String producto : productos) {
                Resource resource = imagenService.buscarImagen(producto);
                String estado = resource != null ? "<span class='ok'>✓ Encontrada</span>" : "<span class='error'>✗ No encontrada</span>";
                String prueba = "<a href='/imagen/" + producto + "' target='_blank'>Ver imagen</a>";

                sb.append("<tr>");
                sb.append("<td>").append(producto).append("</td>");
                sb.append("<td>").append(producto).append(".*</td>");
                sb.append("<td>").append(estado).append("</td>");
                sb.append("<td>").append(prueba).append("</td>");
                sb.append("</tr>");
            }

            sb.append("</table>");

            // Enlaces útiles
            sb.append("<h2>Enlaces Útiles</h2>");
            sb.append("<ul>");
            sb.append("<li><a href='/test-imagen'>Página de prueba de imágenes</a></li>");
            sb.append("<li><a href='/debug/imagenes'>Lista detallada de imágenes</a></li>");
            sb.append("<li><a href='/catalogo'>Catálogo de productos</a></li>");
            sb.append("</ul>");

        } catch (Exception e) {
            sb.append("<p class='error'>Error durante el diagnóstico: ").append(e.getMessage()).append("</p>");
            logger.error("Error en diagnóstico completo", e);
        }

        sb.append("</body></html>");
        return sb.toString();
    }
}
