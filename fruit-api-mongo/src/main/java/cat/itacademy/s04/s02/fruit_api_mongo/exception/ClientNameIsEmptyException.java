package cat.itacademy.s04.s02.fruit_api_mongo.exception;

public class ClientNameIsEmptyException extends RuntimeException {
    public ClientNameIsEmptyException()
    {
        super("El nom del client está buit.");
    }
}
