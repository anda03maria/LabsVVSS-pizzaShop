package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private PaymentRepository paymentRepoMock;
    private MenuRepository menuRepoMock;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        paymentRepoMock = mock(PaymentRepository.class);
        menuRepoMock = mock(MenuRepository.class);
        orderService = new OrderService(menuRepoMock, paymentRepoMock);
    }

    @Test
    void testAddPayment() {
        orderService.addPayment(1, PaymentType.CASH, 20.5);

        // Verificăm că metoda add() din PaymentRepository a fost apelată
        verify(paymentRepoMock, times(1)).add(any(Payment.class));
    }

    @Test
    void testGetTotalAmountFiltersCorrectly() {
        // Mock-uri pentru Payment
        Payment p1 = mock(Payment.class);
        when(p1.getType()).thenReturn(PaymentType.CASH);
        when(p1.getAmount()).thenReturn(10.0);

        Payment p2 = mock(Payment.class);
        when(p2.getType()).thenReturn(PaymentType.CARD);
        when(p2.getAmount()).thenReturn(20.0);

        Payment p3 = mock(Payment.class);
        when(p3.getType()).thenReturn(PaymentType.CASH);
        when(p3.getAmount()).thenReturn(15.0);

        List<Payment> mockPayments = Arrays.asList(p1, p2, p3);
        when(paymentRepoMock.getAll()).thenReturn(mockPayments);

        double total = orderService.getTotalAmount(PaymentType.CASH);
        assertEquals(25.0, total, 0.01);
    }
}
