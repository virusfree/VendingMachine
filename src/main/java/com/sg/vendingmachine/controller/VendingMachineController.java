/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.InventoryPersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Product;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.util.List;

/**
 *
 * @author manpreet
 */
public class VendingMachineController {
  private final VendingMachineServiceLayer service;
    private final VendingMachineView view;
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.view = view;
        this.service = service;
    }
    public void run() throws InsufficientFundsException, NoItemInventoryException, InventoryPersistenceException {
        List<Product> menuItems = this.service.getAllProducts();
        this.view.printMenuItems(menuItems);
        
        if (this.view.userAgrees("select an item")) {
            Change deposit = this.view.getUserDeposit();
            this.service.addBalance(deposit);
        } else {
            return;
        }
        String userSelection = this.view.getUserSelection(menuItems);
        Boolean notDoneYet = true;
        do {
            try {
                this.service.processPurchase(userSelection);
                this.view.displayDispenseSuccess(this.service.getProduct(userSelection));
                notDoneYet = false;
            } catch (NoItemInventoryException e) {
                this.view.displayMessage(e.getMessage());
                if (this.view.userAgrees("choose another item")) {
                    userSelection = this.view.getUserSelection(menuItems);
                } else {
                    notDoneYet = false;
                }
            } catch (InsufficientFundsException e) {
                this.view.displayMessage(e.getMessage());
                if (this.view.userAgrees("add more balance")) {
                    Change deposit = this.view.getUserDeposit();
                    this.service.addBalance(deposit);
                } else {
                    notDoneYet = false;
                }
            }
        } while (notDoneYet);
        
        Change userChange = this.service.returnChange();
        
        this.view.displayChangeReturned(userChange.getChange());
        
    }
    
    
}