package cat.itacademy.s04.s02.n01.fruit.dto;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

public class FruitMapper {
    public static FruitResponseDTO toDTO(Fruit fruit) {
        return new FruitResponseDTO(fruit.getId(), fruit.getName(), fruit.getWeightInKilos());
    }

    public static Fruit toEntity(FruitRequestDTO dto) {
        Fruit fruit = new Fruit();
        fruit.setName(dto.name());
        fruit.setWeightInKilos(dto.weightInKilos());
        return fruit;
    }

    public static void updateEntity(Fruit fruit, FruitRequestDTO dto) {
        fruit.setName(dto.name());
        fruit.setWeightInKilos(dto.weightInKilos());
    }
}
