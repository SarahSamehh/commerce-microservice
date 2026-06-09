package com.example.order_service.controller;

import com.example.order_service.dto.CreateOrderRequest;
import com.example.order_service.entity.Order;
import com.example.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request){
       return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }
}
