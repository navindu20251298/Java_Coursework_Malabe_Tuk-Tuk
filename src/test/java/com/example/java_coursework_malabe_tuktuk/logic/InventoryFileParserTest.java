package com.example.java_coursework_malabe_tuktuk.parser;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryFileParserTest {

    @Test
    public void testParsesCommaSeparatedLine() {
        InventoryFileParser parser = new InventoryFileParser();
        String line = "P001,Bajaj Shock Absorber,Bajaj,20240.0,25,bodywork,12-12-2021,piston.jpg,23";

        Part part = parser.parseLine(line);

        assertEquals("P001", part.getPartCode());
        assertEquals("Bajaj Shock Absorber", part.getName());
        assertEquals("Bajaj", part.getBrand());
        assertEquals(20240.0, part.getPrice(), 0.01);
        assertEquals(25, part.getQuantity());
        assertEquals("bodywork", part.getCategory());
        assertEquals(23, part.getThreshold());
        assertEquals("piston.jpg", part.getImagePath());
    }

    @Test
    public void testParsesPipeSeparatedLine() {
        InventoryFileParser parser = new InventoryFileParser();
        String line = "P002|TVS King Brake Pad|TVS|1250|7|brakes|12-05-2023|battery.jpg|10";

        Part part = parser.parseLine(line);

        assertEquals("P002", part.getPartCode());
        assertEquals("TVS King Brake Pad", part.getName());
        assertEquals(1250.0, part.getPrice(), 0.01);
        assertEquals(7, part.getQuantity());
        assertEquals("brakes", part.getCategory());
        assertEquals(10, part.getThreshold());
        assertEquals("battery.jpg", part.getImagePath());
    }

    @Test
    public void testParsesSemicolonSeparatedLineWithExtraSpacesAndMissingBrand() {
        InventoryFileParser parser = new InventoryFileParser();
        String line = "P003; 205/50-10 Tyre ;  ; 6500.00 ;9;BodyWork;15-10-2023;tyre.jpg;20";

        Part part = parser.parseLine(line);

        assertEquals("P003", part.getPartCode());
        assertEquals("205/50-10 Tyre", part.getName());
        assertEquals("", part.getBrand());
        assertEquals(6500.0, part.getPrice(), 0.01);
        assertEquals(9, part.getQuantity());
        assertEquals("bodywork", part.getCategory());
        assertEquals(20, part.getThreshold());
        assertEquals("tyre.jpg", part.getImagePath());
    }

    @Test
    public void testNormalizesCategoryCapitalization() {
        InventoryFileParser parser = new InventoryFileParser();
        String line1 = "P004,Spark Plug NGK,NGK,850,36,Electrical,05-01-2024,bulb.jpg,15";
        String line2 = "P005,Another Part,Brand,900,10,electrical,05-01-2024,,15";

        Part part1 = parser.parseLine(line1);
        Part part2 = parser.parseLine(line2);

        assertEquals(part1.getCategory(), part2.getCategory());
    }

    @Test
    public void testHandlesMissingThresholdAndImageWithDefaults() {
        InventoryFileParser parser = new InventoryFileParser();
        String line = "P006,Headlight Bulb 12V,,450,8,electrical,20-11-2023";

        Part part = parser.parseLine(line);

        assertEquals("P006", part.getPartCode());
        assertEquals(10, part.getThreshold());
        assertEquals("", part.getImagePath());
    }

    @Test
    public void testHandlesInvalidPriceGracefully() {
        InventoryFileParser parser = new InventoryFileParser();
        String line = "P007,Broken Price Item,Brand,notanumber,5,engine,01-01-2024,,10";

        Part part = parser.parseLine(line);

        assertEquals(0.0, part.getPrice(), 0.01);
    }

    @Test
    public void testHandlesInvalidDateGracefully() {
        InventoryFileParser parser = new InventoryFileParser();
        String line = "P008,Bad Date Item,Brand,500,5,engine,notadate,,10";

        Part part = parser.parseLine(line);

        assertNotNull(part.getAddDate());
    }
}