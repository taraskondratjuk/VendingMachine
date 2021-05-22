package com.example.vendingmachine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "vendingMachine")
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name ="product_name", unique = true)
    private String productName;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "count_of_product")
    private int countOfProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendingMachine_id",insertable = false,updatable = false)
    @JsonIgnore
    private VendingMachine vendingMachine;

}
