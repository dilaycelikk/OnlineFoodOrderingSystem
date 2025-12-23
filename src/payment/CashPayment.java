package payment;

/**
 * CashPayment class implements Payment interface.
 */

public class CashPayment implements Payment {
	
/**
 * Pays with cash.
 */

    @Override
    public void pay(double amount) {
    	
        System.out.println("Cash Payment: " + amount + " TL");
    }
}
