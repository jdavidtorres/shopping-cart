package com.jdti.shoppingcart.models.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShippingDto {

    private Long orderLine;
    private List<ShippingItem> shippingItems;
    private Double total;

    public ShippingDto() {
        this.shippingItems = new ArrayList<>();
    }

    public ShippingDto(Long orderLine, List<ShippingItem> shippingItems, Double total) {
        this.orderLine = orderLine;
        this.shippingItems = shippingItems;
        this.total = total;
    }
}
