package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.IncorrectWeightException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

import java.util.List;
import java.util.Optional;

public interface FruitService {
    Fruit createFruit(Fruit fruit) throws FruitNameIsEmpty, IncorrectWeightException;
    List<Fruit> readAllFruits();
    Optional<Fruit> getFruitById(Long id);
    Optional<Fruit> updateFruit(Fruit fruit, Long id);
    boolean deleteFruit(Long id);
}
