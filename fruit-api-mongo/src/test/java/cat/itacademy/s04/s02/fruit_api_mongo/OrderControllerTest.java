package cat.itacademy.s04.s02.fruit_api_mongo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
