package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitIdDoesNotExists;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import cat.itacademy.s04.s02.n01.fruit.service.FruitServiceImpl;
import cat.itacademy.s04.s02.n01.fruit.service.ProviderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitsServiceTests {
    @Mock
    private FruitRepository fruitRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Test
    public void createFruitShouldSaveAndReturnFruit() {
        Provider provider = new Provider();
        provider.setName("Vendefrutas");
        provider.setCountry("Spain");
        Long providerId=1L;
        provider.setId(providerId);

        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);

        Fruit savedFruit = new Fruit();
        savedFruit.setId(1L);
        savedFruit.setName("Poma");
        savedFruit.setWeightInKilos(200);
        savedFruit.setProvider(provider);

        when(providerRepository.findById(providerId)).thenReturn(Optional.of(provider));
        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedFruit);

        Fruit result = fruitService.createFruit(fruit,providerId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(provider,result.getProvider());
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Poma", result.getName());
        Assertions.assertEquals(200, result.getWeightInKilos());
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    public void readAllFruitsShouldReturnFruits() {
        Provider provider = new Provider();
        provider.setName("Vendefrutas");
        provider.setCountry("Spain");
        Long providerId=1L;
        provider.setId(providerId);

        Fruit fruit = new Fruit();
        fruit.setId(1L);
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        fruit.setProvider(provider);
        Fruit secondFruit = new Fruit();
        secondFruit.setId(2L);
        fruit.setName("Pera");
        fruit.setWeightInKilos(300);
        fruit.setProvider(provider);
        when(providerRepository.findById(providerId)).thenReturn(Optional.of(provider));
        when(fruitRepository.findByProviderId(providerId)).thenReturn(List.of(fruit, secondFruit));
        List<Fruit> result = fruitService.readAllFruits(providerId);
        Assertions.assertEquals(2, result.size());
        verify(fruitRepository).findByProviderId(providerId);
    }

    @Test
    public void getFruitIdShouldReturnFruit() {
        Provider provider = new Provider();
        provider.setName("Vendefrutas");
        provider.setCountry("Spain");
        Long providerId=1L;
        provider.setId(providerId);

        Fruit fruit = new Fruit();
        fruit.setId(1L);
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        fruit.setProvider(provider);
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));
        Fruit result = fruitService.getFruitById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Poma", result.getName());
        Assertions.assertEquals(200, result.getWeightInKilos());
        verify(fruitRepository).findById(1L);
    }

    @Test
    public void getFruitIdShouldThrowExceptionWhenNotExists() {
        when(fruitRepository.findById(999L)).thenReturn(Optional.empty());
        RuntimeException ex = Assertions.assertThrows(FruitIdDoesNotExists.class, () -> fruitService.getFruitById(999L));
        verify(fruitRepository).findById(999L);
    }

    @Test
    public void deleteFruitShouldDeleteWhenIdExists() {
        when(fruitRepository.existsById(1L)).thenReturn(true);
        fruitService.deleteFruit(1L);
        verify(fruitRepository).existsById(1L);
        verify(fruitRepository).deleteById(1L);
    }

    @Test
    public void deleteFruitShouldThrowNotFoundWhenIdDoesNotExists() {
        when(fruitRepository.findById(999L)).thenReturn(Optional.empty());
        Assertions.assertThrows(FruitIdDoesNotExists.class, () -> fruitService.getFruitById(999L));
        verify(fruitRepository).findById(999L);
    }

    @Test
    public void updateFruitShouldUpdateAndReturnFruitWhenItExists() {
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);

        Long providerId=1L;
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        provider.setId(providerId);

        when(fruitRepository.existsById(1L)).thenReturn(true);
        when(providerRepository.findById(providerId)).thenReturn(Optional.of(provider));

        when(fruitRepository.save(any(Fruit.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Fruit result = fruitService.updateFruit(fruit, 1L,providerId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Poma", result.getName());
        Assertions.assertEquals(200, result.getWeightInKilos());
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(provider,result.getProvider());
        verify(fruitRepository).existsById(1L);
        verify(providerRepository).findById(providerId);
        verify(fruitRepository).save(fruit);
    }

    @Test
    public void updateFruitNotFound() {
        Fruit fruit = new Fruit();
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);

        Long providerId=1L;
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");
        provider.setId(providerId);

        when(fruitRepository.existsById(999L)).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(FruitIdDoesNotExists.class, () -> fruitService.updateFruit(fruit, 999L,providerId));
        verify(fruitRepository).existsById(999L);
        verify(fruitRepository, never()).save(any(Fruit.class));
    }


}
