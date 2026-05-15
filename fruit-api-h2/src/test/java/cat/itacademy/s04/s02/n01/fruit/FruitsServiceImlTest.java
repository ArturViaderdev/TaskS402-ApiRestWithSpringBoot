package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitMapper;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.service.FruitServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitsServiceImlTest {
    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;

    @Test
    public void createFruitShouldSaveAndReturnFruit() {
        FruitRequestDTO fruit = new FruitRequestDTO("Poma",200);
        Fruit savedFruit = new Fruit();
        savedFruit.setId(1L);
        savedFruit.setName("Poma");
        savedFruit.setWeightInKilos(200);
        when(fruitRepository.save(FruitMapper.toEntity(fruit))).thenReturn(savedFruit);
        FruitResponseDTO result = fruitService.createFruit(fruit);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.id());
        Assertions.assertEquals("Poma", result.name());
        Assertions.assertEquals(200, result.weightInKilos());
        verify(fruitRepository).save(FruitMapper.toEntity(fruit));
    }

    @Test
    public void readAllFruitsShouldReturnFruits() {
        Fruit fruit = new Fruit();
        fruit.setId(1L);
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        Fruit secondFruit = new Fruit();
        secondFruit.setId(2L);
        fruit.setName("Pera");
        fruit.setWeightInKilos(300);
        when(fruitRepository.findAll()).thenReturn(List.of(fruit, secondFruit));
        List<FruitResponseDTO> result = fruitService.readAllFruits();
        Assertions.assertEquals(2, result.size());
        verify(fruitRepository).findAll();
    }

    @Test
    public void getFruitIdShouldReturnFruit() {
        Fruit fruit = new Fruit();
        fruit.setId(1L);
        fruit.setName("Poma");
        fruit.setWeightInKilos(200);
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));
        FruitResponseDTO result = fruitService.getFruitById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.id());
        Assertions.assertEquals("Poma", result.name());
        Assertions.assertEquals(200, result.weightInKilos());
        verify(fruitRepository).findById(1L);
    }

    @Test
    public void getFruitIdShouldThrowExceptionWhenNotExists() {
        when(fruitRepository.findById(999L)).thenReturn(Optional.empty());
        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class, () -> fruitService.getFruitById(999L));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(fruitRepository).findById(999L);
    }

    @Test
    public void deleteFruitShouldDeleteWhenIdExists() {
        when(fruitRepository.existsById(1L)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> fruitService.deleteFruit(1L));
        verify(fruitRepository).existsById(1L);
        verify(fruitRepository).deleteById(1L);
    }

    @Test
    public void deleteFruitShouldThrowNotFoundWhenIdDoesNotExists() {
        when(fruitRepository.existsById(999L)).thenReturn(false);
        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class, () -> fruitService.deleteFruit(999L));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(fruitRepository).existsById(999L);
        verify(fruitRepository, never()).deleteById(anyLong());
    }

    @Test
    public void removeAllShouldCallDeleteAll() {
        fruitService.removeAll();
        verify(fruitRepository).deleteAll();
    }

    @Test
    public void updateFruitShouldUpdateAndReturnFruitWhenItExists() {
        FruitRequestDTO dto = new FruitRequestDTO("Manzana",300);

        Fruit savedFruit = new Fruit();
        savedFruit.setId(1L);
        savedFruit.setName("Manzana");
        savedFruit.setWeightInKilos(300);

        when(fruitRepository.existsById(1L)).thenReturn(true);
        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedFruit);
        FruitResponseDTO result = fruitService.updateFruit(dto,1L);
        Assertions.assertEquals(1L,result.id());
        Assertions.assertEquals("Manzana",result.name());
        Assertions.assertEquals(300,result.weightInKilos());
        verify(fruitRepository).existsById(1L);
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    public void updateFruitNotFound() {
        FruitRequestDTO fruit = new FruitRequestDTO("Poma",200);
        when(fruitRepository.existsById(999L)).thenReturn(false);
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> fruitService.updateFruit(fruit, 999L));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(fruitRepository).existsById(999L);
        verify(fruitRepository, never()).save(any(Fruit.class));
    }
}


