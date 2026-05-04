package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNotFound;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import cat.itacademy.s04.s02.n01.fruit.services.FruitServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@RestController
public class FruitController {
    FruitService fruitService;

    public FruitController(FruitService fruitService)
    {
        this.fruitService = fruitService;
    }

    @GetMapping("/fruits")
    @ResponseStatus(HttpStatus.OK)
    public List<Fruit> getFruits(@RequestParam(name="providerId",defaultValue = "") String provider){
        return fruitService.readAllFruits(Long.parseLong(provider));
    }

    @GetMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fruit getFruitById(@PathVariable Long id){
            Fruit fruit = fruitService.getFruitById(id);

            return fruit;
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public Fruit postFruits(@RequestBody Fruit fruit, @RequestParam(name="providerId",defaultValue = "") String provider) {

            return fruitService.createFruit(fruit,Long.parseLong(provider));
    }

    @PutMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fruit updateFruit(@RequestBody Fruit fruit,@PathVariable Long id,@RequestParam(name="providerId",defaultValue = "")Long provider) {

            return fruitService.updateFruit(fruit,id,provider);

    }

    @DeleteMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFruit(@PathVariable Long id){
            fruitService.deleteFruit(id);

    }
}
