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

public class AuthMethodsTest {

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
    public void test_01_Method_Auth_Success() {
        given()
                .spec(baseSpec())
                .auth().basic("user", "passwd")
                .when().get("/basic-auth/user/passwd")
                .then()
                .body("user", equalTo("user"),
                        "authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test_02_Method_Bearer_Success() {
        given()
                .spec(baseSpec())
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .when().get("/bearer")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test_03_Method_DigestAuth_Success() {
        given()
                .spec(baseSpec())
                .auth().digest("user", "passwd")
                .when().get("digest-auth/qop/user/passwd")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test_04_Method_Algorithm_Success() {
        given()
                .spec(baseSpec())
                .auth().digest("user", "passwd")
                .when().get("digest-auth/qop/user/passwd/MD5")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test_05_Method_Algorithm_After_Success() {
        given()
                .spec(baseSpec())
                .auth().digest("user", "passwd")
                .when().get("digest-auth/qop/user/passwd/MD5/never")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test_06_Method_Hidden_Auth_Success() {
        given()
                .spec(baseSpec())
                .auth().preemptive().basic("user", "passwd")
                .formParam("user", "user")
                .formParam("passwd", "passwd")
                .when().get("hidden-basic-auth/user/passwd")
                .then()
                .body("user", equalTo("user"),
                        "authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}
