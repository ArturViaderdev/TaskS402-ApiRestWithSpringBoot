package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.ProviderHasFruits;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNameAlreadyExists;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;

import java.util.Optional;

public interface ProviderService {
    Provider createProvider(Provider provider) throws ProviderNameIsEmpty, ProviderNameAlreadyExists;
    Provider updateProvider(Provider provider, Long idProvider) throws ProviderNotFound;
    void deleteProvider(Long idProvider) throws ProviderNotFound, ProviderHasFruits;
}
