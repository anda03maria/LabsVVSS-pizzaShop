package pizzashop.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

class PaymentTest {

    @Test
    void testConstructorAndGetters() {
        Payment payment = new Payment(5, PaymentType.CASH, 50.0);

        assertEquals(5, payment.getTableNumber());
        assertEquals(PaymentType.CASH, payment.getType());
        assertEquals(50.0, payment.getAmount());
    }

    @Test
    void testSetters() {
        Payment payment = new Payment(1, PaymentType.CARD, 20.0);
        payment.setAmount(100.0);
        payment.setTableNumber(3);
        payment.setType(PaymentType.CASH);

        assertEquals(3, payment.getTableNumber());
        assertEquals(PaymentType.CASH, payment.getType());
        assertEquals(100.0, payment.getAmount());
    }
}
