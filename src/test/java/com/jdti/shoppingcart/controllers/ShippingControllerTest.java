package com.jdti.shoppingcart.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdti.shoppingcart.models.dtos.ItemToCartDto;
import com.jdti.shoppingcart.models.dtos.ShippingDto;
import com.jdti.shoppingcart.models.entities.ProductEntity;
import com.jdti.shoppingcart.models.entities.ShippingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class ShippingControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void findAllShipping() throws Exception {
        // Crear producto
        ProductEntity newProduct = new ProductEntity("Control Remoto Samsung", "555555", 89900.00, 5, false);
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoString = objectMapper.writeValueAsString(newProduct);
        String mvcResultString = mockMvc.perform(post("/products/")
                .content(dtoString)
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"))
                .andReturn().getResponse().getContentAsString();
        ProductEntity product = objectMapper.readValue(mvcResultString, ProductEntity.class);

        // Agregar al carrito
        ItemToCartDto item = new ItemToCartDto("id-customer-1", product.getId(), 3);
        String itemJson = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/cart/")
                .content(itemJson)
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"));

        // Confirmar pedido
        mockMvc.perform(post("/shipping/")
                .param("idCustomer", "id-customer-1")
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"));

        // Obtener los pedidos del cliente
        String jsonResponse = mockMvc.perform(get("/shipping/")
                .param("idCustomer", "id-customer-1")
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<ShippingEntity> shipping = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        assertFalse(shipping.isEmpty());
    }

    @Test
    void shippingDetails() throws Exception {

    }

    @Test
    void confirmShipping() throws Exception {
        ProductEntity newProduct = new ProductEntity("Control Remoto Samsung", "555555", 89900.00, 5, false);
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoString = objectMapper.writeValueAsString(newProduct);
        String mvcResultString = mockMvc.perform(post("/products/")
                .content(dtoString)
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"))
                .andReturn().getResponse().getContentAsString();
        ProductEntity product = objectMapper.readValue(mvcResultString, ProductEntity.class);

        ItemToCartDto item = new ItemToCartDto("id-customer-1", product.getId(), 3);
        String itemJson = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/cart/")
                .content(itemJson)
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"));

        String jsonResponse = mockMvc.perform(post("/shipping/")
                .param("idCustomer", "id-customer-1")
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ShippingDto shippingDto = objectMapper.readValue(jsonResponse, ShippingDto.class);
        assertEquals(3L, shippingDto.getOrderLine());
        assertFalse(shippingDto.getShippingItems().isEmpty());
        assertEquals(item.getQuantity() * newProduct.getPrice(), shippingDto.getTotal());
    }

    @Test
    void completeShipping() throws Exception {
        ProductEntity newProduct = new ProductEntity("Control Remoto Samsung", "555555", 89900.00, 5, false);
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoString = objectMapper.writeValueAsString(newProduct);
        String mvcResultString = mockMvc.perform(post("/products/")
                .content(dtoString)
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"))
                .andReturn().getResponse().getContentAsString();
        ProductEntity product = objectMapper.readValue(mvcResultString, ProductEntity.class);

        ItemToCartDto item = new ItemToCartDto("id-customer-1", product.getId(), 3);
        String itemJson = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/cart/")
                .content(itemJson)
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"));

        mockMvc.perform(post("/shipping/")
                .param("idCustomer", "id-customer-1")
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"));

        mockMvc.perform(put("/shipping/")
                .param("idCustomer", "id-customer-1")
                .param("orderLine", "3")
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}
