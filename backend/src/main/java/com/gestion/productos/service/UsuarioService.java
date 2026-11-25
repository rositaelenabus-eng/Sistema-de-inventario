package com.gestion.productos.service;

import com.gestion.productos.dto.LoginRequest;
import com.gestion.productos.dto.LoginResponse;
import com.gestion.productos.model.Usuario;
import com.gestion.productos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Servicio que contiene la lógica de negocio para autenticación y usuarios.
 * Estilo Junior: Claro, directo, sin complejidades innecesarias.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Valida las credenciales de un usuario para iniciar sesión.
     */
    public LoginResponse autenticar(LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(loginRequest.getUsername());

        // Verificar si el usuario existe
        if (usuarioOpt.isEmpty()) {
            return new LoginResponse(false, "El usuario ingresado no existe");
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar si la contraseña coincide (en nivel junior se compara directamente o con hash simple)
        if (!usuario.getPassword().equals(loginRequest.getPassword())) {
            return new LoginResponse(false, "Contraseña incorrecta");
        }

        // Generar un token simple (UUID) para que el frontend lo guarde en localStorage
        String tokenSimulado = UUID.randomUUID().toString();

        return new LoginResponse(
                true,
                "Inicio de sesión exitoso",
                tokenSimulado,
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getRol()
        );
    }

    /**
     * Registra un nuevo usuario en la base de datos si el username no está ocupado.
     */
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El nombre de usuario '" + usuario.getUsername() + "' ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Guarda un usuario directamente (utilizado para precargar datos iniciales).
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}

