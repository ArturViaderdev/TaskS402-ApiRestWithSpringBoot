package cat.itacademy.s04.s02.fruit_api_mongo.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException()
    {
        super("Comanda no trobada");
    }
}
