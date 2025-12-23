package payment;

/**
 * CreditCardPayment class implements Payment interface.
 */

public class CreditCardPayment implements Payment {
	
/**
* Pays with credit card.
*/

    @Override
    public void pay(double amount) {
    	
        System.out.println("Credit Card Payment: " + amount + " TL");
    }
}
