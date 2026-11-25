package com.gestion.productos;

import com.gestion.productos.model.Producto;
import com.gestion.productos.model.Usuario;
import com.gestion.productos.repository.ProductoRepository;
import com.gestion.productos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal que arranca la aplicación Spring Boot.
 * Incluye un CommandLineRunner para insertar datos iniciales en la base de datos H2.
 */
@SpringBootApplication
public class GestionProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionProductosApplication.class, args);
    }

    /**
     * Este método se ejecuta al iniciar la aplicación.
     * Crea usuarios y productos iniciales para que el sistema ya tenga información al abrir Angular.
     */
    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepo, ProductoRepository productoRepo) {
        return args -> {
            // 1. Crear usuarios por defecto si no existen
            if (usuarioRepo.count() == 0) {
                Usuario admin = new Usuario("admin", "admin123", "Administrador Principal", "ADMIN");
                Usuario user = new Usuario("usuario", "12345", "Juan Pérez", "USER");

                usuarioRepo.save(admin);
                usuarioRepo.save(user);

                System.out.println(">>> Usuarios iniciales creados en la BD H2:");
                System.out.println("    - admin / admin123 (ADMIN)");
                System.out.println("    - usuario / 12345 (USER)");
            }

            // 2. Crear productos de muestra si la base de datos está vacía
            if (productoRepo.count() == 0) {
                productoRepo.save(new Producto("Laptop HP Pavilion 15", "Intel Core i5, 16GB RAM, 512GB SSD NVMe", "Tecnología", 799.99, 15));
                productoRepo.save(new Producto("Smartphone Samsung Galaxy A54", "Pantalla AMOLED 120Hz, 128GB, Triple Cámara", "Tecnología", 349.50, 25));
                productoRepo.save(new Producto("Monitor LG UltraWide 29\"", "Resolución 2560x1080 IPS, 75Hz, HDR10", "Tecnología", 219.00, 8));
                productoRepo.save(new Producto("Teclado Mecánico RGB Redragon", "Switches Blue, anti-ghosting, retroiluminado", "Accesorios", 45.00, 30));
                productoRepo.save(new Producto("Silla Ergonómica Ejecutiva", "Soporte lumbar ajustable, malla transpirable", "Mobiliario", 140.00, 12));
                productoRepo.save(new Producto("Auriculares Inalámbricos Sony", "Cancelación de ruido activa, 30h batería", "Audio", 89.90, 18));

                System.out.println(">>> 6 Productos de ejemplo cargados en la BD H2.");
            }
        };
    }
}

