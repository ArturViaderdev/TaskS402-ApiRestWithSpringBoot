package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception;

public class FruitNameIsEmpty extends RuntimeException {
    public FruitNameIsEmpty() {
        super("El nom de la fruita està buit.");
    }
}
