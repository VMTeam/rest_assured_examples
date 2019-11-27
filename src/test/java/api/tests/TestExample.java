package api.tests;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestExample {
    @Test
    public void test_01_auth (){
        given()
                .when().get("https://httpbin.org/#/" + "/basic-auth/{user}/{passwd}")
                .then().assertThat().statusCode(200);
    }
}
