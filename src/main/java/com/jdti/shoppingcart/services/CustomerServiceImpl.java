package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.repositories.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Override
    public Optional<CustomerEntity> findById(String idCustomer) {
        return iCustomerRepository.findById(idCustomer);
    }
}
