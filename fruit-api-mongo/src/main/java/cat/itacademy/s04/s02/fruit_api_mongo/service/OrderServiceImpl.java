package cat.itacademy.s04.s02.fruit_api_mongo.service;

import cat.itacademy.s04.s02.fruit_api_mongo.exception.ClientNameIsEmptyException;
import cat.itacademy.s04.s02.fruit_api_mongo.exception.FruitsEmptyException;
import cat.itacademy.s04.s02.fruit_api_mongo.exception.OrderNotFoundException;
import cat.itacademy.s04.s02.fruit_api_mongo.model.Order;
import cat.itacademy.s04.s02.fruit_api_mongo.repository.OrderRepository;
import org.springframework.stereotype.Service;

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
}
