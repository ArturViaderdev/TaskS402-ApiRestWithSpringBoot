package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception;

public class ProviderNameAlreadyExists extends RuntimeException {
    public ProviderNameAlreadyExists() {
        super("El nom del proveidor ja existeix.");
    }
}
