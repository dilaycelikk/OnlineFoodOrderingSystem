package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Map;

public class MenuCsvReaderTest {

    /**
     * menu.csv dosyası başarıyla yüklenmeli.
     */
    @Test
    void menuFileShouldLoad() {
        Map<Integer, MenuItem> menu = MenuCsvReader.loadAsMap("menu.csv");

        assertNotNull(menu);           // Menü null olmamalı
        assertFalse(menu.isEmpty());   // Menü boş gelmemeli
    }

    /**
     * İlk ürün Margherita (ID: 1) olmalı.
     */
    @Test
    void firstItemShouldBeMargherita() {
        Map<Integer, MenuItem> menu = MenuCsvReader.loadAsMap("menu.csv");

        assertTrue(menu.containsKey(1)); // 1 numaralı ID var mı?

        MenuItem item = menu.get(1);
        assertEquals("Margherita", item.getName());
        assertEquals(200.0, item.getPrice(), 0.0001);
    }
}