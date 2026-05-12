package cat.itacademy.s04.s02.fruit_api_mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="OrderItem")
public class OrderItem {
    private String fruitName;
    private int quantityInKilos;

    private OrderItem() {
    }

}
