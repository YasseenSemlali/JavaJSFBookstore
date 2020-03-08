
package com.gb4w20.gb4w20.jsf.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Holds useful methods for contents of various forms
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class FormValues implements Serializable{
    
    //Lists for selections
    private List<String> availableTitles; 
    private List<String> provinceSelections;
    
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
     * Getter for the available titles. 
     * If the value is null it creates the list. 
     * @return list of available titles
     */
    public List<String> getAvailableTitles(){
        if(this.availableTitles == null){
            generateAvailableTitles();
        }
        return this.availableTitles; 
    }
    
    /**
     * Used as a helper method to set the list based on the local 
     * @author Jeffrey Boisvert
     */
    private void generateAvailableTitles() {
        //TODO Not sure how to make this i18n compliant. Also what titles does Ken want? 
        this.availableTitles = new ArrayList<>(
                Arrays.asList(
                        this.bundle.getString("mr_title"), 
                        this.bundle.getString("mrs_title"), 
                        this.bundle.getString("ms_title"), 
                        this.bundle.getString("dr_title")
                ) 
        );   
       Collections.sort(this.availableTitles);
    }
    
    /**
     * Used to get the provinceSelections value set
     * @return List of the set provinceSelections.
     * @author Jeffrey Boisvert
     */
    public List<String> getProvinceSelections() {
        if(this.provinceSelections == null){
            generateProvinceList();
        }
        return provinceSelections;
    }
    
    /**
     * Used as a helper method to set the list of all supported provinces
     * @author Jeffrey Boisvert
     */
    private void generateProvinceList() {
        this.provinceSelections = new ArrayList<>(
                Arrays.asList(
                        this.bundle.getString("ab_label"), 
                        this.bundle.getString("bc_label"), 
                        this.bundle.getString("ma_label"), 
                        this.bundle.getString("nb_label"), 
                        this.bundle.getString("nl_label"), 
                        this.bundle.getString("nt_label"), 
                        this.bundle.getString("ns_label"), 
                        this.bundle.getString("nu_label"), 
                        this.bundle.getString("on_label"), 
                        this.bundle.getString("pe_label"),
                        this.bundle.getString("qc_label"),
                        this.bundle.getString("sk_label"),
                        this.bundle.getString("yt_label")
                ) 
        ); 
        
        Collections.sort(this.provinceSelections);
    }
    
    
}
