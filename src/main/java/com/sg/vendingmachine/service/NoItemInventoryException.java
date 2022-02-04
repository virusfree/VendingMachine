/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

/**
 *
 * @author manpreet
 */
public class NoItemInventoryException extends Exception{
    public NoItemInventoryException(String message) {
        super(message);
    }
    public NoItemInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
