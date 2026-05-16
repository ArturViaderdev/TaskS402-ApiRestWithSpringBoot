package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service.ProviderService;
import jakarta.validation.Valid;
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
    public List<ProviderResponseDTO> getProviders() {
        return providerService.getProviders();
    }

    @PostMapping("/providers")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProviderResponseDTO> postProvider(@Valid @RequestBody ProviderRequestDTO provider) {
        ProviderResponseDTO savedProvider = providerService.createProvider(provider);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(savedProvider.id());
        return ResponseEntity.created(location).body(savedProvider);
    }

    @PutMapping("/providers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProviderResponseDTO> updateProvider(@Valid @RequestBody ProviderRequestDTO provider, @PathVariable Long id) {
        ProviderResponseDTO savedProvider = providerService.updateProvider(provider, id);
        return ResponseEntity.ok(savedProvider);
    }

    @DeleteMapping("/providers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}
