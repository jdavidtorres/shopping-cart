package com.jdti.shoppingcart.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "shipping")
@Data
@NoArgsConstructor
public class ShippingEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "order_line", nullable = false, updatable = false)
    private Long orderLine;

    @NotEmpty
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private Double total;

    private String idCustomer;

    private String idProduct;

    private Integer quantity;

    public ShippingEntity(Long orderLine, String status, Double total, String idCustomer, String idProduct, Integer quantity) {
        this.orderLine = orderLine;
        this.status = status;
        this.total = total;
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    @PrePersist
    private void saveDate() {
        createdDate = new Date();
    }
}
