package pizzashop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTestInt3 {

    private PaymentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new PaymentRepository();
    }

    @Test
    void testAddPayment() {
        Payment payment = new Payment(1, PaymentType.CARD, 50.0);

        int sizeBefore = repo.getAll().size();
        repo.add(payment);
        int sizeAfter = repo.getAll().size();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertTrue(repo.getAll().contains(payment));
    }

    @Test
    void testGetAllReturnsCorrectList() {
        Payment payment1 = new Payment(2, PaymentType.CASH, 20.0);
        Payment payment2 = new Payment(3, PaymentType.CARD, 35.0);

        repo.add(payment1);
        repo.add(payment2);

        List<Payment> result = repo.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(payment1));
        assertTrue(result.contains(payment2));
    }
}

