package cat.itacademy.s04.t02.n03.fruit.exception;

public class ClientNameIsEmptyException extends RuntimeException {
    public ClientNameIsEmptyException()
    {
        super("El nom del client está buit.");
    }
}
