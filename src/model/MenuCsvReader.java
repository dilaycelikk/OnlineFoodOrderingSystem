package model;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads menu products from a CSV file (id;category;name;price;content).
 */

public class MenuCsvReader {

    public static Map<Integer, MenuItem> loadAsMap(String fileName) {
        Map<Integer, MenuItem> menu = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.equalsIgnoreCase("id;category;name;price;content")) {
                    continue;
                }

                String[] parts = line.split(";", 5);

                int id = Integer.parseInt(parts[0].trim());
                String category = parts[1].trim();
                String name = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                String content = parts[4].trim();

                menu.put(id, new MenuItem(id, category, name, price, content));
            }
        } catch (Exception e) {
            System.out.println("menu.csv not found.");
        }

        return menu;
    }
}
