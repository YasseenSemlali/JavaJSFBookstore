
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the top sellers report. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class TopSellersReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(TopSellersReportBackingBean.class);

    @Inject
    private BooksJpaController booksJpaController;
    
    private java.util.Date startDate;
    
    private java.util.Date endDate; 
    
    private List<NameAndNumberBean> bookSales; 
    
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
     * This will set the properties of the bean of for the book sales based
     * on the values set in startDate and endDate. 
     */
    public void runReport(){
        setBookSales();
    }
    
    
    /**
     * Helper method to set the list of clients and sales in a given date range.
     */
    private void setBookSales() {
        
        if(this.startDate == null || this.endDate == null){
            FacesMessage message = new FacesMessage(this.bundle.getString("missing_dates_error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        
        if(this.startDate.after(this.endDate)){
            FacesMessage message = new FacesMessage(this.bundle.getString("start_date_after_end_date_error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        
        try{
            
            this.bookSales = this.booksJpaController.findTopSellers(sqlDate(this.startDate).toString(), sqlDate(this.endDate).toString());

            if(this.bookSales.isEmpty()){
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
     * Used to retrieve the list of clients and their sales.
     * @return the list of clients and sales. 
     */
    public List<NameAndNumberBean> getBookSales() {
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
