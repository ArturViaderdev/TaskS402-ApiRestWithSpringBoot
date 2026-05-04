package cat.itacademy.s04.s02.n01.fruit.exception;

public class ProviderNameIsEmpty extends RuntimeException{
    public ProviderNameIsEmpty()
    {
        super("El nom del proveidor està buit.");
    }
}
