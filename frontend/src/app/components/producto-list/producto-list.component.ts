import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/producto.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-producto-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.css']
})
export class ProductoListComponent implements OnInit {
  productos: Producto[] = [];
  cargando: boolean = false;
  textoBusqueda: string = '';
  mensajeExito: string = '';
  mensajeError: string = '';

  // Métricas
  totalProductos: number = 0;
  totalUnidadesStock: number = 0;
  productosStockBajo: number = 0;

  constructor(
    private productoService: ProductoService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  /**
   * Carga la lista de productos desde la API REST en Spring Boot
   */
  cargarProductos(): void {
    this.cargando = true;
    this.mensajeError = '';

    this.productoService.obtenerProductos(this.textoBusqueda).subscribe({
      next: (datos) => {
        this.productos = datos;
        this.calcularMetricas();
        this.cargando = false;
      },
      error: (err) => {
        this.cargando = false;
        this.mensajeError = 'Error al cargar los productos. Asegúrate de que el Backend esté activo.';
      }
    });
  }

  /**
   * Busca productos cuando el usuario escribe en el input de búsqueda
   */
  buscar(): void {
    this.cargarProductos();
  }

  /**
   * Limpia el campo de búsqueda y recarga todos los productos
   */
  limpiarBusqueda(): void {
    this.textoBusqueda = '';
    this.cargarProductos();
  }

  /**
   * Elimina un producto tras confirmación del usuario
   */
  eliminar(producto: Producto): void {
    const confirmar = confirm(`¿Estás seguro de que deseas eliminar el producto "${producto.nombre}"?`);
    if (!confirmar || !producto.id) return;

    this.productoService.eliminarProducto(producto.id).subscribe({
      next: () => {
        this.mensajeExito = `El producto "${producto.nombre}" fue eliminado exitosamente.`;
        this.cargarProductos();
        setTimeout(() => (this.mensajeExito = ''), 4000);
      },
      error: (err) => {
        this.mensajeError = 'No se pudo eliminar el producto.';
      }
    });
  }

  /**
   * Calcula estadísticas rápidas para las tarjetas superiores
   */
  private calcularMetricas(): void {
    this.totalProductos = this.productos.length;
    this.totalUnidadesStock = this.productos.reduce((acc, p) => acc + (p.stock || 0), 0);
    this.productosStockBajo = this.productos.filter((p) => (p.stock || 0) <= 10).length;
  }
}

