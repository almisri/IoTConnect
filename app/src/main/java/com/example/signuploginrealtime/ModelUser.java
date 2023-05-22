package com.example.signuploginrealtime;

public class ModelUser {
    private String name;
    private String key;

    public ModelUser() {

    }

    public ModelUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
