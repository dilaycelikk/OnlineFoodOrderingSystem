package model;

/**
 * Represents one menu item (id, category, name, price, content).
 */

public class MenuItem {
    private int id;
    private String category;
    private String name;
    private double price;
    private String content;

    public MenuItem(int id, String category, String name, double price, String content) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getContent() {
        return content;
    }
}

