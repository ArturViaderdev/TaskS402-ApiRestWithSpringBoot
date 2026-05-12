package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Objects;


@Entity
@Table(name="fruits")
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Positive
    private int weightInKilos;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    public Provider provider;

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Fruit()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeightInKilos() {
        return weightInKilos;
    }

    public void setWeightInKilos(int weightInKilos) {
        this.weightInKilos = weightInKilos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return weightInKilos == fruit.weightInKilos && Objects.equals(id, fruit.id) && Objects.equals(name, fruit.name) && Objects.equals(provider, fruit.provider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weightInKilos, provider);
    }
}
