package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class FruitRepositoryTest {
    @Autowired
    private FruitRepository fruitRepository;

    @Test
    void saveFruitTest() {
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        Fruit savedFruit = fruitRepository.save(fruit);
        Assertions.assertNotNull(savedFruit.getId());
        Assertions.assertEquals("Poma", savedFruit.getName());
        Assertions.assertEquals(200, savedFruit.getWeightInKilos());
    }

    @Test
    void findByIdExists() {
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        Fruit savedFruit = fruitRepository.save(fruit);
        Optional<Fruit> result = fruitRepository.findById(savedFruit.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Poma", result.get().getName());
        Assertions.assertEquals(200, result.get().getWeightInKilos());
    }

    @Test
    void findByIdNotExists() {
        Optional<Fruit> result = fruitRepository.findById(999L);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllTest() {
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        Fruit secondFruit = new Fruit();
        secondFruit.setName("Pera");
        secondFruit.setWeightInKilos(100);
        fruitRepository.saveAll(List.of(fruit, secondFruit));
        List<Fruit> result = fruitRepository.findAll();
        List<Fruit> compareList = List.of(fruit,secondFruit);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(result,compareList);
    }

    @Test
    void saveUpdateFruit() {
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        Fruit savedFruit = fruitRepository.save(fruit);
        savedFruit.setName("Pera");
        savedFruit.setWeightInKilos(100);
        Fruit result = fruitRepository.save(savedFruit);
        Assertions.assertEquals(savedFruit.getId(), result.getId());
        Assertions.assertEquals("Pera", result.getName());
        Assertions.assertEquals(100, result.getWeightInKilos());
    }

    @Test
    void deleteByIdTest() {
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        Fruit savedFruit = fruitRepository.save(fruit);
        fruitRepository.deleteById(savedFruit.getId());
        Optional<Fruit> result = fruitRepository.findById(savedFruit.getId());
        Assertions.assertTrue(result.isEmpty());
    }
}
