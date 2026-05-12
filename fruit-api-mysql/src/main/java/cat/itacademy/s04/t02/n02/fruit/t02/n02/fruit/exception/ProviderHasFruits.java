package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.exception;

public class ProviderHasFruits extends RuntimeException {
    public ProviderHasFruits()
    {
        super("El proveidor ja te fruites.");
    }
}
