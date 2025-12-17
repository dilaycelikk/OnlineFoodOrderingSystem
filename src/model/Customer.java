package model;

/**
 * Customer class.
 * Inherits basic user information from User.
 * Adds address and phone details.
 */

public class Customer extends User {

    private String address;
    private String phone;

    public Customer(String name, String email, String address, String phone) {
        super(name, email);
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
