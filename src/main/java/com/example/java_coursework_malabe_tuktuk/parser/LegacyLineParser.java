package com.example.java_coursework_malabe_tuktuk.parser;

public class LegacyLineParser {
    public String[] splitLine(String rawLine) {
        String delimiter;

        if (rawLine.contains("|")) {
            delimiter = "\\|";
        }
        else if (rawLine.contains(";")) {
            delimiter = ";";
        }
        else {
            delimiter = ",";
        }

        String[] rawParts = rawLine.split(delimiter);

        String[] cleanParts = new String[rawParts.length];
        for (int i = 0; i < rawParts.length; i++) {
            cleanParts[i] = rawParts[i].trim();
        }

        return cleanParts;
    }
}
