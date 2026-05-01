package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService {
    FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Fruit createFruit(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    @Override
    public List<Fruit> readAllFruits() {
        List<Fruit> fruits = fruitRepository.findAll();
        return fruits;
    }

    @Override
    public Fruit getFruitById(Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        if (fruit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return fruit.get();
        }
    }

    @Override
    public Fruit updateFruit(Fruit fruit, Long id) {
        if (!fruitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        fruit.setId(id);
        return fruitRepository.save(fruit);
    }

    @Override
    public void deleteFruit(Long id) {
        if (!(fruitRepository.existsById(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruita no trobada.");
        }
        fruitRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        fruitRepository.deleteAll();
    }
}
