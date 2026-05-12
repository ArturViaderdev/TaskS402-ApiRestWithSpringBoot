package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Fruit;

import java.util.List;

public interface FruitService {
    Fruit createFruit(Fruit fruit, Long idProvider) throws FruitNameIsEmpty, ProviderNotFound;
    List<Fruit> readAllFruits(long providerId) throws ProviderNotFound;
    Fruit getFruitById(Long id);
    Fruit updateFruit(Fruit fruit, Long id, Long provider);
    void deleteFruit(Long id);
}
