package cat.itacademy.s04.s02.fruit_api_mongo;

import cat.itacademy.s04.s02.fruit_api_mongo.exception.ClientNameIsEmptyException;
import cat.itacademy.s04.s02.fruit_api_mongo.exception.FruitsEmptyException;
import cat.itacademy.s04.s02.fruit_api_mongo.model.Order;
import cat.itacademy.s04.s02.fruit_api_mongo.model.OrderItem;
import cat.itacademy.s04.s02.fruit_api_mongo.repository.OrderRepository;
import cat.itacademy.s04.s02.fruit_api_mongo.service.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void createOrderShouldSaveAndReturnOrder()
    {
        Order order = new Order();
        order.setClientName("Pedro");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Poma",100));
        order.setItems(items);
        order.setId("1");

        when(orderRepository.findById("1")).thenReturn(Optional.of(order));
        Order result = orderService.findById("1");
        Assertions.assertEquals("Pedro",result.getClientName());
    }

    @Test
    public void createOrderShouldThrowExceptionWhenNameIsEmpty() {
        Order order = new Order();
        order.setClientName("");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Poma",100));
        order.setItems(items);
        order.setId("1");
        ClientNameIsEmptyException ex = Assertions.assertThrows(ClientNameIsEmptyException.class, ()-> orderService.createOrder(order));
    }

    @Test
    public void fruitsEmptyException()
    {
        Order order = new Order();
        order.setClientName("Juan");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        order.setItems(items);
        order.setId("1");
        FruitsEmptyException ex = Assertions.assertThrows(FruitsEmptyException.class, ()-> orderService.createOrder(order));
    }
}
