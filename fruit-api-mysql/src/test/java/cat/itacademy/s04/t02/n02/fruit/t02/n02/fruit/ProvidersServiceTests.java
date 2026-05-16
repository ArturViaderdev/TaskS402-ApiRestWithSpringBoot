package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderHasFruits;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNameAlreadyExists;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNameIsEmpty;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.repository.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service.ProviderServiceImpl;
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
    public void createProviderShouldThrowExceptionWhenNameIsEmpty() {
        ProviderRequestDTO provider = new ProviderRequestDTO("", "Spain");

        Assertions.assertThrows(ProviderNameIsEmpty.class, () -> providerService.createProvider(provider));
        verify(providerRepository, never()).save(any(Provider.class));
        verify(providerRepository, never()).findByName(anyString());
    }

    @Test
    public void createProviderShouldThrowExceptionWhenNameAlreadyExists() {
        ProviderRequestDTO provider = new ProviderRequestDTO("Frutero", "Spain");

        Provider secondProvider = new Provider();
        secondProvider.setName("Frutero");
        secondProvider.setCountry("France");

        when(providerRepository.findByName("Frutero")).thenReturn(Optional.of(secondProvider));
        Assertions.assertThrows(ProviderNameAlreadyExists.class, () -> providerService.createProvider(provider));

        verify(providerRepository).findByName("Frutero");
        verify(providerRepository, never()).save(any(Provider.class));

    }

    @Test
    public void createProviderShouldSaveAndReturnProvider() {
        ProviderRequestDTO provider = new ProviderRequestDTO("Vendefrutas", "Spain");
        Long providerId = 1L;
        Provider savedProvider = new Provider();
        savedProvider.setName("Vendefrutas");
        savedProvider.setCountry("Spain");
        savedProvider.setId(providerId);

        when(providerRepository.save(any(Provider.class))).thenReturn(savedProvider);

        ProviderResponseDTO result = providerService.createProvider(provider);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.id());
        Assertions.assertEquals("Vendefrutas", result.name());
        Assertions.assertEquals("Spain", result.country());
        verify(providerRepository).save(any(Provider.class));
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
        List<ProviderResponseDTO> result = providerService.getProviders();
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
    public void deleteProviderShouldThrowExceptionWhenHavesFruits() {
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
        verify(providerRepository, never()).deleteById(anyLong());
    }

    @Test
    public void updateProviderShouldReturnProvider() {
        Long providerId = 1L;
        Provider existing = new Provider();
        existing.setId(1L);
        existing.setName("Frutero");
        existing.setCountry("Spain");

        ProviderRequestDTO updated = new ProviderRequestDTO("Frutera", "France");


        when(providerRepository.findById(providerId)).thenReturn(Optional.of(existing));
        when(providerRepository.save(any(Provider.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ProviderResponseDTO result = providerService.updateProvider(updated, providerId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(providerId, result.id());
        Assertions.assertEquals(updated.name(), result.name());
        Assertions.assertEquals(updated.country(), result.country());
    }

    @Test
    public void updateProviderShouldThrowExceptionWhenNameAlreadyExists() {
        Long providerId = 1L;
        Provider existing = new Provider();
        existing.setId(1L);
        existing.setName("Frutero");
        existing.setCountry("Spain");

        Provider duplicated = new Provider();
        duplicated.setId(2L);
        duplicated.setName("Frutero");


        ProviderRequestDTO updated = new ProviderRequestDTO("Frutero", "France");

        when((providerRepository).findById(providerId)).thenReturn(Optional.of(existing));
        when((providerRepository).findByName("Frutero")).thenReturn(Optional.of(duplicated));

        Assertions.assertThrows(ProviderNameAlreadyExists.class, () -> providerService.updateProvider(updated, providerId));

        verify(providerRepository).findById(providerId);
        verify(providerRepository).findByName("Frutero");
        verify(providerRepository, never()).save(any(Provider.class));
    }

    @Test
    public void updateProviderShouldThrowExceptionWhenIdIsNotFound() {
        ProviderRequestDTO provider = new ProviderRequestDTO("Frutero", "Spain");

        when((providerRepository).findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(ProviderNotFound.class, () -> providerService.updateProvider(provider, 999L));
        verify(providerRepository).findById(999L);
        verify(providerRepository, never()).save(any(Provider.class));
    }
}
