package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.FruitMapper;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.FruitIdDoesNotExists;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService {
    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    public FruitServiceImpl(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public FruitResponseDTO createFruit(FruitRequestDTO fruit, Long idProvider) throws FruitNameIsEmpty, ProviderNotFound {
        Optional<Provider> provider = providerRepository.findById(idProvider);
        if (provider.isEmpty()) {
            throw new ProviderNotFound();
        }
        if (fruit.name().isEmpty()) {
            throw new FruitNameIsEmpty();
        }
        Fruit fruitToSave = FruitMapper.toEntity(fruit,provider.get());

        return FruitMapper.toDTO(fruitRepository.save(fruitToSave));
    }

    @Override
    public List<FruitResponseDTO> readAllFruits(long providerId) throws ProviderNotFound {
        Optional<Provider> provider = providerRepository.findById(providerId);
        if (provider.isEmpty()) {
            throw new ProviderNotFound();
        }
        return fruitRepository.findByProviderId(providerId).stream().map(FruitMapper::toDTO).toList();
    }

    @Override
    public FruitResponseDTO getFruitById(Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        if (fruit.isEmpty()) {
            throw new FruitIdDoesNotExists();
        }
        return FruitMapper.toDTO(fruit.get());
    }

    @Override
    public FruitResponseDTO updateFruit(FruitRequestDTO fruit, Long id, Long provider) {
        if (!(fruitRepository.existsById(id))) {
            throw new FruitIdDoesNotExists();
        }

        Optional<Provider> providerObject = providerRepository.findById(provider);
        if (providerObject.isEmpty()) {
            throw new ProviderNotFound();
        }

        Fruit saveFruit = FruitMapper.toEntity(fruit,providerObject.get());
        saveFruit.setId(id);
        return FruitMapper.toDTO(fruitRepository.save(saveFruit));
    }

    @Override
    public void deleteFruit(Long id) {
        if (!(fruitRepository.existsById(id))) {
            throw new FruitIdDoesNotExists();
        }
        fruitRepository.deleteById(id);
    }
}
