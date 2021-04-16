package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.dtos.ShippingDto;
import com.jdti.shoppingcart.models.dtos.ShippingItem;
import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ShippingEntity;
import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;
import com.jdti.shoppingcart.models.repositories.ICustomerRepository;
import com.jdti.shoppingcart.models.repositories.IShippingRepository;
import com.jdti.shoppingcart.models.repositories.IShoppingCartRepository;
import com.jdti.shoppingcart.utils.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingServiceImpl implements IShippingService {

    private final IShippingRepository iShippingRepository;

    private final IShoppingCartRepository iShoppingCartRepository;

    private final ICustomerRepository iCustomerRepository;

    public ShippingServiceImpl(IShippingRepository iShippingRepository, IShoppingCartRepository iShoppingCartRepository, ICustomerRepository iCustomerRepository) {
        this.iShippingRepository = iShippingRepository;
        this.iShoppingCartRepository = iShoppingCartRepository;
        this.iCustomerRepository = iCustomerRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShippingEntity> findAll(CustomerEntity customerEntity) {
        return iShippingRepository.findByIdCustomer(customerEntity.getId());
    }

    @Transactional
    @Override
    public ShippingDto createShipping(CustomerEntity customerEntity) {
        List<ShoppingCartEntity> shoppingCartEntities = iShoppingCartRepository.findByCustomer(customerEntity);
        List<ShippingItem> shippingItems = new ArrayList<>();
        Double total = 0.0;
        Long orderLine = iShippingRepository.findDistinctTopByOrderByOrderLineDesc().getOrderLine();
        orderLine = orderLine + 1;
        for (ShoppingCartEntity item : shoppingCartEntities) {
            ShippingEntity shipping = new ShippingEntity(orderLine, Status.PENDIENTE.name(), item.getProduct().getPrice() * item.getQuantity(), item.getCustomer().getId(), item.getProduct().getId(), item.getQuantity());
            iShippingRepository.save(shipping);
            total += shipping.getTotal();
            shippingItems.add(new ShippingItem(shipping.getIdCustomer(), shipping.getIdProduct(), shipping.getQuantity()));
        }
        if (!shoppingCartEntities.isEmpty()) {
            iShoppingCartRepository.deleteAllByCustomer(shoppingCartEntities.get(0).getCustomer());
        }
        return new ShippingDto(orderLine, shippingItems, total);
    }

    @Transactional
    @Override
    public void completeShipping(String idCustomer, Long orderLine) {
        List<ShippingEntity> shippingComplete = iShippingRepository.findByIdCustomer(idCustomer).stream()
                .filter(order -> order.getOrderLine().equals(orderLine))
                .filter(order -> order.getStatus().equals(Status.PENDIENTE.name()))
                .map(item -> {
                    item.setStatus(Status.COMPLETADO.name());
                    return item;
                })
                .collect(Collectors.toList());
        iShippingRepository.saveAll(shippingComplete);
    }
}
