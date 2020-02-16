
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
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
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class ClientReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ClientReportBackingBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    private Long userId; 
    
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
     */
    public void runReport(){
        setTotalSales(this.userId);
        setPurchasedProducts(this.userId);
    }
    
    /**
     * Helper method to set the total sales from the controller. 
     * @param id of the user in question. 
     */
    private void setTotalSales(Long id){
        this.totalSales = this.usersJpaController.getUsersTotalSales(id, "2020-01-01", "2020-02-22");
    }
    
    /**
     * Helper method to set the list of purchased products of the user in question. 
     * @param id of the user in question. 
     */
    private void setPurchasedProducts(Long id) {
        this.purchasedProducts = this.usersJpaController.getUserPurchasedBooks(id, "2020-01-01", "2020-02-22");
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
    
}
