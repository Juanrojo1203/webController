package com.formulario.webformulario.repository;

import com.formulario.webformulario.Model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface farmaciaRepository extends JpaRepository<Producto, Long> {
    // Esta interfaz extiende JpaRepository, lo que permite realizar operaciones de base de datos
    // como guardar, buscar, actualizar y eliminar sin tener que escribir código SQL o JPQL.

    // No es necesario escribir ningún método adicional si solo se usan los básicos (findAll, save, findById, deleteById, etc.).

    // Si se necesitan consultas más complejas, se pueden agregar métodos personalizados aquí.
    // Por ejemplo:
    // List<Producto> findByNombre(String nombre);
    // List<Producto> findByPrecioLessThanEqual(int precio);
}
