package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProviderController {
    private final ProviderService providerService;

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
    public ResponseEntity<Provider> postProvider(@RequestBody Provider provider) {
            Provider savedProvider = providerService.createProvider(provider);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(savedProvider.getId());
            return ResponseEntity.created(location).body(savedProvider);
    }

    @PutMapping("/providers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Provider> updateProvider(@RequestBody Provider provider, @PathVariable String id) {
            Provider savedProvider = providerService.updateProvider(provider, Long.parseLong(id));
            return ResponseEntity.ok(savedProvider);
    }

    @DeleteMapping("/providers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {

        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}
