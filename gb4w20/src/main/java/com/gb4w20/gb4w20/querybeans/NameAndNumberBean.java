package com.gb4w20.gb4w20.querybeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * This is simply used to be able to do group by name, total amount, and count
 * for reports.
 *
 * @author Jeffrey Boisvert
 */
public class NameAndNumberBean implements Serializable {

    private String name;
    private BigDecimal amount;

    public NameAndNumberBean() {
    }

    public NameAndNumberBean(String name, BigDecimal amount) {
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

    @Override
    public String toString() {
        return "{" + name + ", " + amount + "}";
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
        final NameAndNumberBean other = (NameAndNumberBean) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        return true;
    }
    
    

}
