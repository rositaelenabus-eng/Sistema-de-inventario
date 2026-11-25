import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ProductoListComponent } from './components/producto-list/producto-list.component';
import { ProductoFormComponent } from './components/producto-form/producto-form.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  // Ruta pública de autenticación
  { path: 'login', component: LoginComponent },

  // Rutas privadas protegidas por el AuthGuard
  { path: 'productos', component: ProductoListComponent, canActivate: [authGuard] },
  { path: 'productos/nuevo', component: ProductoFormComponent, canActivate: [authGuard] },
  { path: 'productos/editar/:id', component: ProductoFormComponent, canActivate: [authGuard] },

  // Redirecciones por defecto
  { path: '', redirectTo: '/productos', pathMatch: 'full' },
  { path: '**', redirectTo: '/productos' }
];
