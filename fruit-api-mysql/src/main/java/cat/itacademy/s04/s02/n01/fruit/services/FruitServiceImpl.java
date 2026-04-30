package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.IncorrectWeightException;
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
    public Fruit createFruit(Fruit fruit, Long idProvider) throws FruitNameIsEmpty, IncorrectWeightException, ProviderNotFound {
        Optional<Provider> provider = providerRepository.findById(idProvider);
        if(provider.isEmpty())
        {
            throw new ProviderNotFound();
        }
        if(fruit.getName().isEmpty())
        {
            throw new FruitNameIsEmpty();
        }
        if(fruit.getWeightInKilos()<=0)
        {
            throw new IncorrectWeightException();
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
    public Optional<Fruit> getFruitById(Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        return fruit;
    }

    @Override
    public Optional<Fruit> updateFruit(Fruit fruit, Long id,Long provider) {
        if(fruitRepository.existsById(id))
        {
            fruit.setId(id);
            Optional<Provider> providerObject = providerRepository.findById(provider);
            if(providerObject.isEmpty())
            {
                return Optional.empty();
            }
            else
            {
                fruit.setProvider(providerObject.get());
                fruitRepository.save(fruit);
                return Optional.of(fruit);
            }
        }
        else
        {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteFruit(Long id) {
        if(fruitRepository.existsById(id))
        {
            fruitRepository.deleteById(id);
            return true;
        }
        else
        {
            return false;
        }
    }
}
