package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;

import java.util.List;

public interface IShoppingCartService {

    ShoppingCartEntity addCartItemToCart(String idCustomer, String idProduct, int quantity);

    List<ShoppingCartEntity> findByCustomerId(String idCustomer);

    void deleteItemCart(String idCustomer, String idProduct);

    void deleteAllByCustomer(String idCustomer);

    ShoppingCartEntity updateItemQuantity(String idCustomer, String idProduct, int quantity);
}