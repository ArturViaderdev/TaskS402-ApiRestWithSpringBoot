package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitIdDoesNotExists;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService{
    FruitRepository fruitRepository;
    ProviderRepository providerRepository;
    public FruitServiceImpl(FruitRepository fruitRepository, ProviderRepository providerRepository)
    {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public Fruit createFruit(Fruit fruit, Long idProvider) throws FruitNameIsEmpty, ProviderNotFound {
        Optional<Provider> provider = providerRepository.findById(idProvider);
        if(provider.isEmpty())
        {
            throw new ProviderNotFound();
        }
        if(fruit.getName().isEmpty())
        {
            throw new FruitNameIsEmpty();
        }
        fruit.setProvider(provider.get());
        fruit = fruitRepository.save(fruit);
        return fruit;
    }

    @Override
    public List<Fruit> readAllFruits(long providerId) throws ProviderNotFound {
        Optional<Provider> provider = providerRepository.findById(providerId);
        if(provider.isEmpty())
        {
            throw new ProviderNotFound();
        }
        return fruitRepository.findByProviderId(providerId);
    }

    @Override
    public Fruit getFruitById(Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        if(fruit.isEmpty())
        {
            throw new FruitIdDoesNotExists();
        }
        return fruit.get();
    }

    @Override
    public Fruit updateFruit(Fruit fruit, Long id,Long provider) {
        if(!(fruitRepository.existsById(id))) {
            throw new FruitIdDoesNotExists();
        }
            fruit.setId(id);
            Optional<Provider> providerObject = providerRepository.findById(provider);
            if(providerObject.isEmpty())
            {
                throw new ProviderNotFound();
            }
            fruit.setProvider(providerObject.get());
            fruitRepository.save(fruit);
            return fruit;
    }

    @Override
    public void deleteFruit(Long id) {
        if(!(fruitRepository.existsById(id)))
        {
            throw new FruitIdDoesNotExists();

        }
    }
}
