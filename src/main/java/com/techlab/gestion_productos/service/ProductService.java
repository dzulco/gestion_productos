package com.techlab.gestion_productos.service;

import com.techlab.gestion_productos.model.Product;
import com.techlab.gestion_productos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }


    public Optional<Product> findProductById(Long id) {
        return repo.findById(id).stream().findFirst();
    }

    public Product createProduct(Product product) {
        return repo.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return this.findProductById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            return repo.save(product);
        });
    }


    public boolean deleteProduct(Long id) {
        return this.findProductById(id).map(user -> {
                            repo.delete(user);
                            return true;}).orElse(false);

    }

    public List<Product> saveAll(List<Product> products) {
        return repo.saveAll(products);
    }

    public void decrementQuantity(Product product, Integer quantity) {
        product.setStock(product.getStock() - quantity);
    }
}
