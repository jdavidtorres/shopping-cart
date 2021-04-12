package com.jdti.shoppingcart.controllers;

import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProductEntity product, BindingResult result) {
        if (result.hasErrors()) {
            return this.validator(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iProductService.save(product));
    }

    protected ResponseEntity<?> validator(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
