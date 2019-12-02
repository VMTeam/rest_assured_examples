package api.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class StatusCodesTest {

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
    public void test_01_Method_Delete_Success() {
        given()
                .spec(baseSpec())
                .when().delete("/status/200")
                .then().statusCode(HttpStatus.SC_OK)
                .statusLine("HTTP/1.1 200 OK");
        given()
                .spec(baseSpec())
                .when().delete("/status/300")
                .then().statusCode(HttpStatus.SC_MULTIPLE_CHOICES)
                .statusLine("HTTP/1.1 300 MULTIPLE CHOICES");
        given()
                .spec(baseSpec())
                .when().delete("/status/400")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .statusLine("HTTP/1.1 400 BAD REQUEST");
        given()
                .spec(baseSpec())
                .when().delete("/status/500")
                .then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .statusLine("HTTP/1.1 500 INTERNAL SERVER ERROR");
    }

    @Test
    public void test_02_Method_Post_Success() {
        given()
                .spec(baseSpec())
                .when().post("/status/200")
                .then().statusCode(HttpStatus.SC_OK)
                .statusLine("HTTP/1.1 200 OK");
        given()
                .spec(baseSpec())
                .when().post("/status/300")
                .then().statusCode(HttpStatus.SC_MULTIPLE_CHOICES)
                .statusLine("HTTP/1.1 300 MULTIPLE CHOICES");
        given()
                .spec(baseSpec())
                .when().post("/status/400")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .statusLine("HTTP/1.1 400 BAD REQUEST");
        given()
                .spec(baseSpec())
                .when().post("/status/500")
                .then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .statusLine("HTTP/1.1 500 INTERNAL SERVER ERROR");
    }
}
