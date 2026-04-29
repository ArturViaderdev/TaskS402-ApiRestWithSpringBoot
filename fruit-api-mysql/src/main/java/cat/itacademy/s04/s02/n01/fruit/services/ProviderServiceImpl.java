package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.ProviderHasFruits;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNameAlreadyExists;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService{
    ProviderRepository providerRepository;
    FruitRepository fruitRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository, FruitRepository fruitRepository)
    {
        this.providerRepository = providerRepository;
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Provider createProvider(Provider provider) throws ProviderNameIsEmpty, ProviderNameAlreadyExists {
        if(provider.getName().isEmpty())
        {
            throw new ProviderNameIsEmpty();
        }
        if(!(providerRepository.findByName(provider.getName()).isEmpty()))
        {
            throw new ProviderNameAlreadyExists();
        }
        provider = providerRepository.save(provider);
        return provider;
    }

    @Override
    public Provider updateProvider(Provider provider, Long idProvider) throws ProviderNotFound {
        Optional<Provider> existProvider = providerRepository.findById(idProvider);
        if(existProvider.isEmpty())
        {
            throw new ProviderNotFound();
        }
        provider.setId(existProvider.get().getId());
        providerRepository.save(provider);
        return provider;
    }

    @Override
    public void deleteProvider(Long idProvider) throws ProviderNotFound, ProviderHasFruits {
        Optional<Provider> existProvider = providerRepository.findById(idProvider);
        if(existProvider.isEmpty())
        {
            throw new ProviderNotFound();
        }
        List<Fruit> fruits = fruitRepository.findByProviderId(idProvider);
        if(fruits.isEmpty())
        {
            throw new ProviderHasFruits();
        }
        providerRepository.deleteById(idProvider);
    }

    @Override
    public List<Provider> getProviders() {
        return providerRepository.findAll();
    }
}
