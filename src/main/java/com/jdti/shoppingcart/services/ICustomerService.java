package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.CustomerEntity;

import java.util.Optional;

public interface ICustomerService {

    Optional<CustomerEntity> findById(String idCustomer);
}
