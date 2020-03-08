
package com.gb4w20.gb4w20.backingbeans;

import static com.gb4w20.gb4w20.backingbeans.RegisterBackingBean.VALID_EMAIL_ADDRESS_REGEX;
import com.gb4w20.gb4w20.entities.Users;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
    private static final String MANAGER_PAGE = "manager-frontpage.xhtml"; 
    private static final String LOGIN_PAGE ="login.xhtml";
    //Credit to https://stackoverflow.com/questions/8204680/java-regex-email Jason Buberel for regex pattern 
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    @Inject
    UserSessionBean session; 
    
    private String emailInput; 
    private String passwordInput;
    
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
     * @return A string of a URI of where to redirect to
     * @author Jeffrey Boisvert
     */
    public String login(){
        
        if(this.session.loginUser(this.emailInput, this.passwordInput)){
            LOG.info("Login successful for " + this.emailInput + " with password " + this.passwordInput);
            return this.session.isLoggedInManager() ? MANAGER_PAGE : CLIENT_PAGE;  
        }
        
        //Invalid login
        FacesMessage message = new FacesMessage(this.bundle.getString("invalidLoginDetails"));
        FacesContext.getCurrentInstance().addMessage(null, message);
            
        return null;
        
    }
    
    /**
     * Used to validate if email is in the correct format
     * Format: test@email.com
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateEmail(FacesContext fc, UIComponent c, Object value) {
       Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher((String) value);
        
       if(!matcher.find()){
         throw new ValidatorException(new FacesMessage(
                    this.bundle.getString("email_error")));
        }

    }
    
    /**
     * Used to validate if the user entered a value. 
     * Design decision to only validate if empty or blank string (allow user to enter 123 if they
     * really want to it is a string regardless). 
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateIsNotBlank(FacesContext fc, UIComponent c, Object value) {
        if (((String) value).isBlank()) {
            throw new ValidatorException(new FacesMessage(
                    this.bundle.getString("empty_error")));
        }
    }
    
}
