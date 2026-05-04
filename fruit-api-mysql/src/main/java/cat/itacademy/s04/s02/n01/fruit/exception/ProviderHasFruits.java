package cat.itacademy.s04.s02.n01.fruit.exception;

public class ProviderHasFruits extends RuntimeException {
    public ProviderHasFruits()
    {
        super("El proveidor ja te fruites.");
    }
}
