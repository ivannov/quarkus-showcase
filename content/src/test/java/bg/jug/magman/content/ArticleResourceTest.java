package bg.jug.magman.content;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ArticleResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/content")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}