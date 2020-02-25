
package com.gb4w20.gb4w20.backingbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 * Used as a backing bean for the login page. 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class LoginBackingBean implements Serializable {
    
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginBackingBean.class);
    private static final String CLIENT_PAGE = "index.xhtml";
    private static final String MANAGER_PAGE = "manager_frontpage.xhtml"; 
    private static final String LOGIN_PAGE ="login.xhtml";
    //Credit to https://stackoverflow.com/questions/8204680/java-regex-email Jason Buberel for regex pattern 
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
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
            this.emailInput = ""; 
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
        if (this.passwordInput == null){
            this.passwordInput = ""; 
        }
        return this.passwordInput; 
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
     * @return A string of a uri of where to redirect to
     * @author Jeffrey Boisvert
     */
    public String login(){
        
        //If not valid notify user
        if(areInputsNotValid()){
            LOG.info("Values not valid for " + this.emailInput + " with password " + this.passwordInput);
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            //TODO i18n currently not working
            context.addMessage(null, new FacesMessage("Invalid Parameters", "Please provide valid email and password"));
            
            return null;
         }
        
        if(this.session.loginUser(this.emailInput, this.passwordInput)){
            LOG.info("Login successful for " + this.emailInput + " with password " + this.passwordInput);
            return this.session.isLoggedInManager() ? MANAGER_PAGE : CLIENT_PAGE;  
        }
        
         LOG.info("No user found for " + this.emailInput + " with password " + this.passwordInput);
         FacesContext context = FacesContext.getCurrentInstance();
         ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
         context.addMessage(null, new FacesMessage("Invalid login", "Email and password provided did not match any of our records. Please try again"));
            
        return null;
        
    }
    
    /**
     * Used as a help method to check if the inputs are valid
     * @return true of valid, false otherwise
     * @author Jeffrey Boisvert
     */
    private boolean areInputsNotValid() {
        return isEmailNotValid() || isPasswordNotValid(); 
    }
    
    /**
     * Used to validate if the email is valid or not. 
     * @return true if not valid
     * @author Jeffrey Boisvert
     */
    private boolean isEmailNotValid(){
       Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(this.emailInput);
        
       return this.emailInput == null || 
              this.emailInput.isBlank() || 
              this.emailInput.isEmpty() ||
              (!matcher.find()); 
    }
    
    /**
     * Used to valid if the password is valid or not
     * @return true if not valid
     * @author Jeffrey Boisvert
     */
    private boolean isPasswordNotValid(){
       return this.passwordInput == null || 
              this.passwordInput.isBlank() || 
              this.passwordInput.isEmpty(); 
    }
    
}
