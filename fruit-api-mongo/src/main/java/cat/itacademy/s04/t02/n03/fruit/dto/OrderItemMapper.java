package cat.itacademy.s04.t02.n03.fruit.dto;

import cat.itacademy.s04.t02.n03.fruit.model.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toDTO(OrderItem item) {
        return new OrderItemDTO(item.getFruitName(), item.getQuantityInKilos());
    }

    public static OrderItem toEntity(OrderItemDTO dto) {
        return new OrderItem(dto.fruitName(), dto.quantityInKilos());
    }
}
