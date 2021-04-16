package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.dtos.ShippingDto;
import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ShippingEntity;

import java.util.List;

public interface IShippingService {

    List<ShippingEntity> findAll(CustomerEntity customerEntity);

    ShippingDto createShipping(CustomerEntity customerEntity);

    void completeShipping(String isCustomer, Long orderLine);
}
