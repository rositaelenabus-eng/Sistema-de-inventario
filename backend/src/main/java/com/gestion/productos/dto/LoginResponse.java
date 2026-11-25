package com.gestion.productos.dto;

/**
 * Objeto para responder al frontend Angular cuando el login es exitoso o fallido.
 */
public class LoginResponse {

    private boolean exito;
    private String mensaje;
    private String token; // Token simulado o identificador de sesión para Junior
    private String username;
    private String nombreCompleto;
    private String rol;

    public LoginResponse() {
    }

    public LoginResponse(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public LoginResponse(boolean exito, String mensaje, String token, String username, String nombreCompleto, String rol) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.token = token;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

