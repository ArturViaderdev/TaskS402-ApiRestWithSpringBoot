package cat.itacademy.s04.t02.n02.fruit.t02.n02.fruit;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

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
public class FruitsControllerTests {
    //Warning, Execution of this test class will delete all the data stored.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void getFruitsProviderNotFound() throws Exception {

        mockMvc.perform(get("/fruits").param("providerId","999")).andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void getFruitsProviderInputMissmatch() throws Exception{
        mockMvc.perform(get("/fruits").param("providerId","abc")).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
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


    @Test
    @Transactional
    public void addFruitsProviderNotFound() throws Exception
    {
        mockMvc.perform(post("/fruits").param("providerId","999").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Poma\",\n" +
                                "  \"weightInKilos\": \"200\"\n" +
                                "}")).andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void addFruitsProviderMissmatch() throws Exception
    {
        mockMvc.perform(post("/fruits").param("providerId","abc").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}")).andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
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


    @Test
    @Transactional
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


    @Test
    @Transactional
    public void getFruitsByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/fruits/{id}", "999"))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void getFruitsBadRequestTest() throws Exception {
        mockMvc.perform(get("/fruits/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
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
