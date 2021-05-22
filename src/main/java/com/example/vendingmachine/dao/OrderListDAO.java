package com.example.vendingmachine.dao;

import com.example.vendingmachine.models.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderListDAO extends JpaRepository<OrderList,Integer> {

}
