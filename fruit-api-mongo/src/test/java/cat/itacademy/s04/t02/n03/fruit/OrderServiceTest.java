package cat.itacademy.s04.t02.n03.fruit;

import cat.itacademy.s04.t02.n03.fruit.dto.*;
import cat.itacademy.s04.t02.n03.fruit.exception.ClientNameIsEmptyException;
import cat.itacademy.s04.t02.n03.fruit.exception.FruitsEmptyException;
import cat.itacademy.s04.t02.n03.fruit.exception.OrderIdDoesNotExists;
import cat.itacademy.s04.t02.n03.fruit.model.Order;
import cat.itacademy.s04.t02.n03.fruit.model.OrderItem;
import cat.itacademy.s04.t02.n03.fruit.repository.OrderRepository;
import cat.itacademy.s04.t02.n03.fruit.service.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void createOrderShouldSaveAndReturnOrder() {
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO("Poma", 100));
        OrderRequestDTO order = new OrderRequestDTO("Pedro", LocalDate.now(), items);
        Order savedOrder = OrderMapper.toEntity(order);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        OrderResponseDTO result = orderService.createOrder(order);
        Assertions.assertEquals("Pedro", result.clientName());
        Assertions.assertEquals(1, result.items().size());
        Assertions.assertEquals("Poma", result.items().get(0).fruitName());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void createOrderShouldThrowExceptionWhenNameIsEmpty() {

        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO("Poma", 100));
        OrderRequestDTO order = new OrderRequestDTO("", LocalDate.now(), items);

        Assertions.assertThrows(ClientNameIsEmptyException.class, () -> orderService.createOrder(order));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void fruitsEmptyException() {
        List<OrderItemDTO> items = new ArrayList<>();
        OrderRequestDTO order = new OrderRequestDTO("Pedro", LocalDate.now(), items);
        Order savedOrder = OrderMapper.toEntity(order);

        Assertions.assertThrows(FruitsEmptyException.class, () -> orderService.createOrder(order));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void readAllOrdersShouldReturnAllOrders() {
        Order orderA = new Order();
        orderA.setId("1");
        orderA.setClientName("Juan");
        orderA.setDeliveryDate(LocalDate.now());
        orderA.setItems(List.of(new OrderItem("Poma", 100), new OrderItem("Pera", 200)));

        Order orderB = new Order();
        orderB.setId("2");
        orderB.setClientName("Pedro");
        orderB.setDeliveryDate(LocalDate.now());
        orderB.setItems(List.of(new OrderItem("Poma", 100)));
        List<Order> orders = List.of(orderA, orderB);
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderResponseDTO> result = orderService.readAllOrders();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Juan", result.get(0).clientName());
        Assertions.assertEquals("Pedro", result.get(1).clientName());
        Assertions.assertEquals(2, result.get(0).items().size());
        Assertions.assertEquals(1, result.get(1).items().size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void readAllOrdersShouldReturnEmptyList() {
        when(orderRepository.findAll()).thenReturn(List.of());
        List<OrderResponseDTO> result = orderService.readAllOrders();
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(0, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void getOrderIdShouldReturnOrder() {
        Order order = new Order();
        order.setClientName("Pedro");
        order.setDeliveryDate(LocalDate.now());
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Poma", 100));
        order.setItems(items);
        order.setId("1");
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));
        OrderResponseDTO result = orderService.getOrderById("1");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1", result.id());
        Assertions.assertEquals("Pedro", result.clientName());
        Assertions.assertEquals(1, result.items().size());
        Assertions.assertEquals("Poma", result.items().get(0).fruitName());
        Assertions.assertEquals(100, result.items().get(0).quantityInKilos());
        verify(orderRepository).findById("1");
    }

    @Test
    public void getOrderIdShouldThrowExceptionWhenNotExists() {
        when(orderRepository.findById("999")).thenReturn(Optional.empty());
        RuntimeException ex = Assertions.assertThrows(OrderIdDoesNotExists.class, () -> orderService.getOrderById("999"));
        verify(orderRepository).findById("999");
    }

    @Test
    public void updateOrderShouldUpdateAndReturnOrderWhenItExists() {
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO("Poma", 100));
        OrderRequestDTO order = new OrderRequestDTO("Pedro", LocalDate.now(), items);

        when(orderRepository.existsById("1")).thenReturn(true);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDTO result = orderService.updateOrder(order, "1");
        Assertions.assertEquals("Pedro", result.clientName());
        Assertions.assertEquals(1, result.items().size());
        Assertions.assertEquals("Poma", result.items().get(0).fruitName());
        Assertions.assertEquals(100, result.items().get(0).quantityInKilos());
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1", result.id());

        verify(orderRepository).existsById("1");
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    public void updateOrderNotFound() {
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO("Poma", 100));
        OrderRequestDTO order = new OrderRequestDTO("Pedro", LocalDate.now(), items);

        when(orderRepository.existsById("999")).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(OrderIdDoesNotExists.class, () -> orderService.updateOrder(order, "999"));
        verify(orderRepository).existsById("999");
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void deleteOrderShouldDeleteWhenIdExists() {
        when(orderRepository.existsById("1")).thenReturn(true);
        orderService.deleteOrder("1");
        verify(orderRepository).existsById("1");
        verify(orderRepository).deleteById("1");
    }

    @Test
    public void deleteOrderShouldThrowNotFoundWhenIdDoesNotExists() {
        when(orderRepository.findById("999")).thenReturn(Optional.empty());
        Assertions.assertThrows(OrderIdDoesNotExists.class, () -> orderService.getOrderById("999"));
        verify(orderRepository).findById("999");
    }
}
