
package com.gb4w20.backingbeans;

import com.gb4w20.jpa.OrdersJpaController;
import com.gb4w20.jsf.validation.JSFFormMessageValidator;
import com.gb4w20.querybeans.NameTotalAndCountBean;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the total sales report. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class TotalSalesReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(TotalSalesReportBackingBean.class);

    @Inject
    private OrdersJpaController ordersJpaController;
    
    @Inject
    private JSFFormMessageValidator validator;
    
    private java.util.Date startDate;
    private java.util.Date endDate; 
    
    private Double totalSales; 
    
    private List<NameTotalAndCountBean> purchasedProducts;
    
    /**
     * Used to run the report.
     * This will set the properties of the bean of total sales and purchased products. 
     * @author Jeffrey Boisvert
     */
    public void runReport(){
        
         if(validator.validateDatesAreValid(startDate, endDate)){

            try {

                setTotalSales();
                setPurchasedProducts();
                validator.validateCollectionIsNotEmpty(purchasedProducts, "report_no_result");
                
            }
            catch (Exception ex){
                LOG.debug("Error running report ", ex);
                validator.createFacesMessageFromKey("error_running_report");
            }
        
        }

    }
    
    /**
     * Helper method to set the total sales in a given date range. 
     * @author Jeffrey Boisvert
     */
    private void setTotalSales(){
        this.totalSales = this.ordersJpaController.getTotalSales(sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
    }
    
    /**
     * Helper method to set the list of purchased products of the publisher in question. 
     * @param id of the user in question. 
     * @author Jeffrey Boisvert
     */
    private void setPurchasedProducts() {
        this.purchasedProducts = this.ordersJpaController.getPurchasedBooks(sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
    }
    
    /**
     * Used to retrieve the list of products by the publisher that was purchased. 
     * @return the list of products. 
     * @author Jeffrey Boisvert
     */
    public List<NameTotalAndCountBean> getPurchasedProducts() {
        return purchasedProducts;
    }
    
    /**
     * Used to get the total sales of the publisher. 
     * @return total sales of user. 
     * @author Jeffrey Boisvert
     */
    public Double getTotalSales() {
        return totalSales;
    }
    
    /**
     * Used to get the set start date. 
     * @return 
     * @author Jeffrey Boisvert
     */
    public java.util.Date getStartDate() {
        return startDate;
    }

    /**
     * Used to set the start date of the report. 
     * @param startDate 
     * @author Jeffrey Boisvert
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Used to get the set end date. 
     * @return 
     * @author Jeffrey Boisvert
     */
    public java.util.Date getEndDate() {
        return endDate;
    }

    /**
     * Used to set the wanted end date of the report. 
     * @param endDate 
     * @author Jeffrey Boisvert
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
     * @author Jeffrey Boisvert
     */
    private java.sql.Date sqlDate(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }
    
}
