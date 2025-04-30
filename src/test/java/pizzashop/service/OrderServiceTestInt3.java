package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTestInt3 {

    private PaymentRepository paymentRepo;
    private MenuRepository menuRepoMock;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        paymentRepo = new PaymentRepository(); // real
        menuRepoMock = null; // nefolosit aici
        orderService = new OrderService(menuRepoMock, paymentRepo);
    }

    @Test
    void testAddRealPaymentIntegration() {
        int initialSize = paymentRepo.getAll().size();

        orderService.addPayment(1, PaymentType.CARD, 30.0);

        assertEquals(initialSize + 1, paymentRepo.getAll().size());

        Payment lastPayment = paymentRepo.getAll().get(paymentRepo.getAll().size() - 1);
        assertEquals(1, lastPayment.getTableNumber());
        assertEquals(PaymentType.CARD, lastPayment.getType());
        assertEquals(30.0, lastPayment.getAmount(), 0.01);
    }

    @Test
    void testGetTotalAmountWithRealData() {
        // Adăugăm date reale
        orderService.addPayment(1, PaymentType.CASH, 5.0);
        orderService.addPayment(2, PaymentType.CARD, 7.5);
        orderService.addPayment(3, PaymentType.CASH, 2.5);

        double totalCash = orderService.getTotalAmount(PaymentType.CASH);
        assertEquals(7.5, totalCash, 0.01);
    }
}

