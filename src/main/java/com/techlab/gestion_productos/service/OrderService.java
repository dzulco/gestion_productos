package com.techlab.gestion_productos.service;

import com.techlab.gestion_productos.exception.InsufficientStockException;
import com.techlab.gestion_productos.model.*;
import com.techlab.gestion_productos.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {

        order.setState(OrderState.PENDING);
        Client client = clientService.findByIdEntity(order.getClient().getId());
        order.setClient(client);  // Asocio la orden con el cliente

        if (order.getOrderLines() != null) {
            for (OrderLine line : order.getOrderLines()) {
                if (line.getProduct() == null || line.getProduct().getId() == null) {
                    throw new IllegalArgumentException("❌ Cada linea de orden debe tener un Producto ID Válido");
                }

                Product product = productService.findProductById(line.getProduct().getId())
                        .orElseThrow(() -> new EntityNotFoundException("❌ Producto no encontrado: " + line.getProduct().getId()));

                //Verifico stock si pasa decremento
                if(product.getStock()<line.getQuantity()) {
                    throw new InsufficientStockException("❌ No hay esa cantidad en stock para el producto Id: " + line.getProduct().getId());
                }
                productService.decrementQuantity(product, line.getQuantity());

                line.setProduct(product); // Set full product
                line.setOrder(order);     // Set back-reference
                line.setSubTotal(product.getPrice() * line.getQuantity()); //agrego subtotal
                line.setPrice(product.getPrice()); //para tener el precio al momento de cargar la orden
            }
        }
        order.calculateTotal(); // Calculamos y seteamos total
        client.addOrder(order);// agrego la orden al cliente

        return orderRepository.save(order);
    }

    public boolean deleteOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.getClient().removeOrder(order);
            orderRepository.delete(order);
            return true;
        }
        return false;
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setClient(updatedOrder.getClient());
            existingOrder.setState(updatedOrder.getState());

            // Eliminar líneas anteriores y reemplazar por nuevas
            existingOrder.getOrderLines().clear();

            if (updatedOrder.getOrderLines() != null) {
                for (OrderLine line : updatedOrder.getOrderLines()) {
                    line.setOrder(existingOrder); // ¡clave!
                    existingOrder.getOrderLines().add(line);
                }
            }

            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + id));
    }

}
