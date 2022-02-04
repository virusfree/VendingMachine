/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author manpreet
 */
public class Change {
 private BigDecimal value;
    public Change (BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }
    
    public Map<String, BigDecimal> getChange() {
        BigDecimal remaining;
        Map<String, BigDecimal> coinsToCount = new HashMap<>();
        remaining = this.value;
        
        BigDecimal quarter = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal dime = new BigDecimal("0.10").setScale(2, RoundingMode.HALF_UP);
        BigDecimal nickel = new BigDecimal("0.05").setScale(2, RoundingMode.HALF_UP);
        BigDecimal penny = new BigDecimal("0.01").setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal[] divisionAndRemainder = remaining.divideAndRemainder(quarter);
        coinsToCount.put(Coin.QUARTER.toString(), divisionAndRemainder[0]);
        remaining = divisionAndRemainder[1];
        divisionAndRemainder = remaining.divideAndRemainder(dime);
        coinsToCount.put(Coin.DIME.toString(), divisionAndRemainder[0]);
        remaining = divisionAndRemainder[1];
        divisionAndRemainder = remaining.divideAndRemainder(nickel);
        coinsToCount.put(Coin.NICKEL.toString(), divisionAndRemainder[0]);
        remaining = divisionAndRemainder[1];
        divisionAndRemainder = remaining.divideAndRemainder(penny);
        coinsToCount.put(Coin.PENNY.toString(), divisionAndRemainder[0]);
        this.value = divisionAndRemainder[1];
        return coinsToCount;
    }
    
    public BigDecimal getValue() {
        return this.value;
    }
    
}