
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the authors report. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class AuthorReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(AuthorReportBackingBean.class);

    @Inject
    private AuthorsJpaController authorsJpaController;
    
    private Long authorId; 
    
    private java.util.Date startDate;
    
    private java.util.Date endDate; 
    
    private List<Authors> authors; 
    
    private Double totalSales; 
    
    private List<NameAndNumberBean> purchasedProducts; 
    
    //Bundle for i18n
    private ResourceBundle bundle; 
    
    /**
     * Mainly used to set default values.
     * @author Jeffrey Boisvert
     */
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        this.bundle = context.getApplication().getResourceBundle(context, "msgs");
    }
    
    /**
     * Used to get a list of all the authors in the database. 
     * @return a list of all the users in the database. 
     */
    public List<Authors> getAuthors() {
        if (authors == null){
             this.authors = authorsJpaController.findAuthorsEntities();
        }
        return this.authors;
    }
    
    /**
     * Used to get the author id to be used in the report. 
     * @return id of the author in question. 
     */
    public Long getAuthorId() {
        return authorId;
    }
    
    /**
     * Used to set the id of the author for the report. 
     * @param authorId to set
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    
    /**
     * Used to run the report to the current author id set in the bean. 
     * This will set the properties of the bean of total sales and purchased items. 
     */
    public void runReport(){
        
        if(this.startDate == null || this.endDate == null){
            FacesMessage message = new FacesMessage(this.bundle.getString("missing_dates_error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        
        try{
            
            setTotalSales();
            setPurchasedProducts();
            
            if(this.purchasedProducts.isEmpty()){
                FacesMessage message = new FacesMessage(this.bundle.getString("report_no_result"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
        catch (Exception ex){
            LOG.debug("Error running report ", ex);
            FacesMessage message = new FacesMessage(this.bundle.getString("error_running_report"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }
    
    /**
     * Helper method to set the total sales from the controller. 
     * @param id of the user in question. 
     */
    private void setTotalSales(){
       this.totalSales = this.authorsJpaController.getAuthorsTotalSales(this.authorId, sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
    }
    
    /**
     * Helper method to set the list of purchased products of the author in question. 
     * @param id of the user in question. 
     */
    private void setPurchasedProducts() {
      this.purchasedProducts = this.authorsJpaController.getPurchasedBooksByAuthor(this.authorId, sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());
    }
    
    /**
     * Used to retrieve the list of products by the author that have been purchased. 
     * @return the list of products. 
     */
    public List<NameAndNumberBean> getPurchasedProducts() {
        return purchasedProducts;
    }
    
    /**
     * Used to get the total sales of the author. 
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
     */
    private java.sql.Date sqlDate(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }
    
}
