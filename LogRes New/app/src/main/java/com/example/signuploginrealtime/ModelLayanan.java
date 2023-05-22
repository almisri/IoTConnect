package com.example.signuploginrealtime;

public class ModelLayanan {
    private String name;
    private String activity;
    private String key;


    public ModelLayanan(){

    }

    public ModelLayanan(String name, String activity) {
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
