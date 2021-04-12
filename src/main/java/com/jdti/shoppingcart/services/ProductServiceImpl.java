package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public ProductEntity save(ProductEntity product) {
        return iProductRepository.save(product);
    }
}
