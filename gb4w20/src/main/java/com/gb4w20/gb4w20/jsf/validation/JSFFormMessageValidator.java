
package com.gb4w20.gb4w20.jsf.validation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Holds useful methods to send i18n FaceMessages based on validation method
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class JSFFormMessageValidator implements Serializable{
    
    //Bundle for i18n
    private ResourceBundle bundle; 
    
    /**
     * Mainly used to set the bundle.
     * @author Jeffrey Boisvert
     */
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        this.bundle = context.getApplication().getResourceBundle(context, "msgs");
    }
    
    /**
     * Used to validate if the start date and end date combo is valid
     * @param startDate given
     * @param endDate given
     * @return true if dates were valid and false otherwise
     * @author Jeffrey Boisvert
     */
    public boolean validateDatesAreValid(Date startDate, Date endDate){
        
        if(startDate == null || endDate == null){
            FacesMessage message = new FacesMessage(this.bundle.getString("missing_dates_error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        }
        
        if(startDate.after(endDate)){
            FacesMessage message = new FacesMessage(this.bundle.getString("start_date_after_end_date_error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        }
        
        return true;
        
    }
    
    /**
     * Used to validate if the given collection is empty or not. 
     * @param collection given to check 
     * @param errorMessagePropertyKey to get a message from the message bundle to build a FaceMessage from
     * @return true if collection is not empty and false if it is empty 
     * @author Jeffrey Boisvert
     */
    public boolean validateCollectionIsNotEmpty(Collection collection, String errorMessagePropertyKey){
        
        if(collection.isEmpty()){
                    FacesMessage message = new FacesMessage(this.bundle.getString(errorMessagePropertyKey));
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return false;
                }
                
         return true; 
        
    }
    
    /**
     * Used to create a i18n Faces Message based on the given message property key
     * @param messagePropertyKey 
     */
    public void createFacesMessageFromKey(String messagePropertyKey){
        FacesMessage message = new FacesMessage(this.bundle.getString(messagePropertyKey));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    
}
