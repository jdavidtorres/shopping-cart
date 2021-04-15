package com.jdti.shoppingcart.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarItemDto {

    private String customerName;
    private String productName;
    private Double price;
    private int quantity;
    private String sku;
}
