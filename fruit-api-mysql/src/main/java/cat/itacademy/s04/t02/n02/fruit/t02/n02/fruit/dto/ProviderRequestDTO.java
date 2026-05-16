package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto;

import jakarta.validation.constraints.NotBlank;

public record ProviderRequestDTO(
        @NotBlank String name,
        @NotBlank String country
) {
}
