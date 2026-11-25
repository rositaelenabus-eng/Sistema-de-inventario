/**
 * Modelos para la autenticación y datos de usuario.
 */
export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  exito: boolean;
  mensaje: string;
  token?: string;
  username?: string;
  nombreCompleto?: string;
  rol?: string;
}

export interface UsuarioActual {
  username: string;
  nombreCompleto: string;
  rol: string;
  token: string;
}

