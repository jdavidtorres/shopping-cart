package com.jdti.shoppingcart.models.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "customers")
@Data
public class CustomerEntity {

    @NotEmpty
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Column
    private String address;

    @Column(name = "second_address", nullable = false)
    private String secondAddress;

    private String email;
}
