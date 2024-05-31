package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.Product;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Product, Integer> {
    @NonNull
    List<Product> findAll();
    Product findProductByProductId(Integer productId);
}
