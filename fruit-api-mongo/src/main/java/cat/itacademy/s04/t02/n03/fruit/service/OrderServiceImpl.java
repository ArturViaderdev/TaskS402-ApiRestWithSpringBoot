package cat.itacademy.s04.t02.n03.fruit.service;

import cat.itacademy.s04.t02.n03.fruit.exception.ClientNameIsEmptyException;
import cat.itacademy.s04.t02.n03.fruit.exception.FruitsEmptyException;
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

    public Order createOrder(Order order)
    {
        if(order.getClientName().isEmpty())
        {
            throw new ClientNameIsEmptyException();
        }
        if(order.getItems().size()==0)
        {
            throw new FruitsEmptyException();
        }
        Order orderToSave = new Order();
        orderToSave.setClientName( order.getClientName());
        orderToSave.setDeliveryDate(order.getDeliveryDate());
        orderToSave.setItems(order.getItems());
        return orderRepository.save(orderToSave);
    }

    @Override
    public Order findById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty())
        {
            throw new OrderNotFoundException();
        }
        return order.get();
    }

    @Override
    public List<Order> readAllOrders() {
        return orderRepository.findAll();
    }
}
