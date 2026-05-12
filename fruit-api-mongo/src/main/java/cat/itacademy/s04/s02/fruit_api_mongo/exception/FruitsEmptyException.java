package cat.itacademy.s04.s02.fruit_api_mongo.exception;

public class FruitsEmptyException extends RuntimeException{
    public FruitsEmptyException()
    {
        super("Llista de fruites buida");
    }
}
