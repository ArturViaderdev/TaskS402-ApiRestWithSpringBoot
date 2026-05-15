package cat.itacademy.s04.t02.n03.fruit;

import cat.itacademy.s04.t02.n03.fruit.repository.OrderRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void delete()
    {
        orderRepository.deleteAll();
    }

    @Test
    public void addProviderTest() throws Exception
    {
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"clientName\": \"Comprador\",\n" +
                                "  \"deliveryDate\": \"2025-01-01\",\n" +
                                "  \"items\":[{\"fruitName\":\"Poma\",\"quantityInKilos\":\"100\"}]"+
                                "}")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.clientName").value("Comprador"))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items",hasSize(1)))
                .andExpect(jsonPath("$.items[0].fruitName").value("Poma"))
                .andExpect(jsonPath("$.items[0].quantityInKilos").value("100"));
    }

    @Test
    public void addProviderEmptyClientName() throws Exception
    {
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
          .content("{\n" +
            "  \"clientName\": \"\",\n" +
            "  \"deliveryDate\": \"2025-01-01\",\n" +
            "  \"items\":[{\"fruitName\":\"Poma\",\"quantityInKilos\":\"100\"}]"+
            "}")).andExpect(status().isBadRequest());
    }

    @Test
    public void addProviderEmptyItemsList() throws Exception
    {
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"clientName\": \"Comprador\",\n" +
                                "  \"deliveryDate\": \"2025-01-01\",\n" +
                                "}")).andExpect(status().isBadRequest());
    }

    @Test
    public void readAllOrdersTest() throws Exception
    {
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"clientName\": \"Comprador\",\n" +
                        "  \"deliveryDate\": \"2025-01-01\",\n" +
                        "  \"items\":[{\"fruitName\":\"Poma\",\"quantityInKilos\":\"100\"}]"+
                        "}"));
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"clientName\": \"Juan\",\n" +
                        "  \"deliveryDate\": \"2025-01-01\",\n" +
                        "  \"items\":[{\"fruitName\":\"Pera\",\"quantityInKilos\":\"200\"}]"+
                        "}"));
        mockMvc.perform(get("/orders")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void readAllOrdersEmpty() throws Exception
    {
        mockMvc.perform(get("/orders")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void getOrderByIdTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"clientName\": \"Comprador\",\n" +
                        "  \"deliveryDate\": \"2025-01-01\",\n" +
                        "  \"items\":[{\"fruitName\":\"Poma\",\"quantityInKilos\":\"100\"}]"+
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idOrder = JsonPath.parse(response).read("$.id").toString();

        mockMvc.perform(get("/orders/{id}", idOrder))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idOrder))
                .andExpect(jsonPath("$.clientName").value("Comprador"))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items",hasSize(1)))
                .andExpect(jsonPath("$.items[0].fruitName").value("Poma"))
                .andExpect(jsonPath("$.items[0].quantityInKilos").value("100"));
    }

    @Test
    public void getOrderByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/orders/{id}", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrderTest() throws Exception{
        MvcResult result = mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"clientName\": \"Comprador\",\n" +
                        "  \"deliveryDate\": \"2025-01-01\",\n" +
                        "  \"items\":[{\"fruitName\":\"Poma\",\"quantityInKilos\":\"100\"}]"+
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();

        mockMvc.perform(put("/orders/{id}",id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"clientName\": \"Juan\",\n" +
                                "  \"deliveryDate\": \"2025-02-01\",\n" +
                                "  \"items\":[{\"fruitName\":\"Pera\",\"quantityInKilos\":\"100\"}]"+
                                "}")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.clientName").value("Juan"))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items",hasSize(1)))
                .andExpect(jsonPath("$.items[0].fruitName").value("Pera"))
                .andExpect(jsonPath("$.items[0].quantityInKilos").value("100"));
    }

    @Test
    public void updateOrderNotFound() throws Exception {
        mockMvc.perform(put("/orders/{id}","999").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"clientName\": \"Juan\",\n" +
                        "  \"deliveryDate\": \"2025-02-01\",\n" +
                        "  \"items\":[{\"fruitName\":\"Pera\",\"quantityInKilos\":\"100\"}]"+
                        "}")).andExpect(status().isNotFound());
    }

    @Test
    public void deleteOrder() throws Exception{
        MvcResult result = mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"clientName\": \"Comprador\",\n" +
                        "  \"deliveryDate\": \"2025-01-01\",\n" +
                        "  \"items\":[{\"fruitName\":\"Poma\",\"quantityInKilos\":\"100\"}]"+
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}",id)).andExpect(status().isNoContent());
    }

    @Test
    public void deleteOrderNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}","999")).andExpect(status().isNotFound());
    }
}
