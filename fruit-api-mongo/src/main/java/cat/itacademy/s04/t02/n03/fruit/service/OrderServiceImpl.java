package cat.itacademy.s04.t02.n03.fruit.service;

import cat.itacademy.s04.t02.n03.fruit.dto.OrderMapper;
import cat.itacademy.s04.t02.n03.fruit.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n03.fruit.exception.ClientNameIsEmptyException;
import cat.itacademy.s04.t02.n03.fruit.exception.FruitsEmptyException;
import cat.itacademy.s04.t02.n03.fruit.exception.OrderIdDoesNotExists;
import cat.itacademy.s04.t02.n03.fruit.exception.OrderNotFoundException;
import cat.itacademy.s04.t02.n03.fruit.model.Order;
import cat.itacademy.s04.t02.n03.fruit.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO order) {
        if (order.clientName().isEmpty()) {
            throw new ClientNameIsEmptyException();
        }
        if (order.items().size() == 0) {
            throw new FruitsEmptyException();
        }
        Order orderToSave = OrderMapper.toEntity(order);

        return OrderMapper.toDTO(orderRepository.save(orderToSave));
    }

    @Override
    public OrderResponseDTO findById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return OrderMapper.toDTO(order.get());
    }

    @Override
    public List<OrderResponseDTO> readAllOrders() {
        return orderRepository.findAll().stream().map(OrderMapper::toDTO).toList();
    }

    @Override
    public OrderResponseDTO getOrderById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderIdDoesNotExists();
        }
        return OrderMapper.toDTO(order.get());
    }

    @Override
    public OrderResponseDTO updateOrder(OrderRequestDTO order, String id) {
        if (!(orderRepository.existsById(id))) {
            throw new OrderIdDoesNotExists();
        }
        Order orderEntity = OrderMapper.toEntity(order);
        orderEntity.setId(id);
        return OrderMapper.toDTO(orderRepository.save(orderEntity));

    }

    @Override
    public void deleteOrder(String id) {
        if (!(orderRepository.existsById(id))) {
            throw new OrderIdDoesNotExists();
        }
        orderRepository.deleteById(id);
    }
}
