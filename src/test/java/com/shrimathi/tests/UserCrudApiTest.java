package com.shrimathi.tests;

import com.shrimathi.api.ApiClient;
import com.shrimathi.api.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

/**
 * API automation tests — full CRUD + negative + chained flow.
 *
 * Every test uses ApiClient.spec() (shared base URL + headers),
 * and a POJO (User) for request bodies.
 *
 * Covers: POST 201, PUT 200, PATCH 200, DELETE 204,
 *         negative case (400 + exact error message), response time, headers,
 *         and chaining (use the id returned by one call in the next call).
 */
public class UserCrudApiTest {

    @Test(description = "POST creates a user: 201, echoes name/job, returns id and createdAt")
    public void createUserReturns201() {
        User newUser = new User("Shrimathi", "SDET");

        given()
                .spec(ApiClient.spec())
                .body(newUser)                               // POJO -> JSON automatically
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .header("Content-Type", containsString("application/json"))
                .body("name", equalTo("Shrimathi"))
                .body("job", equalTo("SDET"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue())
                .time(lessThan(5000L));                      // basic response-time check (ms)
    }

    @Test(description = "PUT replaces the user: 200 and updated job + updatedAt")
    public void updateUserWithPut() {
        given()
                .spec(ApiClient.spec())
                .body(new User("Shrimathi", "Senior SDET"))
        .when()
                .put("/users/2")
        .then()
                .statusCode(200)
                .body("job", equalTo("Senior SDET"))
                .body("updatedAt", notNullValue());
    }

    @Test(description = "PATCH updates only the given field: 200")
    public void partialUpdateWithPatch() {
        given()
                .spec(ApiClient.spec())
                .body(Map.of("job", "QA Lead"))              // a Map also serializes to JSON
        .when()
                .patch("/users/2")
        .then()
                .statusCode(200)
                .body("job", equalTo("QA Lead"));
    }

    @Test(description = "DELETE removes the user: 204 with an empty body")
    public void deleteUserReturns204() {
        Response response = given()
                .spec(ApiClient.spec())
        .when()
                .delete("/users/2")
        .then()
                .statusCode(204)
                .extract().response();

        Assert.assertTrue(response.asString().isEmpty(), "204 response should have no body");
    }

    @Test(description = "NEGATIVE: login without password returns 400 and a clear error message")
    public void loginWithoutPasswordReturns400() {
        given()
                .spec(ApiClient.spec())
                .body(Map.of("email", "eve.holt@reqres.in"))  // password deliberately missing
        .when()
                .post("/login")
        .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));   // validate the exact error message
    }

    @Test(description = "CHAINED: create a user, then use the returned id in the next request")
    public void createThenUpdateSameUser() {
        // Step 1: create and EXTRACT the id from the response
        String id = given()
                .spec(ApiClient.spec())
                .body(new User("Shrimathi", "SDET"))
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .extract().jsonPath().getString("id");

        Assert.assertNotNull(id, "Create call should return an id");

        // Step 2: use that id in the next call (request chaining)
        given()
                .spec(ApiClient.spec())
                .body(new User("Shrimathi", "SDET II"))
        .when()
                .put("/users/" + id)
        .then()
                .statusCode(200)
                .body("job", equalTo("SDET II"));
    }
}
