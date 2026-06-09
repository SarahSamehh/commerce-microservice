package com.example.order_service.service;

import com.example.order_service.dto.CreateOrderRequest;
import com.example.order_service.dto.ProductDto;
import com.example.order_service.entity.Order;
import com.example.order_service.feign.ProductClient;
import com.example.order_service.feign.UserClient;
import com.example.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;

    private static final Logger logger =
            LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository , UserClient userClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    public Order createOrder(CreateOrderRequest request){
        logger.info("Creating order for userId: {}", request.userId());

        //validate user exists
        userClient.getUser(request.userId());

        logger.debug("User exists");

        //get product
        ProductDto product = productClient.getProduct(request.productId());

        logger.debug("product exists");

        //check stock
        if (product.stock() < request.quantity()) {
            logger.error("Insufficient stock for product {}", product.id());
            throw new RuntimeException("Insufficient stock");
        }


        //calculate total price
        double total = product.price() * request.quantity();

        logger.info("Order total calculated: {}", total);


        //save order
        Order order = new Order();
        order.setUserId(request.userId());
        order.setProductId(request.productId());
        order.setQuantity(request.quantity());
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        logger.info("Order created successfully with id: {}", savedOrder.getOrderId());


        return savedOrder;
    }

    public Order getOrder(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
