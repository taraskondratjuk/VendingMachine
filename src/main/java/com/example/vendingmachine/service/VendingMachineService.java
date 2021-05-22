package com.example.vendingmachine.service;

import com.example.vendingmachine.models.OrderList;
import com.example.vendingmachine.models.Product;

import java.util.List;


public interface VendingMachineService {

    List<Product> productsList();

    void addCategory(Product product);

    void addItem(Product product);

    void purchase(OrderList orderList);

    void clear(Product product);

    List<String> report(int year, int month, int day);

    List<String> reportByYear(int year, int month, int day);

    List<String> reportByYearAndMonth(int year, int month, int day);

    List<String> reportByYearAndMonthAndDay(int year, int month, int day);

}
