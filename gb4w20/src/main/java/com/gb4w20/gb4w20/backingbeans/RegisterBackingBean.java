
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * Used as a backing bean for the register page. 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class RegisterBackingBean implements Serializable {
    
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(RegisterBackingBean.class);
    private static final String CLIENT_PAGE = "index.xhtml";
    private static final String LOGIN_PAGE ="login.xhtml";
    //Credit to https://stackoverflow.com/questions/8204680/java-regex-email Jason Buberel for regex pattern 
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    @Inject
    UsersJpaController userJpaController;
    
    private List<String> availableTitles; 
    private String titleInput; 
    private String emailInput; 
    private String passwordInput;
    
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
     * Getter for the titleInput
     * @return value set for titleInput
     * @author Jeffrey Boisvert
     */
    public String getTitleInput(){
        if(this.titleInput == null){
            return "";
        }
        return this.titleInput; 
    }
    
    /**
     * Setter for the titleInput field. 
     * @param titleInput given
     * @author Jeffrey Boisvert
     */
    public void setTitleInput(String titleInput){
        this.titleInput = titleInput; 
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
     * Used to register the user
     * @return A string of a uri of where to redirect to
     * @author Jeffrey Boisvert
     */
    public String register(){
        
        //If not valid parameters
        //TODO build error message
        if(areInputsNotValid()){
            LOG.info("Values not valid for " + this.emailInput + " with password " + this.passwordInput);
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            //TODO i18n currently not working
            context.addMessage(null, new FacesMessage("Invalid Parameters", "Please provide valid email and password"));
            
            return null;
         }
         
        Users user = generateUserBasedOnInput();
        this.userJpaController.create(user);
            
        //TODO show an alert saying register successful please login
        return LOGIN_PAGE;
        
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
              //this.emailInput.isBlank() || 
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
              //this.passwordInput.isBlank() || 
              this.passwordInput.isEmpty(); 
    }

    /**
     * Used as a helper method to set the list based on the local 
     * @author Jeffrey Boisvert
     */
    private void generateAvailableTitles() {
        //TODO Not sure how to make this i18n compliant. Also what titles does Ken want? 
        this.availableTitles = new ArrayList<>(Arrays.asList("Mr", "Mrs", "Ms", "Dr") );    
    }
    
    /**
     * Helper method to generate a user object based on the inputs 
     * set in the backing bean. 
     * @return user object with attributes reflecting what was entered in the form. 
     * @author Jeffrey Boisvert
     */
    private Users generateUserBasedOnInput() {
        Users user = new Users(); 
        user.setTitle(this.titleInput);
        user.setEmail(this.emailInput);
        user.setPassword(this.passwordInput);
        
        return user;
    }
    
}
