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

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String address;

    @Column(name = "second_address")
    private String secondAddress;

    @Column(nullable = false)
    private String email;

    public CustomerEntity(String name, String address, String secondAddress, String email) {
        this.name = name;
        this.address = address;
        this.secondAddress = secondAddress;
        this.email = email;
    }
}
