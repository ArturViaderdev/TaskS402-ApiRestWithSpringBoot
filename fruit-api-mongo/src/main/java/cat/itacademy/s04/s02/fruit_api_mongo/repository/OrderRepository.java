package cat.itacademy.s04.s02.fruit_api_mongo.repository;

import cat.itacademy.s04.s02.fruit_api_mongo.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
