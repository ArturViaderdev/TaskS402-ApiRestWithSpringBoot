package cat.itacademy.s04.t02.n03.fruit.service;

import cat.itacademy.s04.t02.n03.fruit.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order findById(String id);
    List<Order> readAllOrders();
}
