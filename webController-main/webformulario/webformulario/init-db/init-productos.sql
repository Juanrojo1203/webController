-- Script para inicializar la tabla de productos en MySQL
-- Este script se ejecuta automáticamente si usas Docker Compose

USE farmacia;

-- Crear tabla productos si no existe
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio INT NOT NULL,
    imagen VARCHAR(255)
);

-- Crear tabla item_carrito si no existe
CREATE TABLE IF NOT EXISTS item_carrito (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    producto_id BIGINT,
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- Insertar productos iniciales (solo si la tabla está vacía)
INSERT INTO productos (nombre, descripcion, precio, imagen) 
SELECT * FROM (
    SELECT 'Acetaminofén' as nombre, 'Caja x 20 tabletas 500mg' as descripcion, 3500 as precio, 'acetaminofen.jpg' as imagen
    UNION ALL SELECT 'Ibuprofeno', 'Caja x 10 tabletas 400mg', 4000, 'ibuprofeno.jpg'
    UNION ALL SELECT 'Loratadina', 'Caja x 10 tabletas 10mg', 4500, 'loratadina.jpg'
    UNION ALL SELECT 'Omeprazol', 'Caja x 14 cápsulas 20mg', 6000, 'omeprazol.jpg'
    UNION ALL SELECT 'Amoxicilina', 'Caja x 21 cápsulas 500mg', 7800, 'amoxicilina.jpg'
    UNION ALL SELECT 'Dolex Forte', 'Caja x 24 tabletas', 9500, 'dolex.jpg'
    UNION ALL SELECT 'Salbutamol', 'Inhalador 100 dosis', 18000, 'salbutamol.jpg'
    UNION ALL SELECT 'Diclofenaco MK', 'Caja x 10 tabletas 50mg', 4200, 'diclofenaco.jpg'
    UNION ALL SELECT 'Paracetamol', 'Caja x 20 tabletas 500mg', 3800, 'paracetamol.jpg'
    UNION ALL SELECT 'Azitromicina', 'Caja x 3 tabletas 500mg', 9000, 'azitromicina.jpg'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM productos LIMIT 1);

-- Mostrar productos insertados
SELECT 'Productos insertados:' as mensaje;
SELECT id, nombre, precio FROM productos;
