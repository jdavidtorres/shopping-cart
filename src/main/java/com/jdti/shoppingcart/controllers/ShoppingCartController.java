package com.jdti.shoppingcart.controllers;

import com.jdti.shoppingcart.models.dtos.CarItemDto;
import com.jdti.shoppingcart.models.entities.ShoppingCartEntity;
import com.jdti.shoppingcart.services.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@RestController
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService iShoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCartEntity> addCartItemToCart(@RequestParam String idCustomer, @RequestParam String idProduct, @RequestParam String quantity) {
        // TODO: Refactorizar a @RequestBody
        int quantityParam;
        try {
            quantityParam = Integer.parseInt(quantity);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iShoppingCartService.addCartItemToCart(idCustomer, idProduct, quantityParam));
    }

    @GetMapping
    public ResponseEntity<?> findByCustomerId(String idCustomer) {
        // TODO: Refactor urgente!!
        List<ShoppingCartEntity> carItemsEntities = iShoppingCartService.findByCustomerId(idCustomer);
        if (!carItemsEntities.isEmpty()) {
            List<CarItemDto> carItems = new ArrayList<>();
            Double total = 0.0;
            for (ShoppingCartEntity item : carItemsEntities) {
                CarItemDto carItemDto = new CarItemDto();
                carItemDto.setCustomerName(item.getCustomer().getName());
                carItemDto.setProductName(item.getProduct().getName());
                carItemDto.setQuantity(item.getQuantity());
                carItemDto.setItemPrice(item.getQuantity() * item.getProduct().getPrice());
                carItemDto.setSku(item.getProduct().getSku());
                carItems.add(carItemDto);
                total += carItemDto.getItemPrice();
            }
            Map<String, Object> response = new HashMap<>();
            response.put("items", carItems);
            response.put("total", total);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public void deleteItemCart(@RequestParam String idCustomer, @RequestParam String idProduct) {
        iShoppingCartService.deleteItemCart(idCustomer, idProduct);
    }

    @DeleteMapping("/clean")
    public void cleanCart(@RequestParam String idCustomer) {
        iShoppingCartService.deleteAllByCustomer(idCustomer);
    }

    @PutMapping
    public ShoppingCartEntity updateItemQuantity(@RequestParam String idCustomer, @RequestParam String idProduct, @RequestParam String quantity) {
        return iShoppingCartService.updateItemQuantity(idCustomer, idProduct, Integer.parseInt(quantity));
    }
}
