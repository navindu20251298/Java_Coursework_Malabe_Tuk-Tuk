package com.example.java_coursework_malabe_tuktuk.io;

import com.example.java_coursework_malabe_tuktuk.model.Dealer;
import com.example.java_coursework_malabe_tuktuk.parser.DealerFileParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DealerFileHandler {

    private DealerFileParser parser = new DealerFileParser();

    public List<Dealer> loadDealers(String filePath) {
        List<Dealer> dealers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                try {
                    Dealer dealer = parser.parseLine(line);
                    dealers.add(dealer);
                } catch (Exception e) {
                    System.out.println("Skipping bad dealer line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read dealer file: " + e.getMessage());
        }

        return dealers;
    }
}