package com.gestion.productos.service;

import com.gestion.productos.model.Producto;
import com.gestion.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio con la lógica de negocio para las operaciones CRUD de Productos.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene la lista completa de todos los productos.
     */
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su Identificador (ID).
     */
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     */
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Actualiza la información de un producto existente.
     */
    public Producto actualizar(Long id, Producto productoDetalles) {
        // Buscamos el producto existente
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con ID: " + id));

        // Actualizamos los campos
        productoExistente.setNombre(productoDetalles.getNombre());
        productoExistente.setDescripcion(productoDetalles.getDescripcion());
        productoExistente.setCategoria(productoDetalles.getCategoria());
        productoExistente.setPrecio(productoDetalles.getPrecio());
        productoExistente.setStock(productoDetalles.getStock());

        // Guardamos y retornamos el producto actualizado
        return productoRepository.save(productoExistente);
    }

    /**
     * Elimina un producto por su ID.
     */
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el producto con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    /**
     * Busca productos cuyo nombre contenga el término buscado.
     */
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return productoRepository.findAll();
        }
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}

