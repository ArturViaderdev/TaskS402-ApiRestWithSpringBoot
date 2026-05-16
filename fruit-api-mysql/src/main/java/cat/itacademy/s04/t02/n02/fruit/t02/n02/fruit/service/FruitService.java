package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNotFound;

import java.util.List;

public interface FruitService {
    FruitResponseDTO createFruit(FruitRequestDTO fruit, Long idProvider) throws FruitNameIsEmpty, ProviderNotFound;

    List<FruitResponseDTO> readAllFruits(long providerId) throws ProviderNotFound;

    FruitResponseDTO getFruitById(Long id);

    FruitResponseDTO updateFruit(FruitRequestDTO fruit, Long id, Long provider);

    void deleteFruit(Long id);
}
