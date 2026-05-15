package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import jakarta.validation.Valid;
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
    public List<FruitResponseDTO> getFruits() {
        return fruitService.readAllFruits();
    }

    @GetMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FruitResponseDTO getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id);
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FruitResponseDTO> postFruits(@Valid @RequestBody FruitRequestDTO fruit) {

        FruitResponseDTO savedFruit = fruitService.createFruit(fruit);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedFruit.id()).toUri();
        return ResponseEntity.created(location).body(savedFruit);
    }

    @PutMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FruitResponseDTO> updateFruit(@Valid @RequestBody FruitRequestDTO fruit, @PathVariable Long id) {
        FruitResponseDTO updatedFruit = fruitService.updateFruit(fruit, id);
        return ResponseEntity.ok(updatedFruit);
    }

    @DeleteMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {

        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }
}
