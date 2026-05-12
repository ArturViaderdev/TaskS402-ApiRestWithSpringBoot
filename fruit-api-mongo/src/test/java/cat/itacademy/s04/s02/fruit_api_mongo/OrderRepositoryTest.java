package cat.itacademy.s04.s02.fruit_api_mongo;

import cat.itacademy.s04.s02.fruit_api_mongo.model.Order;
import cat.itacademy.s04.s02.fruit_api_mongo.model.OrderItem;
import cat.itacademy.s04.s02.fruit_api_mongo.repository.OrderRepository;
import org.assertj.core.api.Assertions;
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
}
