package model;

/**
 * Represents a menu item in the Online Food Ordering System.
 * Each menu item has a name and a price.
 */

public class MenuItem {

private String name;
private double price;

public MenuItem(String name, double price) {
    this.name = name;
    this.price = price;
}

public String getName() { 
    return name; 
}

public double getPrice() { 
    return price; 
}
}