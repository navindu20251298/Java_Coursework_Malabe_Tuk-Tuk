package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.CartItem;
import com.example.java_coursework_malabe_tuktuk.model.Part;
import com.example.java_coursework_malabe_tuktuk.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutServiceTest {

    @Test
    public void testNoDiscountForSmallQuantity() {
        Part part = new Part("P001", "Item A", "Brand", "bodywork", 100.0, 10, LocalDate.now(), 5, "");
        CartItem item = new CartItem(part, 2);

        List<CartItem> cart = new ArrayList<>();
        cart.add(item);

        CheckoutService service = new CheckoutService();
        double discount = service.calculateDiscount(cart);

        assertEquals(0.0, discount, 0.01);
    }

    @Test
    public void testBulkDiscountAppliesAtThreeUnits() {
        Part part = new Part("P001", "Item A", "Brand", "bodywork", 100.0, 10, LocalDate.now(), 5, "");
        CartItem item = new CartItem(part, 3);

        List<CartItem> cart = new ArrayList<>();
        cart.add(item);

        CheckoutService service = new CheckoutService();
        double discount = service.calculateDiscount(cart);

        assertEquals(15.0, discount, 0.01);
    }

    @Test
    public void testSynergyDiscountWithEngineAndElectrical() {
        Part enginePart = new Part("P001", "Engine Part", "Brand", "engine", 100.0, 10, LocalDate.now(), 5, "");
        Part electricalPart = new Part("P002", "Electrical Part", "Brand", "electrical", 200.0, 10, LocalDate.now(), 5, "");

        CartItem item1 = new CartItem(enginePart, 1);
        CartItem item2 = new CartItem(electricalPart, 1);

        List<CartItem> cart = new ArrayList<>();
        cart.add(item1);
        cart.add(item2);

        CheckoutService service = new CheckoutService();
        double discount = service.calculateDiscount(cart);

        assertEquals(30.0, discount, 0.01);
    }

    @Test
    public void testBulkAndSynergyDiscountCombined() {
        Part enginePart = new Part("P001", "Engine Part", "Brand", "engine", 100.0, 10, LocalDate.now(), 5, "");
        Part electricalPart = new Part("P002", "Electrical Part", "Brand", "electrical", 100.0, 10, LocalDate.now(), 5, "");

        CartItem item1 = new CartItem(enginePart, 3);
        CartItem item2 = new CartItem(electricalPart, 1);

        List<CartItem> cart = new ArrayList<>();
        cart.add(item1);
        cart.add(item2);

        CheckoutService service = new CheckoutService();
        double discount = service.calculateDiscount(cart);

        assertEquals(53.5, discount, 0.01);
    }

    @Test
    public void testValidateCartThrowsOnEmptyCart() {
        List<CartItem> emptyCart = new ArrayList<>();

        CheckoutService service = new CheckoutService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.validateCart(emptyCart);
        });
    }

    @Test
    public void testValidateCartThrowsOnOverStock() {
        Part part = new Part("P001", "Item A", "Brand", "bodywork", 100.0, 5, LocalDate.now(), 5, "");
        CartItem item = new CartItem(part, 10);

        List<CartItem> cart = new ArrayList<>();
        cart.add(item);

        CheckoutService service = new CheckoutService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.validateCart(cart);
        });
    }

    @Test
    public void testStockDeductionAfterCheckout() {
        Part part = new Part("P001", "Item A", "Brand", "bodywork", 100.0, 10, LocalDate.now(), 5, "");
        CartItem item = new CartItem(part, 4);

        List<CartItem> cart = new ArrayList<>();
        cart.add(item);

        CheckoutService service = new CheckoutService();
        Transaction transaction = service.checkout(cart);

        assertEquals(6, part.getQuantity());
        assertNotNull(transaction);
    }
}