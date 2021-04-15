package com.jdti.shoppingcart.models.repositories;

import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCartEntity, String> {

    ShoppingCartEntity findByCustomerAndProduct(CustomerEntity customerEntity, ProductEntity productEntity);

    List<ShoppingCartEntity> findByCustomer(CustomerEntity customerEntity);

    void deleteByCustomerAndProduct(CustomerEntity customerEntity, ProductEntity productEntity);

    void deleteAllByCustomer(CustomerEntity customerEntity);
}
