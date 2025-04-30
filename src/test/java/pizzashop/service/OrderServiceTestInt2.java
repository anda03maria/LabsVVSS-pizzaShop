package pizzashop.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTestInt2 {

    private PaymentRepository paymentRepo;
    private MenuRepository menuRepoMock;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        // Repo real pentru test de integrare
        paymentRepo = new PaymentRepository();

        // Dacă nu e folosit, putem lăsa mock
        menuRepoMock = mock(MenuRepository.class);

        orderService = new OrderService(menuRepoMock, paymentRepo);
    }

    @Test
    void testAddPaymentIntegration() {
        int initialSize = paymentRepo.getAll().size();

        // Obiect Payment fals (mock) doar pentru verificare
        Payment paymentMock = mock(Payment.class);
        when(paymentMock.getAmount()).thenReturn(10.0);
        when(paymentMock.getType()).thenReturn(PaymentType.CARD);

        // Se adaugă un Payment real prin metoda serviciului (constructorul va crea unul nou)
        orderService.addPayment(1, PaymentType.CARD, 10.0);

        int newSize = paymentRepo.getAll().size();
        assertEquals(initialSize + 1, newSize);
    }

    @Test
    void testGetTotalAmountIntegration() {
        paymentRepo = new PaymentRepository();
        menuRepoMock = mock(MenuRepository.class);
        orderService = new OrderService(menuRepoMock, paymentRepo);

        // Adăugăm plăți reale
        orderService.addPayment(1, PaymentType.CASH, 10.0);
        orderService.addPayment(2, PaymentType.CARD, 5.0);
        orderService.addPayment(3, PaymentType.CASH, 15.0);

        double totalCash = orderService.getTotalAmount(PaymentType.CASH);
        assertEquals(25.0, totalCash, 0.01);
    }
}

