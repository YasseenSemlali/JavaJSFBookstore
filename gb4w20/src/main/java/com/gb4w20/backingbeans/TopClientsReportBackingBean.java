
package com.gb4w20.backingbeans;

import com.gb4w20.jpa.UsersJpaController;
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
 * This is simply used as a backing bean for the top clients sales report. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class TopClientsReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(TopClientsReportBackingBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    @Inject
    private JSFFormMessageValidator validator;
    
    private java.util.Date startDate;
    private java.util.Date endDate; 
    
    private List<NameTotalAndCountBean> clientsSales; 
    
    /**
     * This will set the properties of the bean of for the clients sales based
     * on the values set in startDate and endDate. 
     * @author Jeffrey Boisvert
     */
    public void runReport(){
        
         if(validator.validateDatesAreValid(startDate, endDate)){

            try {
                
                setClientSales();
                validator.validateCollectionIsNotEmpty(clientsSales, "report_no_result");
                
            }
            catch (Exception ex){
                LOG.debug("Error running report ", ex);
                validator.createFacesMessageFromKey("error_running_report");
            }
        
        }

    }
    
    
    /**
     * Helper method to set the list of clients and sales in a given date range.
     * @author Jeffrey Boisvert
     */
    private void setClientSales() {

        this.clientsSales = this.usersJpaController.findTopUsersBySales(sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
        
    }
    
    /**
     * Used to retrieve the list of clients and their sales.
     * @return the list of clients and sales. 
     * @author Jeffrey Boisvert
     */
    public List<NameTotalAndCountBean> getClientsSales() {
        return clientsSales;
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
