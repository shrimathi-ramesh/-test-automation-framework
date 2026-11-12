package com.shrimathi.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * ApiClient = ONE place for everything every API request needs.
 *
 * Why this matters (interview answer):
 *  - Base URL, headers, and content type are defined once, not copied into every test.
 *  - If the environment URL or an auth header changes, we update it here only.
 *  - Logging is switched on only when a validation fails, so failures are easy to debug.
 *
 * Same idea as BaseTest for UI tests — shared setup lives in one class.
 */
public class ApiClient {

    // In a real project these would come from a config file or environment variables,
    // never hardcoded (especially secrets like API keys or tokens).
    private static final String BASE_URL = System.getProperty("api.baseUrl", "https://reqres.in/api");
    private static final String API_KEY  = System.getProperty("api.key", "reqres-free-v1");

    private ApiClient() {
        // utility class — no objects needed
    }

    /** The shared request specification used by every API test. */
    public static RequestSpecification spec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("x-api-key", API_KEY)       // auth-style header sent on every request
                .setContentType(ContentType.JSON)       // we send JSON
                .setAccept(ContentType.JSON)            // we expect JSON back
                .log(LogDetail.METHOD)                  // print METHOD + URI for each call
                .build();
    }
}
