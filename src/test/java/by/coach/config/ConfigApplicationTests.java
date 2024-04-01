package by.coach.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String host = "http://localhost:";
    private final String exerciseApi = "exercise-api";
    private final String common = "common";
    private final String profileDev = "dev";
    private final String profileProd = "prod";
    private final int expectedCode = 200;

    @Test
    void contextLoads() {
        ConfigApplication.main(new String[]{});
        assertNotNull(ConfigApplication.class);
    }

    @Test
    public void getExerciseApiDevConfigTest() {
        var expectedContent = getExpectedContentFromJson("exercise-api-dev.json");

        var response = restTemplate.getForEntity(host + port + "/{exerciseApi}/{profileDev}",
                String.class, exerciseApi, profileDev);

        var actualCode = response.getStatusCode().value();
        var actualContent = response.getBody();

        assertEquals(expectedCode, actualCode);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void getExerciseApiProdConfigTest() {
        var expectedContent = getExpectedContentFromJson("exercise-api-prod.json");

        var response = restTemplate.getForEntity(host + port + "/{exerciseApi}/{profileProd}",
                String.class, exerciseApi, profileProd);

        var actualCode = response.getStatusCode().value();
        var actualContent = response.getBody();

        assertEquals(expectedCode, actualCode);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void getCommonDevConfigTest() {
        var expectedContent = getExpectedContentFromJson("common-dev.json");

        var response = restTemplate.getForEntity(host + port + "/{common}/{profileDev}",
                String.class, common, profileDev);

        var actualCode = response.getStatusCode().value();
        var actualContent = response.getBody();

        assertEquals(expectedCode, actualCode);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void getCommonProdConfigTest() {
        var expectedContent = getExpectedContentFromJson("common-prod.json");

        var response = restTemplate.getForEntity(host + port + "/{common}/{profileProd}",
                String.class, common, profileProd);

        var actualCode = response.getStatusCode().value();
        var actualContent = response.getBody();

        assertEquals(expectedCode, actualCode);
        assertEquals(expectedContent, actualContent);
    }

    private String getExpectedContentFromJson(String fileName) {
        try {
            var resource = getClass().getClassLoader().getResource(fileName);
            var jsonNode = objectMapper.readTree(resource);
            return objectMapper.writeValueAsString(jsonNode);

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return null;
    }

}