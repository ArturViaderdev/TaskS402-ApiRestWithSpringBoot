package cat.itacademy.s04.t02.n03.fruit.dto;


import jakarta.validation.constraints.NotBlank;


import java.time.LocalDate;
import java.util.List;

public record OrderRequestDTO(
        @NotBlank String clientName,
        LocalDate deliveryDate,
        List<OrderItemDTO> items
) {
}
