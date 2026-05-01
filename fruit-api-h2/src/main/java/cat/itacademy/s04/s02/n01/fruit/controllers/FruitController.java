package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FruitController {
    FruitService fruitService;

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
    public Fruit postFruits(@Valid @RequestBody Fruit fruit) {
        return fruitService.createFruit(fruit);
    }

    @PutMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fruit updateFruit(@Valid @RequestBody Fruit fruit, @PathVariable Long id) {
        return fruitService.updateFruit(fruit, id);
    }

    @DeleteMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFruit(@PathVariable Long id) {

        fruitService.deleteFruit(id);
    }
}
