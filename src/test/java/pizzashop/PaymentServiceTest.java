package pizzashop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.OrderService;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PaymentService Test Suite")
@TestMethodOrder(OrderAnnotation.class)
class PaymentServiceTest {
    private PaymentRepository payRepo;
    private OrderService paymentService;

    private MenuRepository menuRepo;

    @BeforeEach
    void setUp() {
        payRepo = new PaymentRepository();
        menuRepo = new MenuRepository();
        paymentService = new OrderService(menuRepo, payRepo);
    }

    @Nested
    @DisplayName("Equivalence Class Partitioning Tests")
    class EquivalenceClassPartitioningTests {

        @Test
        @Order(1)
        @DisplayName("TC1: Valid amount should be added successfully")
        void testAddPayment_ValidAmount() {
            int table = 1;
            PaymentType type = PaymentType.CASH;
            double amount = 50.0; // Valoare validă (>0)

            paymentService.addPayment(table, type, amount);

            assertEquals(1, payRepo.getAll().size());
            Payment expected = new Payment(1, PaymentType.CASH, 50.0);
            Payment actual = payRepo.getAll().get(0);

            assertEquals(expected.getTableNumber(), actual.getTableNumber());
            assertEquals(expected.getType(), actual.getType());
            assertEquals(expected.getAmount(), actual.getAmount(), 0.001);

        }

        @Test
        @Order(2)
        @DisplayName("TC2: Invalid amount (<=0) should throw IllegalArgumentException")
        void testAddPayment_InvalidAmount() {
            int table = 1;
            PaymentType type = PaymentType.CASH;
            double amount = -10.0; // Valoare invalidă (<=0)

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                paymentService.addPayment(table, type, amount);
            });

            assertEquals("Amount must be greater than zero", exception.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("TC3: Null PaymentType should throw IllegalArgumentException")
    void testAddPayment_NullPaymentType() {
        int table = 1;
        PaymentType type = null;
        double amount = 50.0; // Valoare validă

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(table, type, amount);
        });

        assertEquals("Payment type cannot be null", exception.getMessage());
    }

    @Nested
    @DisplayName("Boundary Value Analysis Tests")
    class BoundaryValueAnalysisTests {

        @Test
        @Order(4)
        @DisplayName("TC4: Minimum valid table number (1) should be accepted")
        void testAddPayment_MinValidTable() {
            int table = 1;
            PaymentType type = PaymentType.CASH;
            double amount = 50.0;

            assertDoesNotThrow(() -> paymentService.addPayment(table, type, amount));
        }

        @Test
        @Order(5)
        @DisplayName("TC5: Maximum valid table number (8) should be accepted")
        void testAddPayment_MaxValidTable() {
            int table = 8;
            PaymentType type = PaymentType.CASH;
            double amount = 50.0;

            assertDoesNotThrow(() -> paymentService.addPayment(table, type, amount));
        }

        @Test
        @Order(6)
        @DisplayName("TC6: Table number below valid range (0) should throw IllegalArgumentException")
        void testAddPayment_BelowMinTable() {
            int table = 0;
            PaymentType type = PaymentType.CASH;
            double amount = 50.0;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                paymentService.addPayment(table, type, amount);
            });

            assertEquals("Table number must be between 1 and 8", exception.getMessage());
        }

        @Test
        @Order(7)
        @DisplayName("TC7: Table number above valid range (9) should throw IllegalArgumentException")
        void testAddPayment_AboveMaxTable() {
            int table = 9;
            PaymentType type = PaymentType.CASH;
            double amount = 50.0;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                paymentService.addPayment(table, type, amount);
            });

            assertEquals("Table number must be between 1 and 8", exception.getMessage());
        }
    }
}
