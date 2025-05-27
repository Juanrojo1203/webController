package com.formulario.webformulario.repository;

import com.formulario.webformulario.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JpaRepository ya incluye métodos como save(), findAll(), findById(), deleteById(), etc.
    
    // Métodos personalizados si los necesitas:
    // List<Producto> findByNombre(String nombre);
    // List<Producto> findByPrecioLessThanEqual(int precio);
}
