package com.example.java_coursework_malabe_tuktuk.model;

import java.time.LocalDate;

public class Part {
    private String partCode;
    private String name;
    private String brand;
    private String category;
    private double price;
    private int quantity;
    public LocalDate addDate;
    private int threshold;
    private String imagePath;

    public Part(String partCode, String name, String brand, String category, double price, int quantity, LocalDate addDate, int threshold, String imagePath) {
        this.partCode = partCode;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.addDate = addDate;
        this.threshold = threshold;
        this.imagePath = imagePath;
    }
}
