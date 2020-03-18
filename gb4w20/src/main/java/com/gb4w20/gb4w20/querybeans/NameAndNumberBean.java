
package com.gb4w20.gb4w20.querybeans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This is simply used to be able to do group by name, total amount, and count for reports. 
 * 
 * @author Jeffrey Boisvert
 */
public class NameAndNumberBean implements Serializable {
    
    private String name;
    private BigDecimal amount; 
    
    public NameAndNumberBean(){
    }
        
    public NameAndNumberBean(String name, BigDecimal amount){
        this.name = name; 
        this.amount = amount; 
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String value) {
        name = value;
    }
    
}
