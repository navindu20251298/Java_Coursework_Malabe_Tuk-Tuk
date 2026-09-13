package com.example.java_coursework_malabe_tuktuk.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {

    private static final String LOG_FILE = "audit_log.txt";

    public void log(String action, String itemCode, int quantity) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logLine = timestamp + " | Action: " + action + " | Item: " + itemCode + " | Qty: " + quantity;

        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println(logLine);

        } catch (IOException e) {
            System.out.println("Could not write to audit log: " + e.getMessage());
        }
    }
}