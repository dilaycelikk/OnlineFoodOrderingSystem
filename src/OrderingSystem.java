import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set; 

import model.Customer;
import model.MenuCsvReader;
import model.MenuItem;
import model.Order;
import model.Restaurant;
import payment.CashPayment;
import payment.CreditCardPayment;

public class OrderingSystem {

    public void run() {
        Scanner sc = new Scanner(System.in); 

        System.out.println("WELCOME TO THE CHEF'S CORNER");

        // get customer information from user
        String name = readName(sc, "Full Name: ");
        String email = readEmail(sc, "Email: ");
        String address = readAddress(sc, "Address: ");
        String phone = readPhone(sc, "Phone: ");

        
        // create objects
        Customer customer = new Customer(name, email, address, phone);
        Restaurant restaurant = new Restaurant("Chef's Corner", 4.6);
        Order order = new Order(customer, restaurant);
        
        
       // load menu from the menu.csv
        Map<Integer, MenuItem> menu = MenuCsvReader.loadAsMap("menu.csv");
        if (menu.isEmpty()) {
            System.out.println("menu.csv not found!");
            return;
        }

        String lastMessage = ""; 

      
        while (true) {
            printMenu(menu);
            order.showCart();

            
            if (!lastMessage.isEmpty()) {
                System.out.println(lastMessage);
                lastMessage = ""; 
            }

            int choice = readInt(sc, "ID to Add | -ID to Remove | 0 to Pay: ");

            //finish order
            if (choice == 0) {
                if (order.getTotal() == 0) {
                    lastMessage = " Cart is empty! Please add items from the menu. ";
                    continue;
                }
                break;
            }

             // negative numbers remove items
            if (choice < 0) {
                if (order.getTotal() == 0) {
                    lastMessage = " Cart is empty! Nothing to remove.";
                    continue; 
                }
                int idToRemove = -choice;
                boolean removed = order.removeItem(idToRemove);
                
                if (removed) {
                    lastMessage = " Removed item ID: " + idToRemove;
                } else {
                    lastMessage = " Item not found in cart.";
                }
                continue; 
            }

            // positive numbers add items
            MenuItem selected = menu.get(choice);
            if (selected == null) {
                lastMessage = " Invalid Product ID.";
                continue;
            }

            order.addItem(selected);
            lastMessage = " Added to cart: " + selected.getName();
        }

       
        System.out.println("\n--------------------------------");
        System.out.println("USE CODE 'SPECIAL10' TO GET 10% OFF YOUR FIRST ORDER!");
        System.out.print(" Enter Coupon Code or press Enter): ");
        System.out.flush();
        
        String coupon = sc.nextLine().trim();
        
        if (coupon.equalsIgnoreCase("SPECIAL10")) {
            order.applyDiscount();
            System.out.println("SUCCESS: 10% Discount Applied!");
            System.out.println(" New Total: " + order.getTotal() + " TL");
        } else {
            if(!coupon.isEmpty()) System.out.println(" Coupon invalid or expired.");
        }
        System.out.println("--------------------------------\n");

        
        int payChoice = readIntInSet(sc, "Payment Method (1-Cash, 2-Card): ", Set.of(1, 2));
        if (payChoice == 1) {
            order.setPayment(new CashPayment());
        } else {
            order.setPayment(new CreditCardPayment());
        }

        order.placeOrder();
    }
    
    private void printMenu(Map<Integer, MenuItem> menu) {
        System.out.println("\n========== MENU ==========");

        ArrayList<Integer> ids = new ArrayList<>(menu.keySet());
        Collections.sort(ids);

        Map<String, ArrayList<MenuItem>> grouped = new LinkedHashMap<>();
        for (int id : ids) {
            MenuItem it = menu.get(id);
            grouped.computeIfAbsent(it.getCategory(), k -> new ArrayList<>()).add(it);
        }

        for (Entry<String, ArrayList<MenuItem>> entry : grouped.entrySet()) {
            String category = entry.getKey();
            System.out.println("\n--- " + category + " ---"); 

            for (MenuItem it : entry.getValue()) {
                System.out.println("[" + it.getId() + "] " + it.getName() + " - " + it.getPrice() + " TL");
                System.out.println("    (" + it.getContent() + ")");
            }
        }
        System.out.println("==========================");
    }

    // ---------- SAFE INPUTS ----------
    
    private String readName(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            System.out.flush();

            String name = sc.nextLine().trim();

            if (name.matches("[A-Za-zÇĞİÖŞÜçğıöşü ]{2,50}")) {
                return name;
            }

            System.out.println("Name must contain only letters and spaces!");
        }
    }


    private String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            System.out.flush();

            String s = sc.nextLine().trim();
            if (!s.isEmpty()) {
                return s;
            }

            System.out.println("Empty input is not allowed!");
        }
    }
    
    // email must contain @ and .
    private String readEmail(Scanner sc, String prompt) {
        while (true) {
            String e = readNonEmpty(sc, prompt);

            if (e.contains("@") && e.contains(".") && e.indexOf('@') > 0) {
                return e;
            }

            System.out.println("Please enter a valid email!");
        }
    }

    private String readPhone(Scanner sc, String prompt) {
        while (true) {
            String p = readNonEmpty(sc, prompt);

            if (p.matches("\\d{10,15}")) {
                return p;
            }

            System.out.println("Phone must be digits only (10-15 digits)!");
        }
    }
     
    private String readAddress(Scanner sc, String prompt) {
        while (true) {
            String a = readNonEmpty(sc, prompt);

            if (a.length() < 5) {
                System.out.println("Address is too short!");
                continue;
            }

            for (int i = 0; i < a.length(); i++) {
                if (Character.isLetter(a.charAt(i))) {
                    return a;
                }
            }

            System.out.println("Address cannot be only numbers!");
        }
    }


    private int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            System.out.flush();

            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }
    }

    private int readIntInSet(Scanner sc, String prompt, Set<Integer> allowed) {
        while (true) {
            int v = readInt(sc, prompt);

            if (allowed.contains(v)) {
                return v;
            }

            System.out.println("Wrong choice!");
        }
    }
}