package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.IncorrectWeightException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Fruit> readAllFruits() {
        List<Fruit> fruits = fruitRepository.findAll();
        return fruits;
    }

    @Override
    public Optional<Fruit> getFruitById(Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        return fruit;
    }

    @Override
    public Optional<Fruit> updateFruit(Fruit fruit, Long id) {
        if(fruitRepository.existsById(id))
        {
            fruit.setId(id);
            fruitRepository.save(fruit);
            return Optional.of(fruit);
        }
        else
        {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteFruit(Long id) {
        if(fruitRepository.existsById(id))
        {
            fruitRepository.deleteById(id);
            return true;
        }
        else
        {
            return false;
        }
    }
}
