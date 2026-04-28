package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.IncorrectWeightException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import cat.itacademy.s04.s02.n01.fruit.services.FruitServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class FruitController {
    FruitService fruitService;

    public FruitController(FruitService fruitService)
    {
        this.fruitService = fruitService;
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public Fruit postFruits(@RequestBody Fruit fruit) {
        try {
            return fruitService.createFruit(fruit);
        } catch (FruitNameIsEmpty e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch(IncorrectWeightException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
