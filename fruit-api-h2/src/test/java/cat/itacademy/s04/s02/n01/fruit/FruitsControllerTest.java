package cat.itacademy.s04.s02.n01.fruit;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
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
public class FruitsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void getFruitsTestsIsEmptyIntially() throws Exception {
        mockMvc.perform(get("/fruits")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getFruitsTest() throws Exception {
        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}"));
        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Pera\",\n" +
                        "  \"weightInKilos\": \"100\"\n" +
                        "}"));

        mockMvc.perform(get("/fruits")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Transactional
    public void addFruitTest() throws Exception {
        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON)
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
    public void getFruitByIdTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
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
    public void updateFruitTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(put("/fruits/{id}", id).contentType(MediaType.APPLICATION_JSON)
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

    @Test
    @Transactional
    public void updateFruitIdNotFoundTest() throws Exception {
        mockMvc.perform(put("/fruits/{id}", "999").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Manzana\",\n" +
                        "  \"weightInKilos\": \"300\"\n" +
                        "}")).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFruitInputMissmatch() throws Exception {
        mockMvc.perform(put("/fruits/{id}", "abc").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Manzana\",\n" +
                        "  \"weightInKilos\": \"300\"\n" +
                        "}")).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteFruitTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();

        result = mockMvc.perform(get("/fruits")).andReturn();
        String results = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(results);
        int size = rootNode.size();

        mockMvc.perform(delete("/fruits/{id}", id)).andExpect(status().isNoContent());

        mockMvc.perform(get("/fruits")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(size - 1));
    }

    @Test
    @Transactional
    public void deleteFruitIdNotFoundTest() throws Exception {
        mockMvc.perform(delete("/fruits/{id}", "999")).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteFruitInputMissmatch() throws Exception {
        mockMvc.perform(delete("/fruits/{id}", "abc")).andExpect(status().isBadRequest());
    }
}
