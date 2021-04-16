package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;
import com.jdti.shoppingcart.models.repositories.ICustomerRepository;
import com.jdti.shoppingcart.models.repositories.IProductRepository;
import com.jdti.shoppingcart.models.repositories.IShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    @Autowired
    private IShoppingCartRepository iShoppingCartRepository;

    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Autowired
    private IProductRepository iProductRepository;

    @Transactional
    @Override
    public ShoppingCartEntity addCartItemToCart(CustomerEntity customer, ProductEntity product, int quantity) {
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
    public ShoppingCartEntity updateItemQuantity(String idCustomer, String idProduct, int quantity) {
        // TODO: Refactorizar agregando un optional en los services de Customer y Product para validar en el Controller
        CustomerEntity customer = iCustomerRepository.findById(idCustomer).get();
        ProductEntity product = iProductRepository.findById(idProduct).get();
        ShoppingCartEntity cartItem = iShoppingCartRepository.findByCustomerAndProduct(customer, product);
        cartItem.setQuantity(quantity);
        return iShoppingCartRepository.save(cartItem);
    }
}
