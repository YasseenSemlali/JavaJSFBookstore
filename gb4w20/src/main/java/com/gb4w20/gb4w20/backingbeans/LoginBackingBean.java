
package com.gb4w20.gb4w20.backingbeans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;

/**
 * Used as a backing bean for the login page. 
 * @author Jeffrey Boisvert
 */
public class LoginBackingBean {
    
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginBackingBean.class);
    private static final String CLIENT_PAGE = "index.xhtml";
    private static final String MANAGER_PAGE = "manager_inventory.xhtml"; 
    
    @Inject
    UserSessionBean session; 
    
    private String emailInput; 
    private String passwordInput; 
    
    /**
     * Used to get the email value inputted
     * @return String of the inputted email.
     * @author Jeffrey Boisvert
     */
    public String getEmailInput(){
        if (this.emailInput == null){
            return ""; 
        }
        return this.emailInput; 
    }
    
    /**
     * Used to set the email input to given value
     * @param emailInput given
     * @author Jeffrey Boisvert
     */
    public void setEmailInput(String emailInput){
        this.emailInput = emailInput; 
    }
    
    /**
     * Used to retrieve the inputted password
     * @return String of the inputted password
     * @author Jeffrey Boisvert
     */
    public String getPasswordInput(){
        if (this.emailInput == null){
            return ""; 
        }
        return this.emailInput; 
    }
    
    /**
     * Used to set the passwordInput to the value given
     * @param passwordInput given
     * @author Jeffrey Boisvert 
     */
    public void setPasswordInput(String passwordInput){
        this.passwordInput = passwordInput; 
    }
    
    /**
     * Used to login based on the values given. 
     * If the login is successful there is two possibilities:
     * 1) If a manager redirected to to the manager page
     * 2) If a normal client redirect to the index.xhtml page
     * If login failed prompted with error
     * @author Jeffrey Boisvert
     */
    public void login(){
        
        //If not valid notify user
        if(areInputsNotValid()){
            //TODO notify user
            return;
         }
        
        if(this.session.loginUser(this.emailInput, this.passwordInput)){
            
            String uriToRedirectTo = this.session.isLoggedInManager() ? MANAGER_PAGE : CLIENT_PAGE; 
            try {
                FacesContext.getCurrentInstance().getExternalContext().dispatch(uriToRedirectTo);
            } catch (IOException ex) {
                LOG.error("Redirect not successful for " + uriToRedirectTo);
            }
            
        }
        else {
            //Login not successful
            //TODO notify user
        }
        
    }
    
    /**
     * Used as a help method to check if the inputs are valid
     * @return true of valid, false otherwise
     * @author Jeffrey Boisvert
     */
    private boolean areInputsNotValid() {
        return isEmailValid() && isPasswordValid(); 
    }
    
    /**
     * Used to validate if the email is valid or not. 
     * @return true if valid
     * @author Jeffrey Boisvert
     */
    private boolean isEmailValid(){
       return !(this.emailInput == null || 
              this.emailInput.isBlank() || 
              this.emailInput.isEmpty()); 
    }
    
    /**
     * Used to valid if the password is valid or not
     * @return true if valid
     * @author Jeffrey Boisvert
     */
    private boolean isPasswordValid(){
       return !(this.passwordInput == null || 
              this.passwordInput.isBlank() || 
              this.passwordInput.isEmpty()); 
    }
    
}
