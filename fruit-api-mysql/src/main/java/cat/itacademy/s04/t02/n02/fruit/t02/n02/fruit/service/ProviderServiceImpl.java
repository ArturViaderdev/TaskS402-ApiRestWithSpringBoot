package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.ProviderMapper;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository providerRepository;
    private final FruitRepository fruitRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository, FruitRepository fruitRepository) {
        this.providerRepository = providerRepository;
        this.fruitRepository = fruitRepository;
    }

    @Override
    public ProviderResponseDTO createProvider(ProviderRequestDTO provider) throws ProviderNameIsEmpty, ProviderNameAlreadyExists {
        if (provider.name().isEmpty()) {
            throw new ProviderNameIsEmpty();
        }
        Optional<Provider> providerName = providerRepository.findByName(provider.name());
        if ((providerName.isPresent())) {
            throw new ProviderNameAlreadyExists();
        }
        return ProviderMapper.toDTO(providerRepository.save(ProviderMapper.toEntity(provider)));
    }

    @Override
    public ProviderResponseDTO updateProvider(ProviderRequestDTO provider, Long idProvider) throws ProviderNotFound {
        Optional<Provider> existProvider = providerRepository.findById(idProvider);
        if (existProvider.isEmpty()) {
            throw new ProviderNotFound();
        }
        Optional<Provider> providerName = providerRepository.findByName(provider.name());
        if ((providerName.isPresent())) {
            if (!(providerName.get().getId().equals(idProvider))) {
                throw new ProviderNameAlreadyExists();
            }
        }
        Provider saveProvider = ProviderMapper.toEntity(provider);
        saveProvider.setId(idProvider);
        return ProviderMapper.toDTO(providerRepository.save(saveProvider));
    }

    @Override
    public void deleteProvider(Long idProvider) throws ProviderNotFound, ProviderHasFruits {
        Optional<Provider> existProvider = providerRepository.findById(idProvider);
        if (existProvider.isEmpty()) {
            throw new ProviderNotFound();
        }
        List<Fruit> fruits = fruitRepository.findByProviderId(idProvider);
        if (!(fruits.isEmpty())) {
            throw new ProviderHasFruits();
        }
        providerRepository.deleteById(idProvider);
    }

    @Override
    public List<ProviderResponseDTO> getProviders() {
        return providerRepository.findAll().stream().map(ProviderMapper::toDTO).toList();
    }
}
