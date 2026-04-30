package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(properties = "spring.profiles.active=test")
public class FruitApiMysqlApplicationTests {
    //Warning, Execution of this test class will delete all the data stored.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeEach
    void setUp()
    {
        fruitRepository.deleteAll();
        providerRepository.deleteAll();
    }

    @Order(1)
    @Test
    public void getFruitsProviderNotFound() throws Exception {

        mockMvc.perform(get("/fruits").param("providerId","999")).andExpect(status().isNotFound());
    }

    @Order(2)
    @Test
    public void getFruitsProviderInputMissmatch() throws Exception{
        mockMvc.perform(get("/fruits").param("providerId","abc")).andExpect(status().isBadRequest());
    }

    @Order(3)
    @Test
    public void addProviderTest() throws Exception
    {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Frutero\",\n" +
                                "  \"country\": \"Spain\"\n" +
                                "}")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Frutero"))
                .andExpect(jsonPath("$.country").value("Spain"));
    }

    @Order(4)
    @Test
    public void providerNameIsEmpty() throws Exception
    {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\":\"\",\n" +
                                "  \"country\": \"Spain\"\n" +
                                "}")).andExpect(status().isBadRequest());
    }

    @Order(5)
    @Test
    public void providerNameAlreadyExists() throws Exception
    {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\":\"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}"));
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\":\"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andExpect(status().isConflict());
    }

    @Order(6)
    @Test
    public void addFruitsToProvider() throws Exception
    {
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\":\"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(post("/fruits").param("providerId",id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Poma\",\n" +
                                "  \"weightInKilos\": \"200\"\n" +
                                "}")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Poma"))
                .andExpect(jsonPath("$.weightInKilos").value("200"));
    }

    @Order(7)
    @Test
    public void addFruitsProviderNotFound() throws Exception
    {
        mockMvc.perform(post("/fruits").param("providerId","999").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Poma\",\n" +
                                "  \"weightInKilos\": \"200\"\n" +
                                "}")).andExpect(status().isNotFound());
    }

    @Order(8)
    @Test
    public void addFruitsProviderMissmatch() throws Exception
    {
        mockMvc.perform(post("/fruits").param("providerId","abc").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}")).andExpect(status().isBadRequest());
    }

    @Order(9)
    @Test
    public void getFruitsTest() throws Exception
    {
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();

        mockMvc.perform(post("/fruits").param("providerId",idProvider).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}"));
        mockMvc.perform(post("/fruits").param("providerId",idProvider).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Pera\",\n" +
                        "  \"weightInKilos\": \"100\"\n" +
                        "}"));

        mockMvc.perform(get("/fruits").param("providerId",idProvider)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Order(10)
    @Test
    public void getFruitByIdTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();

        result = mockMvc.perform(post("/fruits").param("providerId",idProvider).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}")).andReturn();

        response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Poma"))
                .andExpect(jsonPath("$.weightInKilos").value("200"));
    }

    @Order(11)
    @Test
    public void getFruitsByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/fruits/{id}", "999"))
                .andExpect(status().isNotFound());
    }

    @Order(12)
    @Test
    public void getFruitsBadRequestTest() throws Exception {
        mockMvc.perform(get("/fruits/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Order(13)
    @Test
    public void updateFruitTest() throws Exception{
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();

        result = mockMvc.perform(post("/fruits").param("providerId",idProvider).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}")).andReturn();
        response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(put("/fruits/{id}",id).param("providerId",idProvider).contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Manzana\",\n" +
                                "  \"weightInKilos\": \"300\"\n" +
                                "}")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Manzana"))
                .andExpect(jsonPath("$.weightInKilos").value("300"));
        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Manzana"))
                .andExpect(jsonPath("$.weightInKilos").value("300"));
    }
}
