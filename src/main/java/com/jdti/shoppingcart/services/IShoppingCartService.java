package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;

import java.util.List;

public interface IShoppingCartService {

    ShoppingCartEntity addCartItemToCart(CustomerEntity customer, ProductEntity product, int quantity);

    List<ShoppingCartEntity> findByCustomerId(CustomerEntity customer);

    void deleteItemCart(CustomerEntity customer, ProductEntity product);

    void deleteAllByCustomer(CustomerEntity customer);

    ShoppingCartEntity updateItemQuantity(CustomerEntity customer, ProductEntity product, int quantity);
}
