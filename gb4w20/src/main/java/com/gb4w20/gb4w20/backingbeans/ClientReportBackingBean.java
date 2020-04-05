
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import com.gb4w20.gb4w20.jsf.validation.JSFFormMessageValidator;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the clients report. 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class ClientReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ClientReportBackingBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    @Inject
    private JSFFormMessageValidator validator;
    
    private Long userId; 
    
    private java.util.Date startDate;
    private java.util.Date endDate; 
    
    private List<Users> users; 
    
    private Double totalSales; 
    
    private List<NameAndNumberBean> purchasedProducts; 
    
    /**
     * Used to get a list of all the users in the database. 
     * @return a list of all the users in the database. 
     */
    public List<Users> getUsers() {
        if (users == null){
             this.users = usersJpaController.findUsersEntities();
        }
        return this.users;
    }
    
    /**
     * Used to get the user id to be used in the report. 
     * @return id of the user in question. 
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * Used to set the id of the user for the report. 
     * @param userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    /**
     * Used to run the report to the current user id set in the bean. 
     * This will set the properties of the bean of total sales and purchased items. 
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
                LOG.debug("Error running report " + ex.getMessage(), ex);
                validator.createFacesMessageFromKey("error_running_report");
            }
        
        }
    }
    
    /**
     * Helper method to set the total sales from the controller. 
     * @param id of the user in question. 
     */
    private void setTotalSales(){
        this.totalSales = this.usersJpaController.getUsersTotalSales(this.userId, sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
    }
    
    /**
     * Helper method to set the list of purchased products of the user in question. 
     * @param id of the user in question. 
     */
    private void setPurchasedProducts() {
        this.purchasedProducts = this.usersJpaController.getUserPurchasedBooks(this.userId, sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
    }
    
    /**
     * Used to retrieve the list of products the user has purchased. 
     * @return the list of products. 
     */
    public List<NameAndNumberBean> getPurchasedProducts() {
        return purchasedProducts;
    }
    
    /**
     * Used to get the total sales of the user. 
     * @return total sales of user. 
     */
    public Double getTotalSales() {
        return totalSales;
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
     * @author Jeffrey Boisvert
     */
    private java.sql.Date sqlDate(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }
    
}
