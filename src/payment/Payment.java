package payment;

/**
 * All payment classes must implement this interface.
 * It defines the pay method.
 */

public interface Payment {
	
    void pay(double amount);
}
