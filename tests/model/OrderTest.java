package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    void emptyOrder_totalIsZero() {
        Customer c = new Customer("Dilay", "dilay@hotmail.com", "Avcılar", "05558754621");
        Restaurant r = new Restaurant("Chef's Corner", 4.6);
        Order o = new Order(c, r);

        assertEquals(0.0, o.getTotal(), 0.0001);
    }
}
