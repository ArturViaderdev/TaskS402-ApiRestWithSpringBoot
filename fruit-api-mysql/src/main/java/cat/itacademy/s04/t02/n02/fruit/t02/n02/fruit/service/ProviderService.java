package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderHasFruits;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNameAlreadyExists;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNameIsEmpty;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Provider;

import java.util.List;

public interface ProviderService {
    ProviderResponseDTO createProvider(ProviderRequestDTO provider) throws ProviderNameIsEmpty, ProviderNameAlreadyExists;

    ProviderResponseDTO updateProvider(ProviderRequestDTO provider, Long idProvider) throws ProviderNotFound;

    void deleteProvider(Long idProvider) throws ProviderNotFound, ProviderHasFruits;

    List<ProviderResponseDTO> getProviders();
}
