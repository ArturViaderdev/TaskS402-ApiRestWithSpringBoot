package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.IncorrectWeightException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

@Service
public class FruitServiceImpl implements FruitService{
    FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository)
    {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Fruit createFruit(Fruit fruit) throws FruitNameIsEmpty, IncorrectWeightException {
        if(fruit.getName().isEmpty())
        {
            throw new FruitNameIsEmpty();
        }
        if(fruit.getWeightInKilos()<=0)
        {
            throw new IncorrectWeightException();
        }
        fruit = fruitRepository.save(fruit);
        return fruit;
    }
}
