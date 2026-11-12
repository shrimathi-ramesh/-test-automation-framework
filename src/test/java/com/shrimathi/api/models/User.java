package com.shrimathi.api.models;

/**
 * POJO (Plain Old Java Object) for a user request body.
 *
 * REST Assured + Jackson turn this object into JSON automatically (serialization):
 *     new User("Shrimathi", "SDET")  ->  {"name":"Shrimathi","job":"SDET"}
 *
 * Why use a POJO instead of a JSON string?
 *  - No typos in hand-written JSON
 *  - Easy to reuse and change test data
 *  - Encapsulation: private fields + getters/setters (OOP)
 */
public class User {

    private String name;
    private String job;

    // Jackson needs a no-argument constructor
    public User() {
    }

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
