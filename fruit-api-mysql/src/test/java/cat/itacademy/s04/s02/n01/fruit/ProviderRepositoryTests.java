package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProviderRepositoryTests {
    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Test
    public void newProviderTest()
    {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);
        Assertions.assertNotNull(savedProvider.getId());
        Assertions.assertEquals(savedProvider.getName(),"Frutero");
        Assertions.assertEquals(savedProvider.getCountry(),"Spain");
    }

    @Test
    public void findProviderByIdTest()
    {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);
        Optional<Provider> readed = providerRepository.findById(savedProvider.getId());
        Assertions.assertTrue(readed.isPresent());
        Assertions.assertTrue(readed.get().getName() . equals(provider.getName()));
        Assertions.assertTrue(readed.get().getCountry().equals(provider.getCountry()));
    }

    @Test
    public void findProvider4IdDoesNotExists()
    {
        Optional<Provider> readed = providerRepository.findById(999L);
        Assertions.assertTrue(readed.isEmpty());
    }

    @Test
    public void deleteById()
    {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Provider savedProvider = providerRepository.save(provider);
        providerRepository.deleteById(savedProvider.getId());
        Optional<Provider> readed = providerRepository.findById(savedProvider.getId());
        Assertions.assertTrue(readed.isEmpty());
    }

    @Test
    public void findProviderByName()
    {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");

        Provider savedProvider = providerRepository.save(provider);

        Optional<Provider> readed = providerRepository.findByName("Frutero");
        Assertions.assertTrue(readed.isPresent());
        Assertions.assertEquals(readed.get().getName(),"Frutero");
    }

    @Test
    public void findProviderByNameNotFound()
    {
        Optional<Provider> readed = providerRepository.findByName("Caja");
        Assertions.assertTrue(readed.isEmpty());
    }
}
