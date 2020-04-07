package com.gb4w20.gb4w20.backingbeans;

import java.io.Serializable;
import org.slf4j.LoggerFactory;

/**
 * <h1>Credit Card Backing Bean</h1>
 * <p>
 * This is the backing bean for the credit card since
 * it needs to be converted and validated
 * </p>
 *
 * @author Jasmar Badion
 */
public class CreditCardBackingBean implements Serializable{
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(CreditCardBackingBean.class);
    
    private final String number;
    
    //constructor
    public CreditCardBackingBean(String number){
        this.number = number;
    }
       
    @Override
    public String toString(){
        return this.number;
    }
}
