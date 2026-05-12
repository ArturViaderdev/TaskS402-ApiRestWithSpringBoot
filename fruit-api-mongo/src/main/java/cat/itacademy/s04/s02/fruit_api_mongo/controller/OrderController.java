package cat.itacademy.s04.s02.fruit_api_mongo.controller;

import cat.itacademy.s04.s02.fruit_api_mongo.model.Order;
import cat.itacademy.s04.s02.fruit_api_mongo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        Order savedOrder = orderService.createOrder(order);
        return savedOrder;
    }
}
