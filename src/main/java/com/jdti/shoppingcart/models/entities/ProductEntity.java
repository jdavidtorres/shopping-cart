package com.jdti.shoppingcart.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "sku", nullable = false)
    private String sku;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "discount", nullable = false)
    private boolean discount;

    public ProductEntity(String name, String sku, Double price, Integer quantity, boolean discount) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Double getPrice() {
        // TODO: Hace falta test y refactor
        return discount ? Double.valueOf(this.price / 2) : this.price;
    }
}
