/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.Inventory;
import com.sg.vendingmachine.dao.InventoryAuditFileImpl;
import com.sg.vendingmachine.dao.InventoryFileImpl;
import com.sg.vendingmachine.dao.InventoryPersistenceException;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author manpreet
 */
public class App {
    public static void main(String[] args) throws InventoryPersistenceException, NoItemInventoryException, InsufficientFundsException {
        
        Inventory dao = new InventoryFileImpl();
        InventoryAuditFileImpl audit = new InventoryAuditFileImpl();
        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao, audit);
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineController controller = new VendingMachineController(service, view);
        controller.run();
        
    }
}
