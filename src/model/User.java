package model;

/**
 * Represents a user in the system.
 * Stores name and email information.
 */

public class User {

	private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}