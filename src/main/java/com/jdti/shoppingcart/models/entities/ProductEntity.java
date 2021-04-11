package com.jdti.shoppingcart.models.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {

    @NotEmpty
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "sku", nullable = false)
    private Long sku;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description")
    private String description;
}
