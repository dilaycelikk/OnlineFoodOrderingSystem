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
    
    
    private boolean discountApplied = false; 

    public Order(Customer customer, Restaurant restaurant) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.cart = new ArrayList<>();

        Random rnd = new Random();
        this.orderTime = LocalDateTime.now();           // sets the order time to now
        this.deliveryMinutes = rnd.nextInt(16) + 30;    //random delivery time between 30 and 45 minutes
        this.orderNumber = rnd.nextInt(9000) + 1000;   // random order number between 1000 and 9999
    }

    public void addItem(MenuItem item) {
        cart.add(item);
    }
    
   
    public boolean removeItem(int id) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == id) {
                cart.remove(i);
                return true;
            }
        }
        return false;
    }

    // activates the discount
    public void applyDiscount() {
        this.discountApplied = true;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

   
    public double getTotal() {
        double total = 0;
        for (MenuItem item : cart) {
            total += item.getPrice();
        }
        
        if (discountApplied) {
            return total * 0.90; // %10 discount
        }
        
        return total;
    }

    
    public void showCart() {
        System.out.println("\n--- Current Cart ---");
        
        if (cart.isEmpty()) {
            System.out.println("(Empty)");
        } else {
            for (MenuItem item : cart) {
                System.out.println("* [" + item.getId() + "] " + item.getName() + " : " + item.getPrice() + " TL");
            }
         
            if (discountApplied) {
                System.out.println("DISCOUNT APPLIED: 10% OFF");
            }
            System.out.println("TOTAL: " + getTotal() + " TL");
        }
        System.out.println("--------------------");
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
        
      
        if (discountApplied) {
            System.out.println("\n(Special Discount: 10% Applied)");
        }

        System.out.println("\nTOTAL: " + getTotal() + " TL");
        System.out.println("\nThank you for your order!");
    }
} 