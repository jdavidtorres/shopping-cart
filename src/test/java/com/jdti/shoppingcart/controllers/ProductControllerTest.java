package com.jdti.shoppingcart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void saveProductOk() throws Exception {
        ProductEntity newProduct = new ProductEntity("Control Remoto Samsung", "555555", 89900.00, 5, false);
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoString = objectMapper.writeValueAsString(newProduct);
        mockMvc.perform(post("/products/")
                .content(dtoString)
                .contentType("application/json;charset=UTF-8")
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void saveProductValidationNoQuantity() throws Exception {
        ProductEntity newProduct = new ProductEntity();
        newProduct.setName("Cereal del Tigre");
        newProduct.setSku("SKU-12345");
        newProduct.setPrice(4000.00);

        ObjectMapper objectMapper = new ObjectMapper();
        String dtoString = objectMapper.writeValueAsString(newProduct);
        mockMvc.perform(post("/products/")
                        .content(dtoString)
                        .contentType("application/json;charset=UTF-8")
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }
}
