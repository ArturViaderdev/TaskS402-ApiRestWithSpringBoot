package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception;

public class FruitIdDoesNotExists extends RuntimeException {
    public FruitIdDoesNotExists() {
        super("El fruit no existeix.");
    }
}
