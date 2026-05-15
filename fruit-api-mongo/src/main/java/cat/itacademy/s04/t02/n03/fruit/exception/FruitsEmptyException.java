package cat.itacademy.s04.t02.n03.fruit.exception;

public class FruitsEmptyException extends RuntimeException {
    public FruitsEmptyException() {
        super("Llista de fruites buida");
    }
}
