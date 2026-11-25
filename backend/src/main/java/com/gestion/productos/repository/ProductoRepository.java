package com.gestion.productos.repository;

import com.gestion.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Producto.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos cuyo nombre contenga el texto (ignorando mayúsculas/minúsculas)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Buscar productos por categoría
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}

