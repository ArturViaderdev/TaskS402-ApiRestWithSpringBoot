package cat.itacademy.s04.t02.n03.fruit.dto;

import cat.itacademy.s04.t02.n03.fruit.model.Order;

import java.util.List;


public class OrderMapper {
    public static OrderResponseDTO toDTO(Order order) {
        List<OrderItemDTO> items = order.getItems().stream().map(OrderItemMapper::toDTO).toList();

        return new OrderResponseDTO(order.getId(), order.getClientName(), order.getDeliveryDate(), items);
    }

    public static Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setClientName(dto.clientName());
        order.setDeliveryDate(dto.deliveryDate());
        order.setItems(dto.items().stream().map(OrderItemMapper::toEntity).toList());
        return order;
    }

    public static void updateEntity(Order order, OrderRequestDTO dto) {
        order.setClientName(dto.clientName());
        order.setDeliveryDate(dto.deliveryDate());
        order.setItems(dto.items().stream().map(OrderItemMapper::toEntity).toList());
    }
}

