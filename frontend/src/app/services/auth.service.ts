import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse, UsuarioActual } from '../models/usuario.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // URL base del backend Spring Boot
  private apiUrl = 'http://localhost:8080/api/auth';
  private USER_KEY = 'usuario_sesion';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  /**
   * Envía las credenciales al backend para iniciar sesión.
   * Si es exitoso, guarda los datos en localStorage.
   */
  login(credenciales: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credenciales).pipe(
      tap((res) => {
        if (res.exito && res.token) {
          const usuario: UsuarioActual = {
            username: res.username || '',
            nombreCompleto: res.nombreCompleto || '',
            rol: res.rol || '',
            token: res.token
          };
          localStorage.setItem(this.USER_KEY, JSON.stringify(usuario));
        }
      })
    );
  }

  /**
   * Cierra la sesión del usuario eliminando los datos del localStorage
   * y redirigiendo a la pantalla de login.
   */
  logout(): void {
    localStorage.removeItem(this.USER_KEY);
    this.router.navigate(['/login']);
  }

  /**
   * Verifica si existe una sesión activa en el navegador.
   */
  estaAutenticado(): boolean {
    return localStorage.getItem(this.USER_KEY) !== null;
  }

  /**
   * Obtiene la información del usuario autenticado actualmente.
   */
  obtenerUsuarioActual(): UsuarioActual | null {
    const data = localStorage.getItem(this.USER_KEY);
    if (data) {
      try {
        return JSON.parse(data) as UsuarioActual;
      } catch (e) {
        return null;
      }
    }
    return null;
  }
}

