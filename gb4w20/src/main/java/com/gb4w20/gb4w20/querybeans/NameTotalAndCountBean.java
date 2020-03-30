
package com.gb4w20.gb4w20.querybeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.count) + Objects.hashCode(super.getName()) + Objects.hashCode(super.getAmount());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NameTotalAndCountBean other = (NameTotalAndCountBean) obj;
        if (!(this.count.longValue() == other.getCount().longValue()) || 
            !(this.getName().equals(other.getName())) ||
            !(this.getAmount().doubleValue() == other.getAmount().doubleValue())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NameTotalAndCountBean{ name=" + super.getName() + " amount=" + super.getAmount()+ " count=" + count + '}';
    }
    
    
    
}
