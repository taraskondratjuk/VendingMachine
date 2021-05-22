package com.example.vendingmachine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "products")
@Entity
@Table(name = "vendingMachine")
public class VendingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "vending_machine_name")
    private String vendingMachineName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="vendingMachine_id")
    @JsonIgnore
    List<Product> products = new ArrayList<>();
}
