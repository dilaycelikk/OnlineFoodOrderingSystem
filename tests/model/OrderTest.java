package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class OrderTest {

    /**
     * Boş siparişin toplamı 0 olmalı.
     */
    @Test
    void emptyOrder_totalIsZero() {
        Customer c = new Customer("Dilay", "dilay@mail.com", "Avcılar", "05558754621");
        Restaurant r = new Restaurant("Chef's Corner", 4.6);
        Order o = new Order(c, r);

        assertEquals(0.0, o.getTotal(), 0.0001);
    }

    /**
     * Ürün ekleyince tutar artmalı.
     */
    @Test
    void addingItem_shouldIncreaseTotal() {
        Customer c = new Customer("Elifsu", "test@mail.com", "Adres", "123");
        Restaurant r = new Restaurant("Chef's Corner", 5.0);
        Order o = new Order(c, r);
        MenuItem item = new MenuItem(1, "Yemek", "Test Yemek", 100.0, "İçerik");

        o.addItem(item);

        assertEquals(100.0, o.getTotal(), 0.0001);
    }

    /**
     * Ürün çıkarınca tutar düşmeli.
     */
    @Test
    void removingItem_shouldDecreaseTotal() {
        Customer c = new Customer("Yaren", "test@mail.com", "Adres", "123");
        Restaurant r = new Restaurant("Restoran", 5.0);
        Order o = new Order(c, r);
        MenuItem item = new MenuItem(1, "Yemek", "Test Yemek", 100.0, "İçerik");

        o.addItem(item);
        o.removeItem(1);

        assertEquals(0.0, o.getTotal(), 0.0001);
    }

    /**
     * İndirim kodu %10 düşürmeli.
     */
    @Test
    void discount_shouldReduceTotalBy10Percent() {
        Customer c = new Customer("Dilek", "test@mail.com", "Adres", "123");
        Restaurant r = new Restaurant("Restoran", 5.0);
        Order o = new Order(c, r);
        MenuItem item = new MenuItem(1, "Yemek", "Test Yemek", 100.0, "İçerik");

        o.addItem(item);
        o.applyDiscount();

        assertEquals(90.0, o.getTotal(), 0.0001);
 
      }

    /**
     * Kullanıcı sepet boşken sipariş verildiğinde 
     * sistemin çökmesi (Exception) yerine, sadece uyarı verip menüye dönmesi test edilir.
     */
    @Test
    void orderingEmptyCart_shouldNotProcessPayment() {
        Customer c = new Customer("Gizem", "test@mail.com", "Adres", "123");
        Restaurant r = new Restaurant("Restoran", 5.0);
        Order o = new Order(c, r);

        assertDoesNotThrow(() -> {
            o.placeOrder();
        });

        assertEquals(0.0, o.getTotal());
    }
} 

