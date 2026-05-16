package cat.itacademy.s04.t02.n03.fruit.service;

import cat.itacademy.s04.t02.n03.fruit.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO order);

    OrderResponseDTO findById(String id);

    List<OrderResponseDTO> readAllOrders();

    OrderResponseDTO getOrderById(String id);

    OrderResponseDTO updateOrder(OrderRequestDTO order, String id);

    void deleteOrder(String id);
}
