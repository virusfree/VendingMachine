/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.Inventory;
import com.sg.vendingmachine.dao.InventoryAudit;
import com.sg.vendingmachine.dao.InventoryPersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author manpreet
 */
public class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerImplTest() {
        Inventory dao = new InventoryStubImpl();
        InventoryAudit auditDao = new InventoryAuditStubImpl();
        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }
    
    @Test
    public void testValidPurchase() {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product product = new Product(position, title, price, quantity);
        Change deposit = new Change(price);

        try {
            service.addBalance(deposit);
            service.processPurchase(position);
        } catch (NoItemInventoryException | InsufficientFundsException | InventoryPersistenceException e) {
            fail("Purchase was valid, should not have thrown exception");
        }
        
        try {
            assertEquals(service.getProduct(position).getQuantity(), quantity - 1);
        } catch (InventoryPersistenceException e) {
            fail("Getting product quantity after purchase should not have thrown exception");
        }
    }
    
    @Test
    public void testInsufficientFunds() {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product product = new Product(position, title, price, quantity);
        BigDecimal insufficient = new BigDecimal("1.24").setScale(2, RoundingMode.HALF_UP);
        Change notEnough = new Change(insufficient);
        
        try {
            service.addBalance(notEnough);
            service.processPurchase(position);
        } catch (NoItemInventoryException | InventoryPersistenceException e) {
            fail("Threw incorrect exception");
        } catch (InsufficientFundsException e) {
            return;
        }
    }
    
    @Test
    public void testDepletedInventory() {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long noQuantity = 0;
        Change enoughMoney = new Change(price);
        
        try {
            Product retreivedProduct = service.getProduct(position);
            retreivedProduct.setQuantity(noQuantity);
            service.addBalance(enoughMoney);
            service.processPurchase(position);
        } catch (InsufficientFundsException | InventoryPersistenceException e) {
            fail("Incorrect exception type thrown");
        } catch (NoItemInventoryException e) {
            return;
        }
    }

    @Test
    public void testGetAllProducts() throws Exception {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product kitKat = new Product(position, title, price, quantity);
        assertEquals(1, service.getAllProducts().size(), "Should only have one item");
        assertTrue(service.getAllProducts().contains(kitKat), "The only item should be KitKat");
    }

    @Test
    public void testGetProduct() throws Exception {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product product = new Product(position, title, price, quantity);

        Product kitKat = service.getProduct("A1");
        assertNotNull(kitKat, "Getting A1 should not be null");
        assertEquals(product, kitKat, "Product stored in poisition A1 should be kitKat.");

        Product shouldBeNull = service.getProduct("F5");
        assertNull(shouldBeNull, "Getting F5 should be null.");
    }

}