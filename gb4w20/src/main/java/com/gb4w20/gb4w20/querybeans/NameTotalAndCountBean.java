
package com.gb4w20.gb4w20.querybeans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Used as a bean to hold a String name, total, and count. 
 * Mainly used for queries done with CriteriaBuilder 
 * 
 * @author Jeffrey Boisvert
 */
public class NameTotalAndCountBean extends NameAndNumberBean implements Serializable {
    
    private Long count;
    
    public NameTotalAndCountBean(String name, BigDecimal amount, Long count){
        super(name, amount);
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
    
}
