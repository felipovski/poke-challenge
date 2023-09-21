package com.github.felipovski.pokeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.felipovski.pokeservice.entity.dto.PokeApiDto;
import com.github.felipovski.pokeservice.entity.dto.PokeResponseDto;
import com.github.felipovski.pokeservice.control.exception.handler.ApiErrorResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;

import static com.github.felipovski.pokeservice.control.PokeCache.cache;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(
        properties = "resources/application-test.yml",
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class PokeServiceApplicationTests {

    @Value("${server.port}")
    String port;

    @Value("${server.servlet.context-path}")
    String contextPath;

    private String BASE_URL;
    @BeforeAll
    void setup() {
        BASE_URL = "http://localhost:" + port + contextPath;
        try {
            ObjectMapper mapper = new ObjectMapper();
            cache.addPokeList(mapper.readValue(
                    PokeApiDto.class.getResourceAsStream("/data.json"),
                    PokeApiDto.class).getResults());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenThereIsNoQuery_thenReturnAllPokemons() {
        var response = given().when().get(BASE_URL + "/pokemons")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", PokeResponseDto.class);

        assertThat(response.getResult()).hasSize(1281);
    }

    @Test
    void whenThereIsNoMatchingQuery_thenReturnEmptyList() {
        var response = given().when().get(BASE_URL + "/pokemons?query=batatas")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", PokeResponseDto.class);

        assertThat(response.getResult()).isEmpty();
    }

    @Test
    void whenThereIsQuery_testCaseInsensitive() {
        var response = given().when().get(BASE_URL + "/pokemons?query=Pikachu")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", PokeResponseDto.class);

        assertThat(response.getResult()).hasSize(17);
    }

    @Test
    void whenSortIsEmpty_sortAlphabetically() {
        var response = given().when().get(BASE_URL + "/pokemons?query=pin")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", PokeResponseDto.class);

        var result = response.getResult();

        assertThat(result.get(0).getName()).isLessThan(result.get(1).getName());
        assertThat(result.get(1).getName()).isLessThan(result.get(2).getName());
        assertThat(result.get(2).getName()).isLessThan(result.get(3).getName());
    }
    @Test
    void whenSortIsLength_sortByLength() {
        var response = given().when().get(BASE_URL + "/pokemons?query=pin&sort=Length")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", PokeResponseDto.class);

        var result = response.getResult();

        assertThat(result.get(0).getName().length()).isLessThanOrEqualTo(result.get(1).getName().length());
        assertThat(result.get(1).getName().length()).isLessThanOrEqualTo(result.get(2).getName().length());
        assertThat(result.get(2).getName().length()).isLessThanOrEqualTo(result.get(3).getName().length());
    }

    @Test
    void whenSortIsInvalid_thenThrowException() {
        given().when().get(BASE_URL + "/pokemons?query=pin&sort=poke")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .extract().body()
                .jsonPath().getObject(".", ApiErrorResponse.class);
    }

    @Test
    void whenThereIsQuery_highlightPokemonName() {
        var response = given().when().get(BASE_URL + "/pokemons/highlight?query=Pikachu")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", PokeResponseDto.class);

        assertThat(response.getResult().get(0).getHighlight()).contains("<pre>", "</pre>");
    }
}
