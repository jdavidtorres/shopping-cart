package com.jdti.shoppingcart.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ShoppingCartDto {

    private List<CartItemDto> cartItems;
    private Double total;

    public ShoppingCartDto(List<CartItemDto> cartItems, Double total) {
        this.cartItems = cartItems;
        this.total = total;
    }
}
