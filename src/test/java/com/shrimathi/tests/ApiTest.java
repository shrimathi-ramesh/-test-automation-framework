package com.shrimathi.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * API tests using REST Assured against a public demo API
 * (https://reqres.in — a free API for testing).
 * Shows REST API validation: status codes, response body fields.
 */
public class ApiTest {

    private final String BASE_URL = "https://reqres.in/api";

    @Test(description = "GET single user should return 200 and correct user id")
    public void testGetSingleUser() {
        Response response = given()
                .baseUri(BASE_URL)
                .when()
                .get("/users/2")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200");
        Assert.assertEquals(response.jsonPath().getInt("data.id"), 2, "User id should be 2");
        Assert.assertNotNull(response.jsonPath().getString("data.email"), "Email should not be null");
    }

    @Test(description = "GET non-existent user should return 404")
    public void testGetMissingUser() {
        int status = given()
                .baseUri(BASE_URL)
                .when()
                .get("/users/23")
                .then()
                .extract().statusCode();

        Assert.assertEquals(status, 404, "Expected HTTP 404 for a user that does not exist");
    }
}
