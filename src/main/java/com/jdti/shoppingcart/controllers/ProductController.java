package com.jdti.shoppingcart.controllers;

import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProductEntity product, BindingResult result) {
        if (result.hasErrors()) {
            return this.validator(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iProductService.save(product));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        // TODO: Implementar tests unitarios
        List<ProductEntity> products = iProductService.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> findById(@RequestParam String idProduct) {
        Optional<ProductEntity> product = iProductService.findById(idProduct);
        if (product.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(product.get());
    }

    @GetMapping("/details")
    public ResponseEntity<?> findBySku(@RequestParam String sku) {
        // TODO: Refactorizar el comportamiento del descueto
        List<ProductEntity> products = iProductService.findBySku(sku);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    protected ResponseEntity<?> validator(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
