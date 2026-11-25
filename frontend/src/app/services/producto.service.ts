import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto.model';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  // URL base del controlador de productos en Spring Boot
  private apiUrl = 'http://localhost:8080/api/productos';

  constructor(private http: HttpClient) {}

  /**
   * 1. GET: Obtener todos los productos o buscar por nombre
   */
  obtenerProductos(filtroNombre?: string): Observable<Producto[]> {
    let params = new HttpParams();
    if (filtroNombre && filtroNombre.trim() !== '') {
      params = params.set('nombre', filtroNombre.trim());
    }
    return this.http.get<Producto[]>(this.apiUrl, { params });
  }

  /**
   * 2. GET: Obtener un solo producto mediante su ID
   */
  obtenerProductoPorId(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  /**
   * 3. POST: Guardar un nuevo producto
   */
  crearProducto(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  /**
   * 4. PUT: Actualizar un producto existente
   */
  actualizarProducto(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, producto);
  }

  /**
   * 5. DELETE: Eliminar un producto por su ID
   */
  eliminarProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}

