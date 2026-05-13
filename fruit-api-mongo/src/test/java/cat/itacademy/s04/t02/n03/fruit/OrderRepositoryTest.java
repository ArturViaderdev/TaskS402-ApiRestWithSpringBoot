package cat.itacademy.s04.t02.n03.fruit;

import cat.itacademy.s04.t02.n03.fruit.model.Order;
import cat.itacademy.s04.t02.n03.fruit.model.OrderItem;
import cat.itacademy.s04.t02.n03.fruit.repository.OrderRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals(savedOrder.getClientName(),"Pedro");
        Assertions.assertEquals(savedOrder.getItems().size(),1);
        Assertions.assertEquals(savedOrder.getItems().get(0).getFruitName(),"Poma");
        Assertions.assertEquals(savedOrder.getItems().get(0).getQuantityInKilos(),100);
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
        Assertions.assertEquals(2,orders.size());
        Assertions.assertEquals(orders.get(0),order);
        Assertions.assertEquals(orders.get(1),orderb);
    }

    @Test
    public void shouldReturnEmptyList()
    {
        List<Order> orders = orderRepository.findAll();
        Assertions.assertEquals(orders.size(),(0));
    }

    @Test
    public void findByIdExists() {
        Order order = new Order();
        order.setClientName("Pedro");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Poma",100));
        order.setItems(items);
        Order savedOrder = orderRepository.save(order);
        Optional<Order> result = orderRepository.findById(savedOrder.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Pedro", result.get().getClientName());
        Assertions.assertEquals(1, result.get().getItems().size());
        Assertions.assertEquals("Poma",result.get().getItems().get(0).getFruitName());
        Assertions.assertEquals("Poma",result.get().getItems().get(0).getFruitName());
    }

    @Test
    public void findByIdNotExists() {
        Optional<Order> result = orderRepository.findById("999");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void saveUpdateOrder()
    {
        Order order = new Order();
        order.setClientName("Pedro");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Poma",100));
        order.setItems(items);
        Order savedOrder = orderRepository.save(order);
        savedOrder.setClientName("Juan");
        items.add(new OrderItem("Pera",200));
        Order result = orderRepository.save(savedOrder);
        Assertions.assertEquals(savedOrder.getId(),result.getId());
        Assertions.assertEquals(result.getClientName(),"Juan");
        Assertions.assertEquals(result.getItems().size(),2);
        Assertions.assertEquals(result.getItems().get(0).getFruitName(),"Poma");
        Assertions.assertEquals(result.getItems().get(1).getFruitName(),"Pera");
        Assertions.assertEquals(result.getItems().get(0).getQuantityInKilos(),100);
        Assertions.assertEquals(result.getItems().get(1).getQuantityInKilos(),200);
    }
}
