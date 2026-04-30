package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.exception.*;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.services.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProviderController {
    ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/providers")
    @ResponseStatus(HttpStatus.OK)
    public List<Provider> getProviders()
    {
        return providerService.getProviders();
    }

    @PostMapping("/providers")
    @ResponseStatus(HttpStatus.CREATED)
    public Provider postProvider(@RequestBody Provider provider) {
        try {
            return providerService.createProvider(provider);
        } catch (ProviderNameIsEmpty e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ProviderNameAlreadyExists e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/providers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Provider updateProvider(@RequestBody Provider provider, @PathVariable String id) {
        try {
            return providerService.updateProvider(provider, Long.parseLong(id));

        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ProviderNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/providers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable String id) {

        try {
            providerService.deleteProvider(Long.parseLong(id));
        } catch (ProviderNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ProviderHasFruits e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


    }
}
