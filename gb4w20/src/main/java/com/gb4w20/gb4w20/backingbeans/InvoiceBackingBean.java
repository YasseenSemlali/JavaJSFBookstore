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
 * This is the backing bean for invoice
 * so that it stores some of the information
 * displayed in the invoice
 * </p>
 * 
 * @author Jasmar Badion
 */
@Named
@SessionScoped
public class InvoiceBackingBean implements Serializable{
    
    private final static Logger LOG = LoggerFactory.getLogger(InvoiceBackingBean.class);
    
    private List<Books> booksincart = new ArrayList<Books>();;
    private BigDecimal totalincart;
    
    /**
     * Getter for books in cart
     * @return 
     */
    public List<Books> getBooksincart(){
        return this.booksincart;
    }
    
    /**
     * Setter for books in cart
     * @param booksincart 
     */
    public void setBooksincart(List<Books> booksincart){
        this.booksincart = booksincart;
    }
    
    /**
     * Basically putting all the HashSet books from the cart
     * to the books in cart for the invoice 
     * @param booksincart 
     */
    public void convertFromHashToList(HashSet<Books> booksincart){
        for(Books bookincart : booksincart){
            this.booksincart.add(bookincart);
            LOG.info("Book purchased from the cart is " + bookincart.getTitle());
        }
    }
    
    /**
     * Getter for total in cart
     * @return 
     */
    public BigDecimal getTotalincart(){
        return this.totalincart;
    }
    
    /**
     * Setter for total in cart
     * @param totalincart 
     */
    public void setTotalincart(BigDecimal totalincart){
        this.totalincart = totalincart;
    }
}
