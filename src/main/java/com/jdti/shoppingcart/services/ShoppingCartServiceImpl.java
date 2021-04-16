package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;
import com.jdti.shoppingcart.models.repositories.ICustomerRepository;
import com.jdti.shoppingcart.models.repositories.IProductRepository;
import com.jdti.shoppingcart.models.repositories.IShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    private final IShoppingCartRepository iShoppingCartRepository;

    private final ICustomerRepository iCustomerRepository;

    private final IProductRepository iProductRepository;

    public ShoppingCartServiceImpl(IShoppingCartRepository iShoppingCartRepository, ICustomerRepository iCustomerRepository, IProductRepository iProductRepository) {
        this.iShoppingCartRepository = iShoppingCartRepository;
        this.iCustomerRepository = iCustomerRepository;
        this.iProductRepository = iProductRepository;
    }

    @Transactional
    @Override
    public ShoppingCartEntity addCartItemToCart(CustomerEntity customer, ProductEntity product, int quantity) {
        // TODO: Agregar validacion de cantidad solicitada con la que hay en inventario
        ShoppingCartEntity cartItem = iShoppingCartRepository.findByCustomerAndProduct(customer, product);
        if (cartItem != null) {
            return updateItemQuantity(customer, product, cartItem.getQuantity() + quantity);
        }
        return iShoppingCartRepository.save(new ShoppingCartEntity(customer, product, quantity));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShoppingCartEntity> findByCustomerId(CustomerEntity customer) {
        return iShoppingCartRepository.findByCustomer(customer);
    }

    @Transactional
    @Override
    public void deleteItemCart(CustomerEntity customer, ProductEntity product) {
        iShoppingCartRepository.deleteByCustomerAndProduct(customer, product);
    }

    @Transactional
    @Override
    public void deleteAllByCustomer(CustomerEntity customer) {
        iShoppingCartRepository.deleteAllByCustomer(customer);
    }

    @Transactional
    @Override
    public ShoppingCartEntity updateItemQuantity(CustomerEntity customer, ProductEntity product, int quantity) {
        ShoppingCartEntity cartItem = iShoppingCartRepository.findByCustomerAndProduct(customer, product);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            return iShoppingCartRepository.save(cartItem);
        } else {
            return addCartItemToCart(customer, product, quantity);
        }
    }
}
