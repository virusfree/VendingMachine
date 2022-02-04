/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

/**
 *
 * @author manpreet
 */
public class InventoryPersistenceException extends Exception{
    public InventoryPersistenceException(String message) {
        super(message);
    }
    public InventoryPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
