package com.example.vendingmachine.dao;

import com.example.vendingmachine.models.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendingMachineDAO extends JpaRepository<VendingMachine,Integer> {
}
