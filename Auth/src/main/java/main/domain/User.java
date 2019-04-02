package main.domain;

import java.util.UUID;

public class User {

    private String id;
    private String name;

    public User() {}

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }
    public String getName() { return this.name; }
    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
    public void setName(String name) {
        this.name = name;
    }
}
