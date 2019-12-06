package api.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ResponseInspectionTest {

    private RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON.withCharset("UTF-8"))
                .setBaseUri("http://httpbin.org")
                .setPort(80)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void test_01_Methods_Cache() {
        given()
                .spec(baseSpec())
                .when().get("/cache")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("origin", equalTo("158.181.247.178, 158.181.247.178"),
                        "url", equalTo("https://httpbin.org/cache"));
        given()
                .spec(baseSpec())
                .headers("If-Modified-Since", "1")
                .when().get("/cache")
                .then()
                .statusCode(HttpStatus.SC_NOT_MODIFIED);
    }

    @Test
    public void test_02_Methods_Cache_Value() {
        given()
                .spec(baseSpec())
                .when().get("/cache/")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        given()
                .spec(baseSpec())
                .pathParam("value", "2")
                .when().get("/cache/{value}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("origin", equalTo("158.181.247.178, 158.181.247.178"),
                        "url", equalTo("https://httpbin.org/cache/2"));
    }

    // In progress! I have not idea(((
    @Test
    public void test_03_Methods_Cache_Etag() {
        given()
                .spec(baseSpec())
                .pathParam("If-None-Match", "2")
                .when().get("/etag/{etag}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("origin", equalTo("158.181.247.178, 158.181.247.178"),
                        "url", equalTo("https://httpbin.org/etag/{etag}"));
        given()
                .spec(baseSpec())
                .when().get("/etag/{etag}")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test_03_Methods_Response_Headers() {
        given()
                .spec(baseSpec())
                .queryParam("freeform", "ololosha")
                .when().get("/response-headers")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("Content-Length", equalTo("95"),
                        "freeform", equalTo("ololosha"));
    }

    @Test
    public void test_04_Methods_Response_Headers() {
        given()
                .spec(baseSpec())
                .queryParam("freeform", "ololosha")
                .when().post("/response-headers")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("Content-Length", equalTo("95"),
                        "freeform", equalTo("ololosha"));

    }
}
