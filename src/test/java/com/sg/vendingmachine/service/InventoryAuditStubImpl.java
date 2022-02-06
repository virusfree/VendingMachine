/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.InventoryAudit;
import com.sg.vendingmachine.dao.InventoryPersistenceException;

/**
 *
 * @author manpreet
 */
public class InventoryAuditStubImpl implements InventoryAudit {

    public InventoryAuditStubImpl() {
    }
    public void writeAuditEntry(String entry) throws InventoryPersistenceException {
        // if only every method could be empty like this
    }
    
}
