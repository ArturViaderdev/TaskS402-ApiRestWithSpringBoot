package cat.itacademy.s04.t02.n03.fruit.model;

import java.util.Objects;

public class OrderItem {
    private String fruitName;
    private int quantityInKilos;

    public OrderItem() {
    }

    public OrderItem(String fruitName, int quantityInKilos) {
        this.fruitName = fruitName;
        this.quantityInKilos = quantityInKilos;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantityInKilos == orderItem.quantityInKilos && Objects.equals(fruitName, orderItem.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitName, quantityInKilos);
    }

    public int getQuantityInKilos() {
        return quantityInKilos;
    }

    public void setQuantityInKilos(int quantityInKilos) {
        this.quantityInKilos = quantityInKilos;
    }
}
