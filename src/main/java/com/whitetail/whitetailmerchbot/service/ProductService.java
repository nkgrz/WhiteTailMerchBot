package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.ProductsRepository;
import com.whitetail.whitetailmerchbot.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }
    public Product getProductById(int id) {
        return productsRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
