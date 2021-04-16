package com.jdti.shoppingcart.controllers;

import com.jdti.shoppingcart.models.dtos.CartItemDto;
import com.jdti.shoppingcart.models.dtos.ItemToCartDto;
import com.jdti.shoppingcart.models.dtos.ShoppingCartDto;
import com.jdti.shoppingcart.models.entities.CustomerEntity;
import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;
import com.jdti.shoppingcart.services.ICustomerService;
import com.jdti.shoppingcart.services.IProductService;
import com.jdti.shoppingcart.services.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/cart")
@RestController
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService iShoppingCartService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping
    public ResponseEntity<?> addCartItemToCart(@Valid @RequestBody ItemToCartDto item, BindingResult result) {
        Optional<CustomerEntity> customer = iCustomerService.findById(item.getIdCustomer());
        Optional<ProductEntity> product = iProductService.findById(item.getIdProduct());

        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        } else if (customer.isEmpty() || product.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iShoppingCartService.addCartItemToCart(customer.get(), product.get(), item.getQuantity()));
    }

    @GetMapping
    public ResponseEntity<?> findByCustomerId(@RequestParam String idCustomer) {
        Optional<CustomerEntity> customer = iCustomerService.findById(idCustomer);

        if (customer.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ShoppingCartEntity> carItemsEntities = iShoppingCartService.findByCustomerId(customer.get());

        if (carItemsEntities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CartItemDto> carItems = new ArrayList<>();
        Double total = 0.0;
        for (ShoppingCartEntity item : carItemsEntities) {
            CartItemDto carItemDto = new CartItemDto(item.getCustomer().getName(), item.getProduct().getName(), item.getQuantity() * item.getProduct().getPrice(), item.getQuantity(), item.getProduct().getSku());
            carItems.add(carItemDto);
            total += carItemDto.getItemPrice();
        }
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto(carItems, total);
        return ResponseEntity.ok(shoppingCartDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteItemCart(@RequestParam String idCustomer, @RequestParam String idProduct) {
        Optional<CustomerEntity> customer = iCustomerService.findById(idCustomer);
        Optional<ProductEntity> product = iProductService.findById(idProduct);

        if (customer.isEmpty() || product.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        iShoppingCartService.deleteItemCart(customer.get(), product.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clean")
    public ResponseEntity<?> cleanCart(@RequestParam String idCustomer) {
        Optional<CustomerEntity> customer = iCustomerService.findById(idCustomer);
        if (customer.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        iShoppingCartService.deleteAllByCustomer(customer.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ShoppingCartEntity updateItemQuantity(@RequestParam String idCustomer, @RequestParam String idProduct, @RequestParam String quantity) {

        return iShoppingCartService.updateItemQuantity(idCustomer, idProduct, Integer.parseInt(quantity));
    }
}
