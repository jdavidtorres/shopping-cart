package com.jdti.shoppingcart.models.repositories;

import com.jdti.shoppingcart.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, String> {

    List<ProductEntity> findBySku(Long sku);
}
