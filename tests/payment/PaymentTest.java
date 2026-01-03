package payment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PaymentTest {

    @Test
    void creditCardPaymentShouldWork() {
        Payment payment = new CreditCardPayment();

        assertDoesNotThrow(() -> {
            payment.pay(150.0);
        });
    }

    @Test
    void cashPaymentShouldWork() {
        Payment payment = new CashPayment();

        assertDoesNotThrow(() -> {
            payment.pay(100.0);
        });
    }
}
