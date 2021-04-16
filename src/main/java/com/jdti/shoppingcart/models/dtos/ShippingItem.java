package com.jdti.shoppingcart.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShippingItem {

    private String idCustomer;
    private String idProduct;
    private Integer quantity;

    public ShippingItem(String idCustomer, String idProduct, Integer quantity) {
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }
}
