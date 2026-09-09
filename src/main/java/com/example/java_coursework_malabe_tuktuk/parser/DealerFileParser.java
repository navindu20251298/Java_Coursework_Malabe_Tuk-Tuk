package com.example.java_coursework_malabe_tuktuk.parser;

import com.example.java_coursework_malabe_tuktuk.model.Dealer;

public class DealerFileParser {

    private LegacyLineParser lineParser = new LegacyLineParser();

    public Dealer parseLine(String rawLine) {

        String[] fields = lineParser.splitLine(rawLine);

        String dealerId = fields.length > 0 ? fields[0] : "";
        String name = fields.length > 1 ? fields[1] : "Unknown";
        String location = fields.length > 2 ? fields[2] : "Unknown";
        String contact = fields.length > 3 ? fields[3] : "";

        return new Dealer(dealerId, name, location, contact);
    }
}