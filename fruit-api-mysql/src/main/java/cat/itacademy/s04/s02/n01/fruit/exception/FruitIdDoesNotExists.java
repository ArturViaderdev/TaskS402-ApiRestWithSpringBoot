package cat.itacademy.s04.s02.n01.fruit.exception;

public class FruitIdDoesNotExists extends RuntimeException{
    public FruitIdDoesNotExists()
    {
        super("El fruit no existeix.");
    }
}
