package com.gestion.productos.controller;

import com.gestion.productos.dto.LoginRequest;
import com.gestion.productos.dto.LoginResponse;
import com.gestion.productos.model.Usuario;
import com.gestion.productos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para el Login y Registro de usuarios.
 * Permite que Angular se autentique enviando usuario y contraseña.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para procesar el inicio de sesión.
     * POST http://localhost:8080/api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse respuesta = usuarioService.autenticar(loginRequest);

        if (!respuesta.isExito()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Endpoint para registrar un nuevo usuario (opcional si se desea crear más cuentas).
     * POST http://localhost:8080/api/auth/registro
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                usuario.setRol("USER");
            }
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario registrado con éxito");
            respuesta.put("username", nuevoUsuario.getUsername());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

