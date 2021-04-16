package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    ProductEntity save(ProductEntity product);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(String id);

    List<ProductEntity> findBySku(String sku);
}
