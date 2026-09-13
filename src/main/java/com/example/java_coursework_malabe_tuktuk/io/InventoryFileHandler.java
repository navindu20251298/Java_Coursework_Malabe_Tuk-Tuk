import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryFileHandler {

    private InventoryFileParser parser = new InventoryFileParser();

    public List<Part> loadInventory(String filePath) {
        List<Part> parts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                try {
                    Part part = parser.parseLine(line);
                    parts.add(part);
                } catch (Exception e) {
                    System.out.println("Skipping bad line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read inventory file: " + e.getMessage());
        }

        return parts;
    }

    public void saveInventory(String filePath, List<Part> parts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            for (Part p : parts) {
                String line = p.getPartCode() + "," + p.getName() + "," + p.getBrand() + ","
                        + p.getCategory() + "," + p.getPrice() + "," + p.getQuantity() + ","
                        + p.getAddDate() + "," + p.getThreshold() + "," + p.getImagePath();
                writer.println(line);
            }

        } catch (IOException e) {
            System.out.println("Could not save inventory file: " + e.getMessage());
        }
    }
}
