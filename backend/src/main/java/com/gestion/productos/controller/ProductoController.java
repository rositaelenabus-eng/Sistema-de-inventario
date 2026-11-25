package com.gestion.productos.controller;

import com.gestion.productos.model.Producto;
import com.gestion.productos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para el CRUD completo de Productos.
 * Provee los endpoints que consumirá el Frontend Angular.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * 1. READ (Listar todos los productos o filtrar por nombre)
     * GET http://localhost:8080/api/productos
     * GET http://localhost:8080/api/productos?nombre=laptop
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
        }
        return ResponseEntity.ok(productoService.listarTodos());
    }

    /**
     * 2. READ (Obtener un producto específico por ID)
     * GET http://localhost:8080/api/productos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> productoOpt = productoService.obtenerPorId(id);

        if (productoOpt.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Producto con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        return ResponseEntity.ok(productoOpt.get());
    }

    /**
     * 3. CREATE (Crear un nuevo producto)
     * POST http://localhost:8080/api/productos
     */
    @PostMapping
    public ResponseEntity<?> crearProducto(@Valid @RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Error al crear producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * 4. UPDATE (Actualizar un producto existente)
     * PUT http://localhost:8080/api/productos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto productoDetalles) {
        try {
            Producto productoActualizado = productoService.actualizar(id, productoDetalles);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * 5. DELETE (Eliminar un producto)
     * DELETE http://localhost:8080/api/productos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Producto con ID " + id + " eliminado con éxito");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

