package com.jdti.shoppingcart.models.repositories;

import com.jdti.shoppingcart.models.entities.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShippingRepository extends JpaRepository<ShippingEntity, String> {

    List<ShippingEntity> findByIdCustomer(String idCustomer);

    List<ShippingEntity> findByOrderLine(Long orderLine);

    ShippingEntity findDistinctTopByOrderByOrderLineDesc();
}
