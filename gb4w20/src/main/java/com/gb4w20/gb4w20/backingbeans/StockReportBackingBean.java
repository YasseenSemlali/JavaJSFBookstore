
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the stocks report. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class StockReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(StockReportBackingBean.class);

    @Inject
    private BooksJpaController booksJpaController;
    
    private List<Books> stock; 
    
    /**
     * This will set the properties of the bean of for the stock
     */
    public void runReport(){
        setStock();
    }
    
    
    /**
     * Helper method to set the list of clients and sales in a given date range.
     */
    private void setStock() {
        this.stock = this.booksJpaController.getActiveBooks(-1);
    }
    
    /**
     * Used to retrieve the list of books in stock
     * @return the list of books in stock
     */
    public List<Books> getStock() {
        return stock;
    }
    
}
