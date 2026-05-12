package cat.itacademy.s04.t02.n03.fruit;

import cat.itacademy.s04.t02.n03.fruit.model.Order;
import cat.itacademy.s04.t02.n03.fruit.model.OrderItem;
import cat.itacademy.s04.t02.n03.fruit.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataMongoTest
@ActiveProfiles("test")
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void delete()
    {
        orderRepository.deleteAll();
    }

    @Test
    public void shouldSaveOrder()
    {
        Order order = new Order();
        order.setClientName("Pedro");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Poma",100));
        order.setItems(items);
        Order savedOrder = orderRepository.save(order);
        Assertions.assertThat(savedOrder.getId()).isNotNull();
        Assertions.assertThat(savedOrder.getClientName()).isEqualTo("Pedro");
        Assertions.assertThat(savedOrder.getItems()).isEqualTo(items);
    }

    @Test
    public void shouldReturnAllOrders()
    {
        Order order = new Order();
        order.setClientName("Pedro");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Poma",100));
        order.setItems(items);
        orderRepository.save(order);
        Order orderb = new Order();
        orderb.setClientName("Juan");
        orderb.setDeliveryDate(LocalDate.now());
        List<OrderItem> itemsB = new ArrayList<>();
        itemsB.add(new OrderItem("Pera",200));
        orderb.setItems(items);
        orderRepository.save(orderb);
        List<Order> orders = orderRepository.findAll();
        Assertions.assertThat(orders.size()).isEqualTo(2);
        Assertions.assertThat(orders.get(0)).isEqualTo(order);
        Assertions.assertThat(orders.get(1)).isEqualTo(orderb);
    }

    @Test
    public void shouldReturnEmptyList()
    {
        List<Order> orders = orderRepository.findAll();
        Assertions.assertThat(orders.size()).isEqualTo(0);
    }
}
