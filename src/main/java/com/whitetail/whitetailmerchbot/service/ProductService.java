package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.ProductsRepository;
import com.whitetail.whitetailmerchbot.entity.Order;
import com.whitetail.whitetailmerchbot.entity.OrderProduct;
import com.whitetail.whitetailmerchbot.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.constants.OtherConstants.ORDER_STATUS_CANCELED;

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
        return productsRepository.findProductByProductId(productId).getQuantity();
    }

    public void updateProductQuantity(Order order) {
        List<OrderProduct> orderProducts = order.getOrderProducts();
        for (OrderProduct currentProduct : orderProducts) {
            int quantityInOrder = currentProduct.getQuantity();
            int productId = currentProduct.getProductId();
            Product productInRepository = getProductById(productId);
            if (order.getStatus().equals(ORDER_STATUS_CANCELED)) {
                productInRepository.setQuantity(productInRepository.getQuantity() + quantityInOrder);
            } else {
                productInRepository.setQuantity(productInRepository.getQuantity() - quantityInOrder);
            }
            productsRepository.save(productInRepository);
        }
    }

    public Product getProductById(int productId) {
        return productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
