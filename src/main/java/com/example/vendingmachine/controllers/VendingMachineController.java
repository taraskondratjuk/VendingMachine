package com.example.vendingmachine.controllers;

import com.example.vendingmachine.dao.ProductDAO;
import com.example.vendingmachine.dao.VendingMachineDAO;
import com.example.vendingmachine.models.OrderList;
import com.example.vendingmachine.models.Product;
import com.example.vendingmachine.models.VendingMachine;
import com.example.vendingmachine.service.VendingMachineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@AllArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost"})
@RequestMapping("/vendingMachine")
public class VendingMachineController {
    private ProductDAO productDAO;
    private VendingMachineDAO vendingMachineDAO;
    private VendingMachineService vendingMachineService;

    @PostMapping("/addVendingMachine")
    public void createVendingMachine(@RequestBody VendingMachine vendingMachine) {
        System.out.println(vendingMachine);
        vendingMachineDAO.save(vendingMachine);
    }

    @GetMapping("/getVendingMachine")
    public List<VendingMachine> getVendingMachine() {
        return vendingMachineDAO.findAll();
    }


    @PostMapping("/addCategory")
    public void addCategory(@RequestBody Product product) {
        vendingMachineService.addCategory(product);
    }


    @PostMapping("/addItem")
    public void addItem(@RequestBody Product product) {
        vendingMachineService.addItem(product);
    }


    @PostMapping("/purchase")
    public void purchase(@RequestBody OrderList orderList) {
        vendingMachineService.purchase(orderList);
    }


    @GetMapping("/list")
    public List<Product> list() {
        return vendingMachineService.productsList();
    }


    @PostMapping("/clear")
    public void clear(@RequestBody Product product) {
        vendingMachineService.clear(product);
    }


    @GetMapping("/report/{year}/{month}/{day}")
    public  List<String> orderListByYearAndMonth(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
        System.out.println(year + " " + month + " " + day);
        return vendingMachineService.report(year, month, day);
    }

}
