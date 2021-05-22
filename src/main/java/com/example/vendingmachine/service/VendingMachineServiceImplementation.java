package com.example.vendingmachine.service;

import com.example.vendingmachine.dao.OrderListDAO;
import com.example.vendingmachine.dao.ProductDAO;
import com.example.vendingmachine.dao.VendingMachineDAO;
import com.example.vendingmachine.models.OrderList;
import com.example.vendingmachine.models.Product;
import com.example.vendingmachine.models.VendingMachine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VendingMachineServiceImplementation implements VendingMachineService {

    private ProductDAO productDAO;
    private OrderListDAO orderListDAO;
    private VendingMachineDAO vendingMachineDAO;

    @Override
    public List<Product> productsList() {
        List<Product> productList = productDAO.findAll()
                .stream()
                .sorted((prod1, prod2) -> prod2.getCountOfProduct() - prod1.getCountOfProduct())
                .collect(Collectors.toList());
        return productList;
    }

    @Override
    public void addCategory(Product product) {
        if (product.getProductName() != null && !product.getProductName().equals("") && product.getCountOfProduct() >= 0 && product.getProductPrice() >= 0) {
            VendingMachine vendingMachine = vendingMachineDAO.getOne(1);
            List<Product> productList = productDAO.findAll();
            if (productList.size() < 10) {
                productList.add(product);
                vendingMachine.setProducts(productList);
                vendingMachineDAO.save(vendingMachine);
            }
        }
    }

    @Override
    public void addItem(Product product) {
        if (product.getProductName() != null && !product.getProductName().equals("") && product.getCountOfProduct() >= 0 && product.getProductPrice() >= 0) {
            List<Product> productList = productDAO.findAll();
            Product changedProduct = productList.stream().filter(product1 -> product1.getProductName().equals(product.getProductName())).findAny().get();
            changedProduct.setCountOfProduct(product.getCountOfProduct() + changedProduct.getCountOfProduct());
            productDAO.save(changedProduct);
        }
    }

    @Override
    public void purchase(OrderList orderList) {
        LocalDate date = LocalDate.now();

        orderList.setPurchaseDate(date);

        List<Product> productList = productDAO.findAll();
        Product orderedProduct = productList.stream().filter(product1 -> product1.getProductName().equals(orderList.getProductName())).findAny().get();
        if (orderedProduct.getCountOfProduct() > 0) {
            orderedProduct.setCountOfProduct(orderedProduct.getCountOfProduct() - 1);
            productDAO.save(orderedProduct);
            orderList.setProductPrice(orderedProduct.getProductPrice());
            orderListDAO.save(new OrderList(orderList.getProductName(), orderList.getProductPrice(), orderList.getPurchaseDate()));
        }
    }

    @Override
    public void clear(Product product) {
        List<Product> productList = productDAO.findAll();
        Product changedProduct = productList.stream().filter(product1 -> product1.getProductName().equals(product.getProductName())).findAny().get();
        if (changedProduct.getCountOfProduct() == 0) {
            productDAO.delete(product);
        }
    }

    @Override
    public List<String> report(int year, int month, int day) {

        List<String> allStatistics = new ArrayList<>();
        if (year > 0 && month == 0 && day == 0) {
            allStatistics = reportByYear(year, 1, 1);
        }

        if (year > 0 && month > 0 && day == 0) {
            allStatistics = reportByYearAndMonth(year, month, 1);
        }
        if (year > 0 && month > 0 && day > 0) {
            allStatistics = reportByYearAndMonthAndDay(year, month, day);
        }

        return allStatistics;
    }

    @Override
    public List<String> reportByYear(int year, int month, int day) {

        List<String> sortedList = new ArrayList<>();
        double sum = 0;

        Map<String, List<OrderList>> orderLists = orderListDAO.findAll().
                stream().
                filter(orderList -> {
                    if ((orderList.getPurchaseDate().getYear() == year)) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .sorted((o1, o2) -> o2.getProductName().compareTo(o1.getProductName()))
                .collect(Collectors.groupingBy(orderList -> orderList.getProductName()));

        for (List<OrderList> value : orderLists.values()) {
            sortedList.add(value.get(0).getProductName() + " " + Math.round(value.get(0).getProductPrice() * value.size() * 100.0) / 100.0 + " " + value.size());
            sum += value.get(0).getProductPrice() * value.size();
        }

        sortedList.add(">Total " + Math.round(sum * 100.0) / 100.0);
        return sortedList;
    }

    @Override
    public List<String> reportByYearAndMonth(int year, int month, int day) {

        List<String> sortedList = new ArrayList<>();
        double sum = 0;

        Map<String, List<OrderList>> orderLists = orderListDAO.findAll().
                stream().
                filter(orderList -> {
                    if (orderList.getPurchaseDate().getYear() == year
                            && orderList.getPurchaseDate().getMonthValue() == month) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .sorted((o1, o2) -> o2.getProductName().compareTo(o1.getProductName()))
                .collect(Collectors.groupingBy(orderList -> orderList.getProductName()));

        for (List<OrderList> value : orderLists.values()) {

            sortedList.add(value.get(0).getProductName() + " " + Math.round(value.get(0).getProductPrice() * value.size() * 100.0) / 100.0 + " " + value.size());
            sum += value.get(0).getProductPrice() * value.size();
        }
        sortedList.add(">Total " + Math.round(sum * 100.0) / 100.0);
        return sortedList;
    }

    @Override
    public List<String> reportByYearAndMonthAndDay(int year, int month, int day) {

        List<String> sortedList = new ArrayList<>();
        double sum = 0;

        Map<String, List<OrderList>> orderLists = orderListDAO.findAll().
                stream().
                filter(orderList -> {
                    if ((orderList.getPurchaseDate().getYear() * 12 * 30) + (orderList.getPurchaseDate().getMonthValue() * 30) + (orderList.getPurchaseDate().getDayOfMonth()) >=
                            (year * 12 * 30) + (month * 30) + day) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .sorted((o1, o2) -> o2.getProductName().compareTo(o1.getProductName()))
                .collect(Collectors.groupingBy(orderList -> orderList.getProductName()));

        for (List<OrderList> value : orderLists.values()) {
            sortedList.add(value.get(0).getProductName() + " " + Math.round(value.get(0).getProductPrice() * value.size() * 100.0) / 100.0 + " " + value.size());
            sum += value.get(0).getProductPrice() * value.size();
        }

        sortedList.add(">Total " + Math.round(sum * 100.0) / 100.0);
        return sortedList;
    }
}

