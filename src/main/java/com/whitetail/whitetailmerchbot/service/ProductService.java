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

    public int getQuantityOfProduct(int productId) {
        return productsRepository.findById(productId).get().getCount();
    }

    public boolean setQuantityOfProduct(int productId, int quantity) {
        Product product = productsRepository.findById(productId).get();
        int productQuantity = product.getCount();
        if (productQuantity < quantity) {
            return false;
        } else {
            product.setCount(productQuantity - quantity);
            return true;
        }
    }

    public Product getProductById(int productId) {
        return productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
