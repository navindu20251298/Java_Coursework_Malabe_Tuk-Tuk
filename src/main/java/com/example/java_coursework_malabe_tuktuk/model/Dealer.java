package com.example.java_coursework_malabe_tuktuk.model;

public class Dealer {
    private String dealerId;
    private String name;
    private String contact;
    private String location;

    public Dealer(String dealerId, String name, String contact, String location) {
        this.dealerId = dealerId;
        this.name = name;
        this.contact = contact;
        this.location = location;
    }

    public String getDealerId() {
        return dealerId;
    }
    public String getName() {
        return name;
    }
    public String getContact() {
        return contact;
    }

    public String getLocation() {
        return location;
    }
}
