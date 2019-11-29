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

public class HttpMethodsTest {

    private RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON.withCharset("UTF-8"))
                .setBaseUri("http://httpbin.org/")
                .setPort(80)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void test_01_MethodGet_Success() {
        given().spec(baseSpec()).when().get("/get")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test_02_MethodPut_Success() {
        given().spec(baseSpec()).when().put("/put")
                .then().statusCode(HttpStatus.SC_OK)
                .body("headers.Host", equalTo("httpbin.org"));
    }

    @Test
    public void test_03_MethodDelete_Success() {
        given().spec(baseSpec()).when().delete("/delete")
                .then().statusCode(HttpStatus.SC_OK)
                .body("url", equalTo("https://httpbin.org/delete"));
    }

    @Test
    public void test_04_MethodPatch_Success() {
        given().spec(baseSpec()).when().patch("/patch")
                .then().statusCode(HttpStatus.SC_OK)
                .body("url", equalTo("https://httpbin.org/patch"));
    }

    @Test
    public void test_04_MethodPost_Success() {
        given().spec(baseSpec()).when().post("/post")
                .then().statusCode(HttpStatus.SC_OK)
                .body("url", equalTo("https://httpbin.org/post"));
    }
}
