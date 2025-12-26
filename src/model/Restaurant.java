package model;

/**
 * Stores restaurant information (name and rating).
 */
public class Restaurant {
    private String name;
    private double rating;

    public Restaurant(String name, double rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
}
