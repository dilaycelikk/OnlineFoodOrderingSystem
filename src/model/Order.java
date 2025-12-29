package model;

import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import payment.Payment;

public class Order {
    private Customer customer;
    private Restaurant restaurant;
    private ArrayList<MenuItem> cart;
    private Payment payment;

    
    private LocalDateTime orderTime;
    private int deliveryMinutes;
    private int orderNumber;

    public Order(Customer customer, Restaurant restaurant) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.cart = new ArrayList<>();

        Random rnd = new Random();
        this.orderTime = LocalDateTime.now();
        this.deliveryMinutes = rnd.nextInt(16) + 30;   
        this.orderNumber = rnd.nextInt(9000) + 1000;   
    }

    public void addItem(MenuItem item) {
        cart.add(item);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getTotal() {
        double total = 0;
        for (MenuItem item : cart) {
            total += item.getPrice();
        }
        return total;
    }

    public void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        if (payment == null) {
            System.out.println("Payment type not selected!");
            return;
        }

        payment.pay(getTotal());
        printSummary();
    }

    public void printSummary() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime deliveryTime = orderTime.plusMinutes(deliveryMinutes);

        System.out.println("\n=== ORDER SUMMARY ===");
        System.out.println("Order No: #" + orderNumber);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Restaurant: " + restaurant.getName());
        System.out.println("Rating: " + restaurant.getRating() + "/5");

        System.out.println("\nOrder time: " + orderTime.format(fmt));
        System.out.println("Delivery time: " + deliveryMinutes + " min");
        System.out.println("Delivery at: " + deliveryTime.format(fmt));
        System.out.println("----------------------------");

        System.out.println("\nItems:");
        for (MenuItem item : cart) {
            System.out.println("- [" + item.getId() + "] " + item.getName() + " (" + item.getPrice() + " TL)");
        }

        System.out.println("\nTOTAL: " + getTotal() + " TL");
        System.out.println("\nThank you for your order!");
    }
}

