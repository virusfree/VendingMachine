/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author manpreet
 */
public class InventoryAuditFileImpl implements InventoryAudit{
public static final String AUDIT_FILE = "audit.txt";
    private PrintWriter auditOutput;
    private LocalDateTime timestamp;
    
    @Override
    public void writeAuditEntry(String entry) throws InventoryPersistenceException{
        try {
            this.auditOutput = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new InventoryPersistenceException("Could not persist audit entry information");
        }
        LocalDateTime timestamp = LocalDateTime.now();
        this.auditOutput.println(timestamp + ": " + entry);
        this.auditOutput.flush();
        this.auditOutput.close();
    }
}
