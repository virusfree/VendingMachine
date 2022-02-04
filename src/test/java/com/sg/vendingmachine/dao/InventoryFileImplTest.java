/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Product;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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
public class InventoryFileImplTest {
    
    public Inventory testDao;
    
    @BeforeEach
    public void setUp()throws Exception {
        String testInventory = "test_inventory.txt";
        new FileWriter(testInventory);
        this.testDao = new InventoryFileImpl(testInventory); 
    }
    
    @Test
    public void testAddGetProduct() throws Exception {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product testAddProduct = new Product(position, title, price, quantity);
        this.testDao.addProduct(testAddProduct);
        Product testGetProduct = this.testDao.getProduct(position);

        assertEquals(testAddProduct.getPosition(), testGetProduct.getPosition(), "Check product position");
        assertEquals(testAddProduct.getTitle(), testGetProduct.getTitle(), "Check product title");
        assertEquals(testAddProduct.getPrice(), testGetProduct.getPrice(), "Check product price");
        assertEquals(testAddProduct.getQuantity(), testGetProduct.getQuantity(), "Check product quantity");
    }

    @Test
    public void testAddGetAllProducts() throws Exception {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product kitKat = new Product(position, title, price, quantity);
        position = "C3";
        title = "Doritos Ranch";
        price = new BigDecimal("1.50").setScale(2, RoundingMode.HALF_UP);
        quantity = 6;
        Product doritosRanch = new Product(position, title, price, quantity);
        testDao.addProduct(kitKat);
        testDao.addProduct(doritosRanch);
        List<Product> allProducts = testDao.getAllProducts();

        assertNotNull(allProducts, "The list of products must not be null");
        assertEquals(2, allProducts.size(), "List of products should have 2 products only.");

        // add more specific tests
        assertTrue(testDao.getAllProducts().contains(kitKat), "The list of products should contain KitKat");
        assertTrue(testDao.getAllProducts().contains(doritosRanch), "The list of products should contain Doritos Ranch");

    }

    @Test
    public void testAddProductEditProductQuantity() throws Exception {
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product kitKat = new Product(position, title, price, quantity);
        testDao.addProduct(kitKat);

        testDao.setProductQuantity(position, quantity - 1);
        Product retreivedKitKat = testDao.getProduct(position);

        assertFalse(testDao.getAllProducts().contains(kitKat), "KitKat with old quantity should not be in product list");
        assertTrue(testDao.getAllProducts().contains(retreivedKitKat), "KitKat with new quantity should be in product list");

        assertEquals(retreivedKitKat.getQuantity(), quantity - 1, "KitKat quantity in product list should reflect updated quantity");

    }

}
