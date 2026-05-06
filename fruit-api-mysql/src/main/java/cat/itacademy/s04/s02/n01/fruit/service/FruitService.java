package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

import java.util.List;

public interface FruitService {
    Fruit createFruit(Fruit fruit, Long idProvider) throws FruitNameIsEmpty, ProviderNotFound;
    List<Fruit> readAllFruits(long providerId) throws ProviderNotFound;
    Fruit getFruitById(Long id);
    Fruit updateFruit(Fruit fruit, Long id, Long provider);
    void deleteFruit(Long id);
}
