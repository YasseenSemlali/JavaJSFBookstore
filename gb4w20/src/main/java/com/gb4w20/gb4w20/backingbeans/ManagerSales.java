package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jsf.validation.JSFFormMessageValidator;
import java.io.IOException;
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
    
    private final static Logger LOG = LoggerFactory.getLogger(ManagerSales.class);
    
    @Inject
    private BooksJpaController booksController;
    
    @Inject
    private JSFFormMessageValidator validator;
    
    private BigDecimal[] newSalePrice;
    
    private Integer errorIndex;
    
    /**
     * Method to initialize variables
     */
    @PostConstruct
    private void init() {
        int size = booksController.getBooksCount();
        newSalePrice = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            newSalePrice[i] = new BigDecimal(0);
        }
    }
    
    /**
     * Method to alter the sale price of a book
     * based on input number bound to field
     * 
     * @param isbn 
     * @param index 
     * @throws java.io.IOException 
     */
    public void editSalePrice(Long isbn, int index) throws IOException {
        
        BigDecimal inputSalePrice = newSalePrice[index];
        
        try {
            Books book = booksController.findBooks(isbn);
            
            if (validateSalePrice(book.getListPrice(), inputSalePrice)) {
                book.setSalePrice(inputSalePrice);
                booksController.edit(book);
                //Reset input field
                this.newSalePrice[index] = new BigDecimal(0);
            } else {
                errorIndex = index;
                validator.createFacesMessageFromKey("bigger_than_list");
            }
        } catch (Exception ex) {
            LOG.info(ex.toString());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
        }
    }
    
    /**
     * Used to validate that the sale price is smaller than the list price
     * 
     * @author Jean Robatto
     */
    private boolean validateSalePrice(BigDecimal listPrice, BigDecimal newSalePrice) {
        return listPrice.compareTo(newSalePrice) == 1; //listPrice > newSalePrice
    }
    

    public BigDecimal[] getNewSalePrice() {
        return newSalePrice;
    }

    public Integer getErrorIndex() {
        return errorIndex;
    }

    public void setNewSalePrice(BigDecimal[] newSalePrice) {
        this.newSalePrice = newSalePrice;
    }

    public void setErrorIndex(Integer errorIndex) {
        this.errorIndex = errorIndex;
    }
    
    

}
