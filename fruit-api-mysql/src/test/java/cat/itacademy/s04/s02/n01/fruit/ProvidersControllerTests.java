package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(properties = "spring.profiles.active=test")
public class ProvidersControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeEach
    void setUp()
    {
        providerRepository.deleteAll();
    }

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

    @Test
    public void providerNameIsEmpty() throws Exception
    {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\":\"\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andExpect(status().isBadRequest());
    }

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

    @Test
    public void getProvidersTest() throws Exception {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}"));
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutera\",\n" +
                        "  \"country\": \"France\"\n" +
                        "}"));
        mockMvc.perform(get("/providers")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void updateProviderTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(put("/providers/{id}",idProvider).contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Frutere\",\n" +
                                "  \"country\": \"France\"\n" +
                                "}")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Frutere"))
                .andExpect(jsonPath("$.country").value("France"))
                .andExpect(jsonPath("$.id").value(idProvider));
    }

    @Test
    public void updateProviderIdNotFound() throws Exception {
        mockMvc.perform(put("/providers/{id}","999").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutere\",\n" +
                        "  \"country\": \"France\"\n" +
                        "}")).andExpect(status().isNotFound());
    }

    @Test
    public void updateProviderInvalidId() throws Exception{
        mockMvc.perform(put("/providers/{id}","abc").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutere\",\n" +
                        "  \"country\": \"France\"\n" +
                        "}")).andExpect(status().isBadRequest());
    }

    @Test
    public void updateProviderNameIsEmpty() throws Exception{
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(put("/providers/{id}",idProvider).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"\",\n" +
                        "  \"country\": \"France\"\n" +
                        "}")).andExpect(status().isBadRequest());
    }

    @Test
    public void updateProviderNameAlreadyExists() throws Exception {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\":\"Frutero\",\n" +
                        "  \"country\": \"Spain\"\n" +
                        "}"));
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutera\",\n" +
                        "  \"country\": \"Germany\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(put("/providers/{id}",idProvider).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutero\",\n" +
                        "  \"country\": \"France\"\n" +
                        "}")).andExpect(status().isConflict());
    }

    @Test
    public void deleteProvider() throws Exception{
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutera\",\n" +
                        "  \"country\": \"Germany\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(delete("/providers/{id}",idProvider)).andExpect(status().isNoContent());
    }

    @Test
    public void deleteProviderHasFruits() throws Exception{
        MvcResult result = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Frutera\",\n" +
                        "  \"country\": \"Germany\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String idProvider = JsonPath.parse(response).read("$.id").toString();

        mockMvc.perform(post("/fruits").param("providerId",idProvider).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Poma\",\n" +
                        "  \"weightInKilos\": \"200\"\n" +
                        "}"));

        mockMvc.perform(delete("/providers/{id}",idProvider)).andExpect(status().isConflict());
    }

    @Test
    public void deleteProviderIdNotFound() throws Exception {
        mockMvc.perform(delete("/providers/{id}","999")).andExpect(status().isNotFound());
    }

    @Test
    public void deleteProviderBadId() throws Exception{
        mockMvc.perform(delete("/providers/{id}","abc")).andExpect(status().isBadRequest());
    }

}
