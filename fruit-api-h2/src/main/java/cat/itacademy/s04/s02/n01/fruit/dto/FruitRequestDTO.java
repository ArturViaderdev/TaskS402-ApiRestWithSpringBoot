package cat.itacademy.s04.s02.n01.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FruitRequestDTO(
    @NotBlank String name,
    @Positive int weightInKilos)
    {

    }
