package com.jdti.shoppingcart.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemToCartDto {

    @NotEmpty
    private String idCustomer;

    @NotEmpty
    private String idProduct;

    @NotNull
    private Integer quantity;

    public ItemToCartDto(String idCustomer, String idProduct, Integer quantity) {
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }
}
