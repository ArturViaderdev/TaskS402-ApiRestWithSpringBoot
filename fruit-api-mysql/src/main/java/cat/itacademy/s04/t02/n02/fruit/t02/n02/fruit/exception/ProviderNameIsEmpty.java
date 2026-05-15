package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception;

public class ProviderNameIsEmpty extends RuntimeException {
    public ProviderNameIsEmpty() {
        super("El nom del proveidor està buit.");
    }
}
