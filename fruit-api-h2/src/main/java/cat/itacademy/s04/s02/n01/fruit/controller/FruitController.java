package cat.itacademy.s04.s02.n01.fruit.controller;

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
    public List<Fruit> getFruits() {
        return fruitService.readAllFruits();
    }

    @GetMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fruit getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id);
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Fruit> postFruits(@Valid @RequestBody Fruit fruit) {

        Fruit savedFruit = fruitService.createFruit(fruit);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedFruit.getId()).toUri();
        return ResponseEntity.created(location).body(savedFruit);
    }

    @PutMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Fruit> updateFruit(@Valid @RequestBody Fruit fruit, @PathVariable Long id) {
        Fruit updatedFruit = fruitService.updateFruit(fruit, id);
        return ResponseEntity.ok(updatedFruit);
    }

    @DeleteMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {

        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }
}
