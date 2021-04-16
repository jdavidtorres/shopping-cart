package com.jdti.shoppingcart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdti.shoppingcart.models.dtos.ItemToCartDto;
import com.jdti.shoppingcart.models.dtos.ShoppingCartDto;
import com.jdti.shoppingcart.models.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class ShoppingCartControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void addCartItemToCart() throws Exception {
        // TODO: Agregar escenario de agregar nuevamente un producto, bug
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
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void findByCustomerId() throws Exception {
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

        String jsonResponse = mockMvc.perform(get("/cart/")
                .param("idCustomer", "id-customer-1")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ShoppingCartDto cart = objectMapper.readValue(jsonResponse, ShoppingCartDto.class);
        assertEquals(product.getPrice() * item.getQuantity(), cart.getTotal());
    }

    @Test
    void deleteItemCart() throws Exception {
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

        mockMvc.perform(delete("/cart/")
                .param("idCustomer", "id-customer-1")
                .param("idProduct", item.getIdProduct())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        // Corroboramos que no borre el carrito de otro cliente
        String jsonResponse = mockMvc.perform(get("/cart/")
                .param("idCustomer", "id-customer-2")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ShoppingCartDto cart = objectMapper.readValue(jsonResponse, ShoppingCartDto.class);
        assertEquals(125000, cart.getTotal());
    }
}
