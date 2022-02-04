/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Product;
import java.util.List;

/**
 *
 * @author manpreet
 */
public class Inventory {
    List<Product> getAllProducts () throws InventoryPersistenceException;
    Product addProduct(Product product) throws InventoryPersistenceException;
    Product getProduct(String position) throws InventoryPersistenceException;
    Product setProductQuantity(String position, long quantity) throws InventoryPersistenceException;
    
}
