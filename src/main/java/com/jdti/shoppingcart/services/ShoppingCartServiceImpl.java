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
    public List<ShoppingCartEntity> findByCustomerId(String idCustomer) {
        // TODO: Agregar validacion
        return iShoppingCartRepository.findByCustomer(iCustomerRepository.findById(idCustomer).get());
    }

    @Transactional
    @Override
    public void deleteItemCart(String idCustomer, String idProduct) {
        // TODO: Borrar mas bonito
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(idCustomer);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(idProduct);
        iShoppingCartRepository.deleteByCustomerAndProduct(customerEntity, productEntity);
    }

    @Transactional
    @Override
    public void deleteAllByCustomer(String idCustomer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(idCustomer);
        // CustomerEntity customer = iCustomerRepository.findById(idCustomer).orElse(new CustomerEntity());
        iShoppingCartRepository.deleteAllByCustomer(customerEntity);
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
