package cat.itacademy.s04.t02.n03.fruit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Document(collection = "Order")
public class Order {
    @Id
    private String id;
    private String clientName;
    private LocalDate deliveryDate;
    private List<OrderItem> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(clientName, order.clientName) && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName, deliveryDate, items);
    }
}
