package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception;

public class ProviderNotFound extends RuntimeException {
    public ProviderNotFound() {
        super("El proveidor no existeix.");
    }
}
