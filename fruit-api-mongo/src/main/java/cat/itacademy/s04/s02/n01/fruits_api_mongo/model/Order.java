package cat.itacademy.s04.s02.n01.fruits_api_mongo.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="Order")
public class Order {
    private String id;
    private String clientName;
    private LocalDate deliveryDate;
    private List<OrderItem> items;
}
