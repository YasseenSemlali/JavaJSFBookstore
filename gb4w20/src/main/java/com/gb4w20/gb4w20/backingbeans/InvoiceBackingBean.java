package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * <h1>Invoice Backing Bean</h1>
 * <p>
 * This is the backing bean for invoice so that it stores some of the
 * information displayed in the invoice
 * </p>
 *
 * @author Jasmar Badion
 */
@Named
@SessionScoped
public class InvoiceBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(InvoiceBackingBean.class);

    private List<Books> booksincart = new ArrayList<Books>();
    ;
    private BigDecimal totalincart;
    private CreditCardBackingBean credcard;

    /**
     * Getter for books in cart
     *
     * @return
     */
    public List<Books> getBooksincart() {
        return this.booksincart;
    }

    /**
     * Setter for books in cart
     *
     * @param booksincart
     */
    public void setBooksincart(List<Books> booksincart) {
        this.booksincart = booksincart;
    }

    /**
     * Basically putting all the HashSet books from the cart to the books in
     * cart for the invoice
     *
     * @param booksincart
     */
    public void convertFromHashToList(HashSet<Books> booksincart) {
        this.booksincart = new ArrayList<>(booksincart);
    }

    /**
     * Getter for total in cart
     *
     * @return
     */
    public BigDecimal getTotalincart() {
        return this.totalincart;
    }

    /**
     * Setter for total in cart
     *
     * @param totalincart
     */
    public void setTotalincart(BigDecimal totalincart) {
        this.totalincart = totalincart;
    }

    /**
     * Getter for card
     *
     * @return
     */
    public CreditCardBackingBean getCredcard() {
        return this.credcard;
    }

    /**
     * Setter for card
     *
     * @param credcard
     */
    public void setCredcard(CreditCardBackingBean credcard) {
        this.credcard = credcard;
    }

    /**
     * Displays the credit card number in invoice as masked
     *
     * @return
     */
    public String hideCreditCardNum() {
        int index = 0;
        String mask = "##xx-xxxx-xxxx-xx##"; //format of how it will be displayed masked
        StringBuilder maskedNumber = new StringBuilder();
        LOG.debug(this.credcard.toString());
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            switch (c) {
                case '#':
                    maskedNumber.append(this.credcard.toString().charAt(index));
                    index++;
                    break;
                case 'x':
                    maskedNumber.append(c);
                    index++;
                    break;
                default:
                    maskedNumber.append(c);
                    break;
            }
        }
        return maskedNumber.toString();
    }
}
