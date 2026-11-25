package com.gestion.productos.repository;

import com.gestion.productos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para operaciones con la entidad Usuario.
 * Proporciona métodos CRUD automáticos y consultas personalizadas por convención de nombres.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar un usuario por su nombre de usuario
    Optional<Usuario> findByUsername(String username);

    // Método para verificar si un nombre de usuario ya existe
    boolean existsByUsername(String username);
}

