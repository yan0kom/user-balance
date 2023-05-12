package yan0kom.userbal.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import yan0kom.userbal.UserbalPostgreSqlContainer;
import yan0kom.userbal.api.dto.UserSearchInDto;
import yan0kom.userbal.dao.UserDao;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserApiSearchIntegrationTest {
    @ClassRule
    public static UserbalPostgreSqlContainer postgreSqlContainer = UserbalPostgreSqlContainer.getInstance();

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper json;
    @Autowired
    UserDao userDao;

    String token;

    @BeforeAll
    void setup() throws Exception {
        assertThat(mvc).isNotNull();
        assertThat(postgreSqlContainer.isRunning()).isTrue();
    }

    @Test
    public void hasUsers() {
        assertThat(userDao.count()).isEqualTo(4L);
    }

    @Test
    public void searchAll() throws Exception {
        useToken("john@gmail.com", "john-pass");

        var inDto = new UserSearchInDto();
        inDto.setPageNumber(0);
        inDto.setPageSize(3);

        var jsonNode = asJsonNode(get("/user/search"), inDto);
        assertThat(jsonNode.get("content").size()).isEqualTo(3);
        assertThat(jsonNode.get("number").asInt()).isEqualTo(0);
        assertThat(jsonNode.get("numberOfElements").asInt()).isEqualTo(3);
        assertThat(jsonNode.get("totalElements").asInt()).isEqualTo(4);
        assertThat(jsonNode.get("totalPages").asInt()).isEqualTo(2);

        inDto.setPageNumber(1);
        jsonNode = asJsonNode(get("/user/search"), inDto);
        assertThat(jsonNode.get("content").size()).isEqualTo(1);
        assertThat(jsonNode.get("number").asInt()).isEqualTo(1);
        assertThat(jsonNode.get("numberOfElements").asInt()).isEqualTo(1);
    }

    @Test
    public void searchByPhone() throws Exception {
        useToken("john@gmail.com", "john-pass");

        var inDto = new UserSearchInDto();
        inDto.setPageNumber(0);
        inDto.setPageSize(3);
        inDto.setPhone("79995461100");

        var jsonNode = asJsonNode(get("/user/search"), inDto);
        assertThat(jsonNode.get("content").size()).isEqualTo(1);
        assertThat(jsonNode.get("number").asInt()).isEqualTo(0);
        assertThat(jsonNode.get("numberOfElements").asInt()).isEqualTo(1);
        assertThat(jsonNode.get("totalElements").asInt()).isEqualTo(1);
        assertThat(jsonNode.get("totalPages").asInt()).isEqualTo(1);
        assertThat(jsonNode.get("content").get(0).get("id").asLong()).isEqualTo(2L);
    }

    @Test
    public void searchByEmail() throws Exception {
        useToken("john@gmail.com", "john-pass");

        var inDto = new UserSearchInDto();
        inDto.setPageNumber(0);
        inDto.setPageSize(3);
        inDto.setEmail("polina@gmail.com");

        var jsonNode = asJsonNode(get("/user/search"), inDto);
        assertThat(jsonNode.get("content").size()).isEqualTo(1);
        assertThat(jsonNode.get("number").asInt()).isEqualTo(0);
        assertThat(jsonNode.get("numberOfElements").asInt()).isEqualTo(1);
        assertThat(jsonNode.get("totalElements").asInt()).isEqualTo(1);
        assertThat(jsonNode.get("totalPages").asInt()).isEqualTo(1);
        assertThat(jsonNode.get("content").get(0).get("id").asLong()).isEqualTo(3L);
    }

    @Test
    public void searchByDateOfBirthAfter() throws Exception {
        useToken("john@gmail.com", "john-pass");

        var inDto = new UserSearchInDto();
        inDto.setPageNumber(0);
        inDto.setPageSize(3);
        inDto.setDateOfBirthAfter(LocalDate.of(1978, 1, 1));

        var jsonNode = asJsonNode(get("/user/search"), inDto);
        assertThat(jsonNode.get("content").size()).isEqualTo(2);
        assertThat(jsonNode.get("content").get(0).get("id").asLong()).isEqualTo(4L);
        assertThat(jsonNode.get("content").get(1).get("id").asLong()).isEqualTo(3L);
    }

    @Test
    public void searchByNamePrefix() throws Exception {
        useToken("john@gmail.com", "john-pass");

        var inDto = new UserSearchInDto();
        inDto.setPageNumber(0);
        inDto.setPageSize(3);
        inDto.setNamePrefix("Пол");

        var jsonNode = asJsonNode(get("/user/search"), inDto);
        assertThat(jsonNode.get("content").size()).isEqualTo(2);
        assertThat(jsonNode.get("content").get(0).get("id").asLong()).isEqualTo(2L);
        assertThat(jsonNode.get("content").get(1).get("id").asLong()).isEqualTo(3L);
    }

    @Test
    public void searchByNamePrefixAndDateOfBirthAfter() throws Exception {
        useToken("john@gmail.com", "john-pass");

        var inDto = new UserSearchInDto();
        inDto.setPageNumber(0);
        inDto.setPageSize(3);
        inDto.setNamePrefix("Пол");
        inDto.setDateOfBirthAfter(LocalDate.of(1978, 1, 1));

        var jsonNode = asJsonNode(get("/user/search"), inDto);
        assertThat(jsonNode.get("content").size()).isEqualTo(1);
        assertThat(jsonNode.get("content").get(0).get("id").asLong()).isEqualTo(3L);
    }

    void useToken(String username, String password) throws Exception {
        token = mvc.perform(post("/token").with(httpBasic(username, password)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    MockHttpServletRequestBuilder setBody(MockHttpServletRequestBuilder requestBuilder, Object body)
            throws JsonProcessingException {
        return requestBuilder.contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json.writeValueAsString(body))
                .accept("application/json;charset=UTF-8");
    }

    MockHttpServletRequestBuilder addToken(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder.header("Authorization", String.format("Bearer %s", token));
    }

    JsonNode asJsonNode(MockHttpServletRequestBuilder requestBuilder, Object body) throws Exception {
        var res = mvc.perform(setBody(addToken(requestBuilder), body)).andExpect(status().isOk()).andReturn();
        return json.readTree(res.getResponse().getContentAsString());
    }
}
