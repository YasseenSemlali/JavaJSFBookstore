
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.OrdersJpaController;
import com.gb4w20.gb4w20.jsf.validation.JSFFormMessageValidator;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the zero sales report. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class ZeroSalesReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ZeroSalesReportBackingBean.class);

    @Inject
    private BooksJpaController booksJpaController;
    
    @Inject
    private OrdersJpaController ordersJpaController;
    
    @Inject
    private JSFFormMessageValidator validator;
    
    private java.util.Date startDate;
    private java.util.Date endDate; 
    
    private List<Books> bookSales; 
    
    /**
     * This will set the properties of the bean of for the book sales based
     * on the values set in startDate and endDate. 
     */
    public void runReport(){
        
        if(validator.validateDatesAreValid(startDate, endDate)){

            try {

                setBookSales();
                validator.validateCollectionIsNotEmpty(this.bookSales, "report_no_result");

            }
            catch (Exception ex){
                try{
                    //Means there were no sales so it is all books 
                    List list = this.ordersJpaController.getPurchasedBooks(sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
                    if(list.isEmpty()){
                        this.bookSales = this.booksJpaController.findBooksEntities();
                    }
                }
                catch(Exception e){
                    LOG.debug("Error running report " + ex.getMessage(), ex);
                    validator.createFacesMessageFromKey("error_running_report");
                }
  
            }
        
        }
        
    }
    
    
    /**
     * Helper method to set the list of clients and sales in a given date range.
     */
    private void setBookSales() {
        this.bookSales = this.booksJpaController.findBooksThatWereNeverSold(sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
    }
    
    /**
     * Used to retrieve the list of clients and their sales.
     * @return the list of clients and sales. 
     */
    public List<Books> getBookSales() {
        return bookSales;
    }
    
    /**
     * Used to get the set start date. 
     * @return 
     */
    public java.util.Date getStartDate() {
        return startDate;
    }

    /**
     * Used to set the start date of the report. 
     * @param startDate 
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Used to get the set end date. 
     * @return 
     */
    public java.util.Date getEndDate() {
        return endDate;
    }

    /**
     * Used to set the wanted end date of the report. 
     * @param endDate 
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * This solution is to help with the fact that primefaces cannot map to sql date correctly. 
     * Credit to Matt Handy in post: https://stackoverflow.com/questions/10244164/primefaces-calendar-component-date-conversions 
     * for the idea to the solution. 
     * 
     * @param date Given
     * @return sql version of the date. 
     */
    private java.sql.Date sqlDate(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }
    
}
