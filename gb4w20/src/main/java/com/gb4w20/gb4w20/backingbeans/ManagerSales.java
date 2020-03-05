package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to edit book sales.
 *
 * @author Jean Robatto
 */
@Named
@RequestScoped
public class ManagerSales implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);
    
    @Inject
    private BooksJpaController booksController;
    
    private BigDecimal[] newSalePrice;
    
    /**
     * Method to initialize variables
     */
    @PostConstruct
    private void init() {
        int size = booksController.getBooksCount();
        this.newSalePrice = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            this.newSalePrice[i] = new BigDecimal(0);
        }
    }
    
    /**
     * Method to alter the sale price of a book
     * based on input number bound to field
     * 
     * @param isbn 
     */
    public void editSalePrice(Long isbn, int index) {
        try {
            Books book = booksController.findBooks(isbn);
            book.setSalePrice(newSalePrice[index]);
            booksController.edit(book);
            FacesContext.getCurrentInstance().getExternalContext().redirect("manager-sales.xhtml");
        } catch (Exception ex) {
            LOG.info(ex.toString());
        }
    }

    public BigDecimal[] getNewSalePrice() {
        return newSalePrice;
    }

    public void setNewSalePrice(BigDecimal[] newSalePrice) {
        this.newSalePrice = newSalePrice;
    }
    
    

    

    

}
