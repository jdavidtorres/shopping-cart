package com.jdti.shoppingcart.controllers;

import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ShippingEntity;
import com.jdti.shoppingcart.services.ICustomerService;
import com.jdti.shoppingcart.services.IShippingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RequestMapping("/shipping")
@RestController
public class ShippingController {

    private final IShippingService iShippingService;

    private final ICustomerService iCustomerService;

    public ShippingController(IShippingService iShippingService, ICustomerService iCustomerService) {
        this.iShippingService = iShippingService;
        this.iCustomerService = iCustomerService;
    }

    @GetMapping
    public ResponseEntity<?> findAllShipping(String idCustomer) {
        Optional<CustomerEntity> customer = iCustomerService.findById(idCustomer);

        if (customer.isEmpty()) {

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(iShippingService.findAll(customer.get()));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> shippingDetails(String idCustomer) {
        Optional<CustomerEntity> customer = iCustomerService.findById(idCustomer);

        if (customer.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ArrayList<ShippingEntity>());
    }

    @PostMapping
    public ResponseEntity<?> confirmShipping(@RequestParam String idCustomer) {
        Optional<CustomerEntity> customer = iCustomerService.findById(idCustomer);

        if (customer.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iShippingService.createShipping(customer.get()));
    }

    @PutMapping
    public ResponseEntity<?> completeShipping(@RequestParam String idCustomer, @RequestParam String orderLine) {
        try {
            iShippingService.completeShipping(idCustomer, Long.parseLong(orderLine));
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
