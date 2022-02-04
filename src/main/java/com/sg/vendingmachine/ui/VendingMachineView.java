/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author manpreet
 */
public class VendingMachineView {
    
private UserIO io;
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    public void printMenuItems(List<Product> products) {
        
        String DELIMITER = "  ";
        String itemInfo;
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                itemInfo = product.getPosition() + DELIMITER;
                itemInfo += product.getTitle() + DELIMITER;
                itemInfo += "$" + product.getPrice() + DELIMITER;
                this.io.print(itemInfo);
            } 
        }
    }
    
    public String getUserSelection(List<Product> products) {
        String userChoice;
        
        List validChoices = new ArrayList<>();
        
        for (int i = 0; i < products.size(); i++) {
            validChoices.add(products.get(i).getPosition());
        }
        this.io.print("");
        userChoice = this.io.readString("Choose item by position (e.g. A1)");
        
        while (! validChoices.contains(userChoice)) {
            this.io.print("");
            userChoice = this.io.readString("Enter a valid choice");
        }
        
        return userChoice;
    }
    
    public Boolean userAgrees(String action) {
       
        String userResponse;
        Boolean willSelect = false;
        do {
            this.io.print("");
            userResponse= this.io.readString("Enter 'y' to " + action + " or 'n' to exit:");
        } while (! (userResponse.equals("y") ||  userResponse.equals("n")));
   
        if (userResponse.equals("y")) {
            willSelect = true;
        } 
        return willSelect;

    }
    
    public Change getUserDeposit(){
        
        BigDecimal userAmount;
        this.io.print("");
        userAmount = this.io.readBigDecimal("Deposit funds to continue (e.g. 1.50): ");
        Change amount = new Change(userAmount);
        return amount;
    }
    
    public void displayChangeReturned(Map<String, BigDecimal> coinsToCount) {
        this.io.print("");
        this.io.print("Returning change:");
        this.io.print("-----------------");
        this.io.print("quarter count = " + coinsToCount.get("QUARTER"));
        this.io.print("dime count = " + coinsToCount.get("DIME"));
        this.io.print("nickel count = " + coinsToCount.get("NICKEL"));
        this.io.print("penny count = " + coinsToCount.get("PENNY"));
    }
    
    public void displayMessage(String msg) {
        this.io.print("");
        this.io.print(msg);
    }
    
    public void displayDispenseSuccess(Product product) {
        String productTitle = product.getTitle();
        this.displayMessage("Dispensing " + productTitle);
    }
}