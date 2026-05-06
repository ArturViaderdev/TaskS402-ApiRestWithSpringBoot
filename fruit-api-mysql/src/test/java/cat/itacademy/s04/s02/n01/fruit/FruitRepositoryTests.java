package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FruitRepositoryTests {
    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Test
    void saveFruitTest() {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        fruit.setProvider(savedProvider);
        Fruit savedFruit = fruitRepository.save(fruit);
        Assertions.assertNotNull(savedFruit.getId());
        Assertions.assertEquals("Poma", savedFruit.getName());
        Assertions.assertEquals(200, savedFruit.getWeightInKilos());
    }

    @Test
    void findByIdExists() {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        fruit.setProvider(savedProvider);
        Fruit savedFruit = fruitRepository.save(fruit);
        Optional<Fruit> result = fruitRepository.findById(savedFruit.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Poma", result.get().getName());
        Assertions.assertEquals(200, result.get().getWeightInKilos());
        Assertions.assertEquals(savedProvider,result.get().getProvider());
    }

    @Test
    void findByIdNotExists() {
        Optional<Fruit> result = fruitRepository.findById(999L);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllTest() {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        fruit.setProvider(savedProvider);
        Fruit secondFruit = new Fruit();
        secondFruit.setName("Pera");
        secondFruit.setWeightInKilos(100);
        secondFruit.setProvider(savedProvider);
        fruitRepository.saveAll(List.of(fruit, secondFruit));
        List<Fruit> result = fruitRepository.findByProviderId(savedProvider.getId());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void saveUpdateFruit() {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);

        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        fruit.setProvider(savedProvider);
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
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);

        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        fruit.setProvider(savedProvider);
        Fruit savedFruit = fruitRepository.save(fruit);
        fruitRepository.deleteById(savedFruit.getId());
        Optional<Fruit> result = fruitRepository.findById(savedFruit.getId());
        Assertions.assertTrue(result.isEmpty());
    }
}
