package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MenuCsvReaderTest {

    @Test
    void menuFileShouldLoad() {
        Map<Integer, MenuItem> menu = MenuCsvReader.loadAsMap("menu.csv");

        assertNotNull(menu);
        assertFalse(menu.isEmpty());
    }

    @Test
    void firstItemShouldBeMargherita() {
        Map<Integer, MenuItem> menu = MenuCsvReader.loadAsMap("menu.csv");

        assertTrue(menu.containsKey(1));

        MenuItem item = menu.get(1);
        assertEquals("Margherita", item.getName());
        assertEquals(200.0, item.getPrice(), 0.0001);
    }
}
