package cat.itacademy.s04.s02.n01.fruit.exception;

public class ProviderNameAlreadyExists extends RuntimeException{
    public ProviderNameAlreadyExists()
    {
        super("El nom del proveidor ja existeix.");
    }
}
