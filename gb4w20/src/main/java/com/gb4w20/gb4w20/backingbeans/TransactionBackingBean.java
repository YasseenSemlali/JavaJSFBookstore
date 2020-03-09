/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 * <h1>Transaction Backing Bean</h1>
 * <p>
 * This is the backing bean for the transaction information
 * where a logged in user needs to input the credit card
 * and other information to be able to finalize the purchase of 
 * the cart
 * </p>
 *
 * @author Jasmar Badion
 */
@Named("transaction")
@RequestScoped
public class TransactionBackingBean implements Serializable{
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(TransactionBackingBean.class);
    
    //Inputs
    private BigDecimal amount;
    private CreditCardBackingBean card = new CreditCardBackingBean("");
    private Date date = new Date();
    private String billAddress;
    private String cardSecurityCode;
    
    /**
     * Getter for amount
     * @return 
     */
    public BigDecimal getAmount(){
        return this.amount;
    }
    
    /**
     * Setter for amount
     * @param amount 
     */
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }
    
    /**
     * Getter for card
     * @return 
     */
    public CreditCardBackingBean getCard(){
        return this.card;
    }
    
    /**
     * Setter for card
     * @param card 
     */
    public void setCard(CreditCardBackingBean card){
        this.card = card;
    }
    
    /**
     * Getter for date
     * @return 
     */
    public Date getDate(){
        return this.date;
    }
    
    /**
     * Setter for date
     * @param date 
     */
    public void setDate(Date date){
        this.date = date;
    }
    
    /**
     * Getter for billAddress
     * @return 
     */
    public String getBillAddress(){
        return this.billAddress;
    }
    
    /**
     * Setter for billAddress
     * @param billAddress 
     */
    public void setBillAddress(String billAddress){
        this.billAddress = billAddress;
    }
    
    /**
     * Getter for cardSecurityCode
     * @return 
     */
    public String getCardSecurityCode(){
        return this.cardSecurityCode;
    }
    
    /**
     * Setter for cardSecurityCode
     * @param cardSecurityCode 
     */
    public void setCardSecurityCode(String cardSecurityCode){
        this.cardSecurityCode = cardSecurityCode;
    }
}
