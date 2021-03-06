
package com.gb4w20.backingbeans;

import com.gb4w20.entities.Taxes;
import com.gb4w20.jsf.validation.JSFFormMessageValidator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 * <h1>Transaction Backing Bean</h1>
 * <p>
 * This is the backing bean for the transaction information where a logged in
 * user needs to input the credit card and other information to be able to
 * finalize the purchase of the cart
 * </p>
 *
 * @author Jasmar Badion
 */
@Named("transaction")
@SessionScoped
public class TransactionBackingBean implements Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(TransactionBackingBean.class);

    //Inputs
    private CreditCardBackingBean card = new CreditCardBackingBean("");
    private String ownername;
    private Date date = new Date();
    private String cardSecurityCode;

    @Inject
    private JSFFormMessageValidator validator;

    /**
     * Getter for card
     *
     * @return
     */
    public CreditCardBackingBean getCard() {
        return this.card;
    }

    /**
     * Setter for card
     *
     * @param card
     */
    public void setCard(CreditCardBackingBean card) {
        this.card = card;
    }
    
    /**
     * Getter for credit card owner name
     * @return 
     */
    public String getOwnername(){
        return this.ownername;
    }
    
    /**
     * Setter for credit card owner name
     * @param ownerName
     */
    public void setOwnername(String ownerName){
        this.ownername = ownerName;
    }

    /**
     * Getter for date
     *
     * @return
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Setter for date
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for cardSecurityCode
     *
     * @return
     */
    public String getCardSecurityCode() {
        if (this.cardSecurityCode == null) {
            return "";
        }
        return this.cardSecurityCode;
    }

    /**
     * Setter for cardSecurityCode
     *
     * @param cardSecurityCode
     */
    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    /**
     * Calculating the total amount along with the taxes and scaling it to two
     * decimals
     *
     * @param amount
     * @param taxes
     * @return
     */
    public BigDecimal calculateAmountWithTaxes(BigDecimal amount, Taxes taxes) {
        BigDecimal hst = new BigDecimal(0);
        BigDecimal gst = new BigDecimal(0);
        BigDecimal pst = new BigDecimal(0);

        if (taxes.getHSTpercentage() != null) {
            hst = amount.multiply(taxes.getHSTpercentage());
        }
        if (taxes.getGSTpercentage() != null) {
            gst = amount.multiply(taxes.getGSTpercentage());
        }
        if (taxes.getPSTpercentage() != null) {
            pst = amount.multiply(taxes.getPSTpercentage());
        }

        LOG.info("HST is " + hst);
        LOG.info("GST is " + gst);
        LOG.info("PST is " + pst);

        BigDecimal totals = amount.add(gst.add(hst.add(pst)));
        //rounding the amount to two decimal points
        return totals.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Scales amount to two decimals
     * @param amount
     * @return 
     */
    public BigDecimal scaleByTwoDecimals(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Get the current date to be displayed to invoice to know the date
     * purchased
     *
     * @return
     */
    public String showCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datenow = new Date();
        return formatter.format(datenow);
    }
    
    /**
     * Clears transaction input after paying
     */
    public void clearTransactioninfo(){
        this.card = null;
        this.ownername = null;
        this.date = null;
        this.cardSecurityCode = null;
    }

    /**
     * To validate credit card's owner name
     * 
     * @param fc
     * @param c
     * @param value 
     */
    public void validateCreditOwnerName(FacesContext fc, UIComponent c, Object value){
        this.validator.validateIsNotBlank((String) value);
    }
    
    /**
     * To validate card security code or CVV
     *
     * @param fc
     * @param c
     * @param value
     */
    public void validateCVV(FacesContext fc, UIComponent c, Object value) {
        this.validator.validateCVV((String) value);
    }

    /**
     * To validate credit card expiry date
     *
     * @param fc
     * @param c
     * @param value
     */
    public void validateExpiryDate(FacesContext fc, UIComponent c, Object value) {
        this.validator.validateExpiryDate((Date) value);
    }
}
