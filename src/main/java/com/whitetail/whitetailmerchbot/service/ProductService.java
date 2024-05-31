package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.ProductsRepository;
import com.whitetail.whitetailmerchbot.entity.OrderProduct;
import com.whitetail.whitetailmerchbot.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductsRepository productsRepository;
    private final OrderService orderService;

    @Autowired
    public ProductService(ProductsRepository productsRepository, OrderService orderService) {
        this.productsRepository = productsRepository;
        this.orderService = orderService;
    }

    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    public int getQuantityOfProduct(int productId) {
        return productsRepository.findProductByProductId(productId).getQuantity();
    }

    public void updateProductQuantity(Long orderId) {
        List<OrderProduct> orderProducts= orderService.findOrderByOrderId(orderId).getOrderProducts();
        for (OrderProduct currentProduct : orderProducts) {
            int quantityInOrder = currentProduct.getQuantity();
            int productId = currentProduct.getProductId();
            Product productInRepository = getProductById(productId);
            productInRepository.setQuantity(productInRepository.getQuantity() - quantityInOrder);
            productsRepository.save(productInRepository);
        }
    }

    public Product getProductById(int productId) {
        return productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
