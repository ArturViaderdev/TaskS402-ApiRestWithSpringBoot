package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Provider;

public class ProviderMapper {
    public static ProviderResponseDTO toDTO(Provider provider)
    {
        return new ProviderResponseDTO(provider.getId(),provider.getName(),provider.getCountry());
    }

    public static Provider toEntity(ProviderRequestDTO provider)
    {
        Provider result = new Provider();
        result.setName(provider.name());
        result.setCountry(provider.country());
        return result;
    }
}
