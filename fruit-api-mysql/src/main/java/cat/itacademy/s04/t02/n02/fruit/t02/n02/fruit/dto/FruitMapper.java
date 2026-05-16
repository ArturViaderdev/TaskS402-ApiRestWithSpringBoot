package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Provider;

public class FruitMapper {
    public static FruitResponseDTO toDTO(Fruit fruit) {
        ProviderResponseDTO providerDTO = ProviderMapper.toDTO(fruit.getProvider());
        return new FruitResponseDTO(fruit.getId(), fruit.getName(), fruit.getWeightInKilos(), providerDTO);
    }

    public static Fruit toEntity(FruitRequestDTO dto, Provider provider) {
        Fruit fruit = new Fruit();
        fruit.setName(dto.name());
        fruit.setWeightInKilos(dto.weightInKilos());
        fruit.setProvider(provider);
        return fruit;
    }

    public static void updateEntity(Fruit fruit, FruitRequestDTO dto, Provider provider) {
        fruit.setName(dto.name());
        fruit.setWeightInKilos(dto.weightInKilos());
        fruit.setProvider(provider);
    }
}
