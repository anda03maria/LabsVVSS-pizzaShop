package pizzashop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new PaymentRepository();
    }

    @Test
    void testAddPayment() {
        Payment mockPayment = mock(Payment.class);
        when(mockPayment.getTableNumber()).thenReturn(1);
        when(mockPayment.getType()).thenReturn(PaymentType.CARD);
        when(mockPayment.getAmount()).thenReturn(50.0);

        int sizeBefore = repo.getAll().size();
        repo.add(mockPayment);
        int sizeAfter = repo.getAll().size();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertTrue(repo.getAll().contains(mockPayment));
    }

    @Test
    void testGetAllReturnsCorrectList() {
        Payment payment1 = mock(Payment.class);
        Payment payment2 = mock(Payment.class);

        repo.add(payment1);
        repo.add(payment2);

        List<Payment> result = repo.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(payment1));
        assertTrue(result.contains(payment2));
    }
}
