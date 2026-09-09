package com.example.java_coursework_malabe_tuktuk.parser;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InventoryFileParser {

    private LegacyLineParser lineParser = new LegacyLineParser();

    public Part parseLine(String rawLine) {

        String[] fields = lineParser.splitLine(rawLine);

        String partCode = fields.length > 0 ? fields[0] : "";
        String name = fields.length > 1 ? fields[1] : "Unknown";
        String brand = fields.length > 2 ? fields[2] : "";
        double price = fields.length > 3 ? parsePrice(fields[3]) : 0.0;
        int quantity = fields.length > 4 ? parseQuantity(fields[4]) : 0;
        String category = fields.length > 5 ? fields[5].toLowerCase() : "unknown";
        LocalDate addDate = fields.length > 6 ? parseDate(fields[6]) : LocalDate.now();
        String imagePath = fields.length > 7 ? fields[7] : "";
        int threshold = fields.length > 8 ? parseQuantity(fields[8]) : 10;

        return new Part(partCode, name, brand, category, price, quantity, addDate, threshold, imagePath);
    }

    private double parsePrice(String rawPrice) {
        String cleaned = rawPrice.replaceAll("[^0-9.]", "");
        if (cleaned.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseQuantity(String rawQty) {
        try {
            return Integer.parseInt(rawQty.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private LocalDate parseDate(String rawDate) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(rawDate.trim(), fmt);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}