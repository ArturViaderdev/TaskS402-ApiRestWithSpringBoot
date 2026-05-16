package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.service.FruitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class FruitController {
    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping("/fruits")
    @ResponseStatus(HttpStatus.OK)
    public List<FruitResponseDTO> getFruits(@RequestParam(name = "providerId", defaultValue = "") Long provider) {
        return fruitService.readAllFruits(provider);
    }

    @GetMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FruitResponseDTO getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id);
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FruitResponseDTO> postFruits(@RequestBody FruitRequestDTO fruit, @RequestParam(name = "providerId", defaultValue = "") Long provider) {
        FruitResponseDTO savedFruit = fruitService.createFruit(fruit,provider);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedFruit.id()).toUri();
        return ResponseEntity.created(location).body(savedFruit);
    }

    @PutMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FruitResponseDTO> updateFruit(@RequestBody FruitRequestDTO fruit, @PathVariable Long id, @RequestParam(name = "providerId", defaultValue = "") Long provider) {
        FruitResponseDTO updatedFruit = fruitService.updateFruit(fruit, id, provider);
        return ResponseEntity.ok(updatedFruit);
    }

    @DeleteMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();

    }
}
