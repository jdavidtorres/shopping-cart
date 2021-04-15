package com.jdti.shoppingcart.models.repositories;

import com.jdti.shoppingcart.models.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity, String> {
}
