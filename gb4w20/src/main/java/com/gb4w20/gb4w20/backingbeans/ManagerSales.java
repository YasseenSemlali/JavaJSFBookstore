package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jsf.validation.JSFFormMessageValidator;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
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
     *
     * @author Jean Robatto
     */
    @PostConstruct
    private void init() {
        LOG.debug("Initializing Manager Sales variables");
        int size = booksController.getBooksCount();
        newSalePrice = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            newSalePrice[i] = new BigDecimal(0);
        }
    }

    /**
     * Method to alter the sale price of a book based on input number bound to
     * field
     *
     * Redirects on failure instead of returning a string because it is an ajax
     * call
     *
     * @param isbn
     * @param index
     * @throws java.io.IOException
     * @author Jean Robatto
     */
    public void editSalePrice(Long isbn, int index) throws IOException {
        LOG.debug("Editing sale price of book with ISBN: " + Long.toString(isbn));

        BigDecimal inputSalePrice = newSalePrice[index];

        try {
            Books book = booksController.findBooks(isbn);

            if (validateSalePrice(book.getListPrice(), inputSalePrice, book.getWholesalePrice())) {
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
     * Used to validate that the sale price.
     *
     * @author Jean Robatto
     */
    private boolean validateSalePrice(BigDecimal listPrice, BigDecimal newSalePrice, BigDecimal wholesalePrice) {
        //listPrice > newSalePrice && newSalePrice >= 0 && listPrice - newSalePrice > wholesalePrice
        return listPrice.compareTo(newSalePrice) == 1
                && (newSalePrice.compareTo(new BigDecimal(0)) == 1 || newSalePrice.compareTo(new BigDecimal(0)) == 0)
                && listPrice.subtract(newSalePrice).compareTo(wholesalePrice) == 1;
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
