
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
    
    private BigDecimal count; 

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
    
}
