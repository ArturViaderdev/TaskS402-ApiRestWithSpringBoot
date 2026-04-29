package cat.itacademy.s04.s02.n01.fruit.exception;

public class ProviderNotFound extends Exception{
    public ProviderNotFound()
    {
        super("El proveidor no existeix.");
    }
}
