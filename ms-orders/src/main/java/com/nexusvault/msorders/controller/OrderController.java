package com.nexusvault.msorders.controller;

import com.nexusvault.msorders.model.Order;
import com.nexusvault.msorders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll(); // Esto traerá la orden y sus items!
    }
}