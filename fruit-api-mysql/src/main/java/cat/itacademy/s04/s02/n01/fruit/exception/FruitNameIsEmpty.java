package cat.itacademy.s04.s02.n01.fruit.exception;

public class FruitNameIsEmpty extends RuntimeException{
    public FruitNameIsEmpty()
    {
        super("El nom de la fruita està buit.");
    }
}
