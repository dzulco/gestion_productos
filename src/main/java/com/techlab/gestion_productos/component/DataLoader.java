package com.techlab.gestion_productos.component;

import com.techlab.gestion_productos.model.Client;
import com.techlab.gestion_productos.model.Product;
import com.techlab.gestion_productos.service.ClientService;
import com.techlab.gestion_productos.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClientService clientService;
    private final ProductService productService;

    public DataLoader(ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        createClients();
        createProducts();
    }

    private void createProducts() {
        if (productService.getAllProducts().isEmpty()) {
            List<Product> products = List.of(
                    new Product("Zapatillas de Running", 20, 12000.00,
                            "Zapatillas ligeras y cómodas para correr.",
                            "https://images.pexels.com/photos/2529148/pexels-photo-2529148.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Pelota de Fútbol", 15, 4500.00,
                            "Pelota oficial para partidos y entrenamientos.",
                            "https://images.pexels.com/photos/46798/the-ball-stadion-football-the-pitch-46798.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Camiseta Deportiva", 30, 3500.00,
                            "Camiseta transpirable para todo tipo de deportes.",
                            "https://images.pexels.com/photos/3757958/pexels-photo-3757958.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Mancuernas 5kg", 10, 5000.00,
                            "Mancuernas para entrenamiento de fuerza y tonificación.",
                            "https://images.pexels.com/photos/2261477/pexels-photo-2261477.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Bicicleta de Montaña", 5, 45000.00,
                            "Bicicleta robusta para terrenos difíciles.",
                            "https://images.pexels.com/photos/276517/pexels-photo-276517.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Balón de Básquet", 18, 6000.00,
                            "Balón oficial para jugar en cancha.",
                            "https://images.pexels.com/photos/159283/basketball-basketball-court-ball-game-159283.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Guantes de Boxeo", 12, 8000.00,
                            "Guantes acolchados para entrenamiento de boxeo.",
                            "https://images.pexels.com/photos/416778/pexels-photo-416778.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Ropa de Yoga", 25, 7000.00,
                            "Conjunto cómodo y flexible para yoga.",
                            "https://images.pexels.com/photos/1552249/pexels-photo-1552249.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Casco para Ciclismo", 8, 9000.00,
                            "Casco protector para ciclismo seguro.",
                            "https://images.pexels.com/photos/276517/pexels-photo-276517.jpeg?auto=compress&cs=tinysrgb&w=400"),
                    new Product("Cuerda para Saltar", 40, 1500.00,
                            "Cuerda ligera para entrenamiento cardiovascular.",
                            "https://images.pexels.com/photos/416778/pexels-photo-416778.jpeg?auto=compress&cs=tinysrgb&w=400")
            );

            productService.saveAll(products);
            System.out.println("✔ Productos creados por codigo.");
        }
    }

    private void createClients() {
        if (clientService.isEmpty()) {
            clientService.save(new Client("John", "Smith", "john@example.com"));
            clientService.save(new Client("Alice", "Johnson", "alice@example.com"));
            clientService.save(new Client("Bob", "Williams", "bob@example.com"));

            System.out.println("✔ Clientes creados por codigo.");
        }
    }
}
