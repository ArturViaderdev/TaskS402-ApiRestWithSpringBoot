package cat.itacademy.s04.s02.n01.fruit.exception;

public class ProviderNotFound extends RuntimeException{
    public ProviderNotFound()
    {
        super("El proveidor no existeix.");
    }
}
