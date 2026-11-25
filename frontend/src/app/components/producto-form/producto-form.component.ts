import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/producto.model';

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.css']
})
export class ProductoFormComponent implements OnInit {
  // Modelo para el formulario
  producto: Producto = {
    nombre: '',
    descripcion: '',
    categoria: 'Tecnología',
    precio: 0,
    stock: 0
  };

  esModoEdicion: boolean = false;
  idProducto?: number;
  cargando: boolean = false;
  guardando: boolean = false;
  mensajeError: string = '';

  // Categorías predefinidas comunes
  categorias: string[] = [
    'Tecnología',
    'Accesorios',
    'Mobiliario',
    'Audio',
    'Electrodomésticos',
    'Oficina',
    'Otros'
  ];

  constructor(
    private productoService: ProductoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.esModoEdicion = true;
      this.idProducto = Number(idParam);
      this.cargarProducto(this.idProducto);
    }
  }

  /**
   * Carga los datos del producto en modo edición
   */
  cargarProducto(id: number): void {
    this.cargando = true;
    this.productoService.obtenerProductoPorId(id).subscribe({
      next: (data) => {
        this.producto = data;
        this.cargando = false;
      },
      error: (err) => {
        this.cargando = false;
        this.mensajeError = 'No se pudo cargar el producto solicitado.';
      }
    });
  }

  /**
   * Guarda o actualiza el producto según el modo
   */
  guardarProducto(): void {
    // Validaciones básicas de Junior
    if (!this.producto.nombre.trim()) {
      this.mensajeError = 'El nombre del producto es obligatorio.';
      return;
    }
    if (this.producto.precio === undefined || this.producto.precio < 0) {
      this.mensajeError = 'El precio debe ser un número mayor o igual a 0.';
      return;
    }
    if (this.producto.stock === undefined || this.producto.stock < 0) {
      this.mensajeError = 'El stock no puede ser un valor negativo.';
      return;
    }

    this.guardando = true;
    this.mensajeError = '';

    if (this.esModoEdicion && this.idProducto) {
      // 1. UPDATE en el Backend Spring Boot
      this.productoService.actualizarProducto(this.idProducto, this.producto).subscribe({
        next: () => {
          this.guardando = false;
          this.router.navigate(['/productos']);
        },
        error: (err) => {
          this.guardando = false;
          this.mensajeError = 'Ocurrió un error al actualizar el producto.';
        }
      });
    } else {
      // 2. CREATE en el Backend Spring Boot
      this.productoService.crearProducto(this.producto).subscribe({
        next: () => {
          this.guardando = false;
          this.router.navigate(['/productos']);
        },
        error: (err) => {
          this.guardando = false;
          this.mensajeError = 'Ocurrió un error al guardar el nuevo producto.';
        }
      });
    }
  }
}

