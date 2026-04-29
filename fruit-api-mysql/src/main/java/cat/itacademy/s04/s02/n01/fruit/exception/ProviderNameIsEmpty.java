package cat.itacademy.s04.s02.n01.fruit.exception;

public class ProviderNameIsEmpty extends Exception{
    public ProviderNameIsEmpty()
    {
        super("El nom del proveidor està buit.");
    }
}
