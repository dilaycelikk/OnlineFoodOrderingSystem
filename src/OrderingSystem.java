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

/**
 * Runs the ordering system.
 * Shows the menu and creates orders.
 */

public class OrderingSystem {

    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Chef's Corner");

        String name = readName(sc, "Full Name: ");
        String email = readEmail(sc, "Email: ");
        String address = readNonEmpty(sc, "Address: ");
        String phone = readPhone(sc, "Phone: ");

        Customer customer = new Customer(name, email, address, phone);
        Restaurant restaurant = new Restaurant("Chef's Corner", 4.6);
        Order order = new Order(customer, restaurant);

        Map<Integer, MenuItem> menu = MenuCsvReader.loadAsMap("menu.csv");
        if (menu.isEmpty()) {
            System.out.println("Menu could not be loaded! Check menu.csv location.");
            return;
        }

        while (true) {
            printMenu(menu);

            int choice = readInt(sc, "Choose (0 or item id): ");

            /**
             * Checks if the user can finish the order.
             * Prevents finishing when the cart is empty.
             */
            if (choice == 0) {
                if (order.getTotal() == 0) {
                    System.out.println("Cart is empty! Please add a product.");
                    continue;
                }
                break;
            }

            MenuItem selected = menu.get(choice);
            if (selected == null) {
                System.out.println("Wrong choice!");
                continue;
            }

            order.addItem(selected);

            System.out.println("Added: [" + selected.getId() + "] " + selected.getName());
            System.out.println("Total: " + order.getTotal() + " TL");

            boolean more = readYesNo(sc, "Add another product? (y/n): ");
            if (!more) {
                break;
            }
        }

        int payChoice = readIntInSet(sc, "Payment (1-Cash, 2-Credit Card): ", Set.of(1, 2));
        if (payChoice == 1) {
            order.setPayment(new CashPayment());
        } else {
            order.setPayment(new CreditCardPayment());
        }

        order.placeOrder();
    }
    
    /** Prints menu items grouped by category. */

    private void printMenu(Map<Integer, MenuItem> menu) {
        System.out.println("\n=== MENU ===\n");

        ArrayList<Integer> ids = new ArrayList<>(menu.keySet());
        Collections.sort(ids);

       
        Map<String, ArrayList<MenuItem>> grouped = new LinkedHashMap<>();
        for (int id : ids) {
            MenuItem it = menu.get(id);
            grouped.computeIfAbsent(it.getCategory(), k -> new ArrayList<>()).add(it);
        }

        for (Entry<String, ArrayList<MenuItem>> entry : grouped.entrySet()) {
            String category = entry.getKey();
            System.out.println(category.toUpperCase());

            for (MenuItem it : entry.getValue()) {
                System.out.println(it.getId() + ") " + it.getName() + " - " + it.getPrice() + " TL");
                System.out.println("   -> " + shorten(it.getContent(), 60));
            }
            System.out.println();
        }

        System.out.println("0) Finish");
    }

    private String shorten(String s, int maxLen) {
        if (s == null) {
            return "";
        }
        s = s.trim();
        if (s.length() <= maxLen) {
            return s;
        }
        return s.substring(0, maxLen - 3) + "...";
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

    private boolean readYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            System.out.flush();

            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) {
                return true;
            }
            if (s.equals("n") || s.equals("no")) {
                return false;
            }

            System.out.println("Please type y or n!");
        }
    }
}
