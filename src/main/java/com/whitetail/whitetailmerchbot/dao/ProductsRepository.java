package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Product, Integer> {
List<Product> findAll();
}
