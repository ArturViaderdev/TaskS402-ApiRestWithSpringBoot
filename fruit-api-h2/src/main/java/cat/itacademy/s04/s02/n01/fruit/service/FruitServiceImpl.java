package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitMapper;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService {
    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public FruitResponseDTO createFruit(FruitRequestDTO fruit) {
        return FruitMapper.toDTO(fruitRepository.save(FruitMapper.toEntity(fruit)));
    }

    @Override
    public List<FruitResponseDTO> readAllFruits() {
       return fruitRepository.findAll()
               .stream()
               .map(FruitMapper::toDTO)
               .toList();
    }

    @Override
    public FruitResponseDTO getFruitById(Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        if (fruit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return FruitMapper.toDTO(fruit.get());

    }

    @Override
    public FruitResponseDTO updateFruit(FruitRequestDTO fruit, Long id) {
        if (!fruitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Fruit fruitEntity = FruitMapper.toEntity(fruit);
        fruitEntity.setId(id);
        return FruitMapper.toDTO(fruitRepository.save(fruitEntity));
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
