package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.controller;

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
    public ResponseEntity<Fruit> postFruits(@RequestBody Fruit fruit, @RequestParam(name="providerId",defaultValue = "") String provider) {
            Fruit savedFruit = fruitService.createFruit(fruit,Long.parseLong(provider));
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedFruit.getId()).toUri();
            return ResponseEntity.created(location).body(savedFruit);
    }

    @PutMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Fruit> updateFruit(@RequestBody Fruit fruit,@PathVariable Long id,@RequestParam(name="providerId",defaultValue = "")Long provider) {

            Fruit updatedFruit = fruitService.updateFruit(fruit,id,provider);
            return ResponseEntity.ok(updatedFruit);

    }

    @DeleteMapping("/fruits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id){
            fruitService.deleteFruit(id);
            return ResponseEntity.noContent().build();

    }
}
