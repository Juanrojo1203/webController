package com.formulario.webformulario.service;

import com.formulario.webformulario.Model.Producto;
import com.formulario.webformulario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DataInitializationService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializationService.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay productos en la base de datos
        if (productoRepository.count() == 0) {
            logger.info("🔄 Inicializando base de datos con productos...");
            inicializarProductos();
            logger.info("✅ Base de datos inicializada con {} productos", productoRepository.count());
        } else {
            logger.info("📦 Base de datos ya contiene {} productos", productoRepository.count());
        }
    }

    private void inicializarProductos() {
        // Crear y guardar los productos
        Producto[] productos = {
            new Producto(null, "Acetaminofén", "Caja x 20 tabletas 500mg", 3500, "acetaminofen.jpg"),
            new Producto(null, "Ibuprofeno", "Caja x 10 tabletas 400mg", 4000, "ibuprofeno.jpg"),
            new Producto(null, "Loratadina", "Caja x 10 tabletas 10mg", 4500, "loratadina.jpg"),
            new Producto(null, "Omeprazol", "Caja x 14 cápsulas 20mg", 6000, "omeprazol.jpg"),
            new Producto(null, "Amoxicilina", "Caja x 21 cápsulas 500mg", 7800, "amoxicilina.jpg"),
            new Producto(null, "Dolex Forte", "Caja x 24 tabletas", 9500, "dolex.jpg"),
            new Producto(null, "Salbutamol", "Inhalador 100 dosis", 18000, "salbutamol.jpg"),
            new Producto(null, "Diclofenaco MK", "Caja x 10 tabletas 50mg", 4200, "diclofenaco.jpg"),
            new Producto(null, "Paracetamol", "Caja x 20 tabletas 500mg", 3800, "paracetamol.jpg"),
            new Producto(null, "Azitromicina", "Caja x 3 tabletas 500mg", 9000, "azitromicina.jpg")
        };

        // Guardar todos los productos en la base de datos
        for (Producto producto : productos) {
            productoRepository.save(producto);
            logger.debug("💊 Producto guardado: {}", producto.getNombre());
        }
    }
}
