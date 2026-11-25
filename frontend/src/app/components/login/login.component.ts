import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/usuario.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  // Modelo para el formulario de login
  credenciales: LoginRequest = {
    username: '',
    password: ''
  };

  // Estados de la interfaz
  cargando: boolean = false;
  mensajeError: string = '';
  mostrarPassword: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Si ya tiene sesión activa, redirigir a productos
    if (this.authService.estaAutenticado()) {
      this.router.navigate(['/productos']);
    }
  }

  /**
   * Método que se ejecuta al presionar "Iniciar Sesión"
   */
  iniciarSesion(): void {
    // Validación básica de campos vacíos
    if (!this.credenciales.username.trim() || !this.credenciales.password.trim()) {
      this.mensajeError = 'Por favor ingresa tu usuario y contraseña.';
      return;
    }

    this.cargando = true;
    this.mensajeError = '';

    this.authService.login(this.credenciales).subscribe({
      next: (respuesta) => {
        this.cargando = false;
        if (respuesta.exito) {
          // Redirigir al catálogo de productos
          this.router.navigate(['/productos']);
        } else {
          this.mensajeError = respuesta.mensaje || 'Error en las credenciales';
        }
      },
      error: (err) => {
        this.cargando = false;
        if (err.status === 401 && err.error && err.error.mensaje) {
          this.mensajeError = err.error.mensaje;
        } else {
          this.mensajeError = 'No se pudo conectar con el servidor Spring Boot (¿está encendido en el puerto 8080?).';
        }
      }
    });
  }

  /**
   * Atajos rápidos para probar el login con usuarios predeterminados
   */
  usarCredencial(usuario: string, pass: string): void {
    this.credenciales.username = usuario;
    this.credenciales.password = pass;
    this.mensajeError = '';
  }

  toggleMostrarPassword(): void {
    this.mostrarPassword = !this.mostrarPassword;
  }
}

