package com.jdti.shoppingcart.services;

import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;

    @Transactional
    @Override
    public ProductEntity save(ProductEntity product) {
        return iProductRepository.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductEntity> findAll() {
        return iProductRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductEntity> findById(String id) {
        return iProductRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductEntity> findBySku(String sku) {
        return iProductRepository.findBySku(sku);
    }
}
