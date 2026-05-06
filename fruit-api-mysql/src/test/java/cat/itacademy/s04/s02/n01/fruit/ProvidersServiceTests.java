package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.exception.*;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
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
public class ProvidersServiceTests {
    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Mock
    private FruitRepository fruitRepository;

    @Test
    public void createProviderShouldThrowExceptionWhenNameIsEmpty()
    {
        Provider provider = new Provider();
        provider.setName("");
        provider.setCountry("Spain");
        Assertions.assertThrows(ProviderNameIsEmpty.class, () -> providerService.createProvider(provider));
        verify(providerRepository,never()).save(any(Provider.class));
        verify(providerRepository,never()).findByName(anyString());
    }

    @Test
    public void createProviderShouldThrowExceptionWhenNameAlreadyExists()
    {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");

        Provider secondProvider = new Provider();
        secondProvider.setName("Frutero");
        secondProvider.setCountry("France");

        when(providerRepository.findByName("Frutero")).thenReturn(Optional.of(secondProvider));
        Assertions.assertThrows(ProviderNameAlreadyExists.class, () -> providerService.createProvider(provider));

        verify(providerRepository).findByName("Frutero");
        verify(providerRepository,never()).save(any(Provider.class));

    }

    @Test
    public void createProviderShouldSaveAndReturnProvider() {
        Provider provider = new Provider();
        provider.setName("Vendefrutas");
        provider.setCountry("Spain");
        Long providerId=1L;
        Provider savedProvider = new Provider();
        savedProvider.setName("Vendefrutas");
        savedProvider.setCountry("Spain");
        savedProvider.setId(providerId);

        when(providerRepository.save(any(Provider.class))).thenReturn(savedProvider);

        Provider result = providerService.createProvider(provider);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Vendefrutas", result.getName());
        Assertions.assertEquals("Spain", result.getCountry());
        verify(providerRepository).save(provider);
    }

    @Test
    public void readAllProvidersShouldReturnProviders() {
        Provider provider = new Provider();
        provider.setName("Vendefrutas");
        provider.setCountry("Spain");

        provider.setId(1L);

        Provider secondProvider = new Provider();
        secondProvider.setName("Frutero");
        secondProvider.setCountry("France");
        secondProvider.setId(2L);

        when(providerRepository.findAll()).thenReturn(List.of(provider, secondProvider));
        List<Provider> result = providerService.getProviders();
        Assertions.assertEquals(2, result.size());
        verify(providerRepository).findAll();
    }

    @Test
    public void deleteProviderShouldDeleteWhenIdExists() {
        when(providerRepository.findById(1L)).thenReturn(Optional.of(new Provider()));
        when(fruitRepository.findByProviderId(1L)).thenReturn(List.of());
        providerService.deleteProvider(1L);
        verify(providerRepository).findById(1L);
        verify(fruitRepository).findByProviderId(1L);
        verify(providerRepository).deleteById(1L);
    }

    @Test
    public void deleteProviderShouldThrowNotFoundWhenIdDoesNotExists() {
        when(providerRepository.findById(999L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProviderNotFound.class, () -> providerService.deleteProvider(999L));
        verify(providerRepository).findById(999L);
    }

    @Test
    public void deleteProviderShouldThrowExceptionWhenHavesFruits()
    {
        Provider provider = new Provider();
        Long providerId = 1L;
        provider.setId(providerId);
        provider.setName("Frutero");
        provider.setCountry("Spain");
        Fruit fruit = new Fruit();
        fruit.setId(1L);
        fruit.setName("Manzana");
        fruit.setWeightInKilos(100);
        fruit.setProvider(provider);

        when(providerRepository.findById(providerId)).thenReturn(Optional.of(provider));
        when(fruitRepository.findByProviderId(1L)).thenReturn(List.of(fruit));
        Assertions.assertThrows(ProviderHasFruits.class, () -> providerService.deleteProvider(providerId));
        verify(providerRepository).findById(providerId);
        verify(fruitRepository).findByProviderId(providerId);
        verify(providerRepository,never()).deleteById(anyLong());
    }

    @Test
    public void updateProviderShouldReturnProvider()
    {
        Long providerId=1L;
        Provider existing = new Provider();
        existing.setId(1L);
        existing.setName("Frutero");
        existing.setCountry("Spain");

        Provider updated = new Provider();
        updated.setName("Frutera");
        updated.setCountry("France");

        when(providerRepository.findById(providerId)).thenReturn(Optional.of(existing));
        when(providerRepository.save(any(Provider.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Provider result = providerService.updateProvider(updated,providerId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(providerId,result.getId());
        Assertions.assertEquals(updated.getName(),result.getName());
        Assertions.assertEquals(updated.getCountry(),result.getCountry());
    }

    @Test
    public void updateProviderShouldThrowExceptionWhenNameAlreadyExists()
    {
        Long providerId=1L;
        Provider existing = new Provider();
        existing.setId(1L);
        existing.setName("Frutero");
        existing.setCountry("Spain");

        Provider duplicated = new Provider();
        duplicated.setId(2L);
        duplicated.setName("Frutero");


        Provider updated = new Provider();
        updated.setName("Frutero");
        updated.setCountry("France");

        when((providerRepository).findById(providerId)).thenReturn(Optional.of(existing));
        when((providerRepository).findByName("Frutero")).thenReturn(Optional.of(duplicated));

        Assertions.assertThrows(ProviderNameAlreadyExists.class,()->providerService.updateProvider(updated,providerId));

        verify(providerRepository).findById(providerId);
        verify(providerRepository).findByName("Frutero");
        verify(providerRepository,never()).save(any(Provider.class));
    }

    @Test
    public void updateProviderShouldThrowExceptionWhenIdIsNotFound()
    {
        Provider provider = new Provider();
        provider.setName("Frutero");
        provider.setCountry("Spain");

        when((providerRepository).findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(ProviderNotFound.class, () -> providerService.updateProvider(provider, 999L));
        verify(providerRepository).findById(999L);
        verify(providerRepository, never()).save(any(Provider.class));
    }
}
