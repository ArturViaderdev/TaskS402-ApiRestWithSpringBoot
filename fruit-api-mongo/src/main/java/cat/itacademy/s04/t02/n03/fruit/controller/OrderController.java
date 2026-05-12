package cat.itacademy.s04.t02.n03.fruit.controller;

import cat.itacademy.s04.t02.n03.fruit.model.Order;
import cat.itacademy.s04.t02.n03.fruit.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> postOrder(@RequestBody Order order) {
        Order savedOrder = orderService.createOrder(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedOrder.getId()).toUri();
        return ResponseEntity.created(location).body(savedOrder);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrders(){
        return orderService.readAllOrders();
    }
}
