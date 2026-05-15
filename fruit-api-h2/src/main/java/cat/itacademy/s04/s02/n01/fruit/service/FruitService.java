package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

import java.util.List;

public interface FruitService {
    FruitResponseDTO createFruit(FruitRequestDTO fruit);

    List<FruitResponseDTO> readAllFruits();

    FruitResponseDTO getFruitById(Long id);

    FruitResponseDTO updateFruit(FruitRequestDTO fruit, Long id);

    void deleteFruit(Long id);

    void removeAll();
}
