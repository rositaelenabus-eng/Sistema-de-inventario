/**
 * Modelo que representa un Producto en el Frontend.
 * Coincide con la entidad Producto de Spring Boot.
 */
export interface Producto {
  id?: number;
  nombre: string;
  descripcion: string;
  categoria: string;
  precio: number;
  stock: number;
}

