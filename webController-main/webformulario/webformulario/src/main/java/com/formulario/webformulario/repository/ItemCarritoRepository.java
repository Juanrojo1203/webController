package com.formulario.webformulario.repository;

import com.formulario.webformulario.Model.ItemCarrito;
import com.formulario.webformulario.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar operaciones CRUD de ItemCarrito en la base de datos.
 * Esta tabla es temporal - se limpia cuando se confirma la compra.
 */
@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {

    /**
     * Buscar un item del carrito por producto
     */
    Optional<ItemCarrito> findByProducto(Producto producto);

    /**
     * Buscar un item del carrito por ID del producto
     */
    @Query("SELECT ic FROM ItemCarrito ic WHERE ic.producto.id = :productoId")
    Optional<ItemCarrito> findByProductoId(@Param("productoId") Long productoId);

    /**
     * Obtener todos los items del carrito ordenados por ID
     */
    List<ItemCarrito> findAllByOrderByIdAsc();

    /**
     * Calcular el total del carrito
     */
    @Query("SELECT COALESCE(SUM(ic.subtotal), 0) FROM ItemCarrito ic")
    int calcularTotalCarrito();

    /**
     * Contar items en el carrito
     */
    @Query("SELECT COUNT(ic) FROM ItemCarrito ic")
    long contarItemsEnCarrito();

    /**
     * Eliminar item por producto ID
     */
    void deleteByProductoId(Long productoId);

    /**
     * Reiniciar AUTO_INCREMENT para que los IDs empiecen desde 1
     */
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE item_carrito AUTO_INCREMENT = 1", nativeQuery = true)
    void reiniciarAutoIncrement();

    /**
     * Vaciar tabla y reiniciar AUTO_INCREMENT usando TRUNCATE (más eficiente)
     */
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE item_carrito", nativeQuery = true)
    void truncarTablaCarrito();
}
