package com.example.vendingmachine.dao;

import com.example.vendingmachine.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product,Integer> {
}
