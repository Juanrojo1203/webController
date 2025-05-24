package com.formulario.webformulario.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración web para el manejo de recursos estáticos.
 *
 * Esta clase configura:
 * - Rutas para servir recursos estáticos (CSS, JS, imágenes)
 * - Cache de recursos para mejorar el rendimiento
 * - Múltiples ubicaciones para imágenes
 *
 * @author Sistema de Farmacia
 * @version 2.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("Configurando manejadores de recursos estáticos...");

        // Configuración principal para recursos estáticos
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600); // Cache por 1 hora

        // Configuración específica para imágenes en /img/
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/", "classpath:/static/css/img/")
                .setCachePeriod(3600);

        // Configuración para CSS
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCachePeriod(3600);

        logger.info("Manejadores de recursos estáticos configurados exitosamente");
    }
}
