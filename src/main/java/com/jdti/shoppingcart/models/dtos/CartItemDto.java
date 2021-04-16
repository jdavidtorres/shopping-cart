package com.jdti.shoppingcart.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDto {

    private String customerName;
    private String productName;
    private Double itemPrice;
    private Integer quantity;
    private String sku;

    public CartItemDto(String customerName, String productName, Double itemPrice, Integer quantity, String sku) {
        this.customerName = customerName;
        this.productName = productName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.sku = sku;
    }
}
