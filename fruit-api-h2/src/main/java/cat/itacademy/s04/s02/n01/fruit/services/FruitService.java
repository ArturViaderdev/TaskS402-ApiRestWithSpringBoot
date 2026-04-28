package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.IncorrectWeightException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

public interface FruitService {
    Fruit createFruit(Fruit fruit) throws FruitNameIsEmpty, IncorrectWeightException;
}
