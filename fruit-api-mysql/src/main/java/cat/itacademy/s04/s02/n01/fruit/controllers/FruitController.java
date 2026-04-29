package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNameIsEmpty;
import cat.itacademy.s04.s02.n01.fruit.exception.IncorrectWeightException;
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
    public List<Fruit> getFruits(){
        return fruitService.readAllFruits();
    }

    @GetMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fruit getFruitById(@PathVariable String id){
        try
        {
            Optional<Fruit> fruit = fruitService.getFruitById(Long.parseLong(id));
            if(fruit.isEmpty())
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            else
            {
                return fruit.get();
            }
        }
        catch(NumberFormatException ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public Fruit postFruits(@RequestBody Fruit fruit) {
        try {
            return fruitService.createFruit(fruit);
        } catch (FruitNameIsEmpty | IncorrectWeightException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fruit updateFruit(@RequestBody Fruit fruit,@PathVariable String id) {
        try {
            Optional<Fruit> modfruit = fruitService.updateFruit(fruit,Long.parseLong(id));
            if(modfruit.isEmpty())
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            else
            {
                return modfruit.get();
            }
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFruit(@PathVariable String id){
        try
        {
            boolean deleted = fruitService.deleteFruit(Long.parseLong(id));
            if(!deleted)
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        catch(NumberFormatException ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
