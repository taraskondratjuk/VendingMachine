package com.example.vendingmachine.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString()

@Entity
@Table(name = "order_list")
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    public OrderList(String productName, double productPrice, LocalDate purchaseDate) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.purchaseDate = purchaseDate;
    }
}
