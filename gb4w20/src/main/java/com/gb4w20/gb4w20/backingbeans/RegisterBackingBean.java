
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
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
    
    //Lists for selections
    private List<String> availableTitles; 
    
    //Inputs
    private String titleInput; 
    private String firstNameInput;
    private String lastNameInput;
    private String companyInput; 
    private String firstAddressInput;
    private String secondAddressInput;
    private String cityInput; 
    private String provinceInput;
    private String countryInput;
    private String postalInput;
    private String mobilePhoneInput; 
    private String homePhoneInput;
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
        this.countryInput = "Canada";
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
     * Getting for the firstNameInput and sets value to empty string if null
     * @return firstNameInput value
     * @author Jeffrey Boisvert
     */
    public String getFirstNameInput() {
        if(this.firstNameInput == null){
            return ""; 
        }
        return firstNameInput;
    }
    
    /**
     * Setter for the firstNameInput
     * @param firstNameInput 
     * @author Jeffrey Boisvert
     */
    public void setFirstNameInput(String firstNameInput) {
        this.firstNameInput = firstNameInput;
    }
    
    /**
     * Getter for lastNameInput and returns empty string if null
     * @return value of lastNameInput
     * @author Jeffrey Boisvert
     */
    public String getLastNameInput() {
        if(this.lastNameInput == null){
            return ""; 
        }
        return lastNameInput;
    }
    
    /**
     * Setter for the lastNameInput
     * @param lastNameInput
     * @author Jeffrey Boisvert
     */
    public void setLastNameInput(String lastNameInput) {
        this.lastNameInput = lastNameInput;
    }
    
    /**
     * Used to get the firstAddressInput value inputted
     * @return String of firstAddressInput.
     * @author Jeffrey Boisvert
     */
    public String getFirstAddressInput() {
        if(this.firstAddressInput == null){
            return ""; 
        }
        return firstAddressInput;
    }

    /**
     * Used to set the firstAddressInput to the value given
     * @param firstAddressInput given
     * @author Jeffrey Boisvert 
     */
    public void setFirstAddressInput(String firstAddressInput) {
        this.firstAddressInput = firstAddressInput;
    }
    
    /**
     * Used to get the secondAddressInput value inputted
     * @return String of secondAddressInput.
     * @author Jeffrey Boisvert
     */
    public String getSecondAddressInput() {
        if(this.secondAddressInput == null){
            return ""; 
        }
        return secondAddressInput;
    }
    
    /**
     * Used to set the secondAddressInput to the value given
     * @param secondAddressInput given
     * @author Jeffrey Boisvert 
     */
    public void setSecondAddressInput(String secondAddressInput) {
        this.secondAddressInput = secondAddressInput;
    }
    
    /**
     * Used to get the cityInput value inputted
     * @return String of cityInput.
     * @author Jeffrey Boisvert
     */
    public String getCityInput() {
        if(this.cityInput == null){
            return ""; 
        }
        return cityInput;
    }
    
    /**
     * Used to set the cityInput to the value given
     * @param city given
     * @author Jeffrey Boisvert 
     */
    public void setCity(String city) {
        this.cityInput = city;
    }
    
    /**
     * Used to get the provinceInput value inputted
     * @return String of provinceInput.
     * @author Jeffrey Boisvert
     */
    public String getProvinceInput() {
        if(this.provinceInput == null){
            return ""; 
        }
        return provinceInput;
    }
    
    /**
     * Used to set the provinceInput to the value given
     * @param provinceInput given
     * @author Jeffrey Boisvert 
     */
    public void setProvinceInput(String provinceInput) {
        this.provinceInput = provinceInput;
    }
    
    /**
     * Used to get the countryInput value inputted
     * @return String of countryInput.
     * @author Jeffrey Boisvert
     */
    public String getCountryInput() {
        if(this.countryInput == null){
            return ""; 
        }
        return countryInput;
    }
    
    /**
     * Used to set the countryInput to the value given
     * @param countryInput given
     * @author Jeffrey Boisvert 
     */
    public void setCountryInput(String countryInput) {
        this.countryInput = countryInput;
    }
    
    /**
     * Used to get the getPostalInput value inputted
     * @return String of getPostalInput.
     * @author Jeffrey Boisvert
     */
    public String getPostalInput() {
        if(this.postalInput == null){
            return ""; 
        }
        return postalInput;
    }
    
    /**
     * Used to set the postalInput to the value given
     * @param postalInput given
     * @author Jeffrey Boisvert 
     */
    public void setPostalInput(String postalInput) {
        this.postalInput = postalInput;
    }
    
    /**
     * Used to get the mobilePhoneInput value inputted
     * @return String of the inputted mobilePhoneInput.
     * @author Jeffrey Boisvert
     */
    public String getMobilePhoneInput() {
        if(this.mobilePhoneInput == null){
            return ""; 
        }
        return mobilePhoneInput;
    }
    
    /**
     * Used to set the mobilePhoneInput to the value given
     * @param mobilePhoneInput given
     * @author Jeffrey Boisvert 
     */
    public void setMobilePhoneInput(String mobilePhoneInput) {
        this.mobilePhoneInput = mobilePhoneInput;
    }
    
    /**
     * Used to get the homePhoneInput value inputted
     * @return String of the inputted homePhoneInput.
     * @author Jeffrey Boisvert
     */
    public String getHomePhoneInput() {
        if(this.homePhoneInput == null){
            return ""; 
        }
        return homePhoneInput;
    }
    
    /**
     * Used to set the homePhoneInput to the value given
     * @param homePhoneInput given
     * @author Jeffrey Boisvert 
     */
    public void setHomePhoneInput(String homePhoneInput) {
        this.homePhoneInput = homePhoneInput;
    }
    
    /**
     * Used to get the companyInput value inputted
     * @return String of the inputted companyInput.
     * @author Jeffrey Boisvert
     */
    public String getCompanyInput() {
        if(this.companyInput == null){
            return ""; 
        }
        return companyInput;
    }
    
    /**
     * Used to set the companyInput to the value given
     * @param companyInput given
     * @author Jeffrey Boisvert 
     */
    public void setCompanyInput(String companyInput) {
        this.companyInput = companyInput;
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
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
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
     * Used as a helper method to set the list of all supported provinces
     * @author Jeffrey Boisvert
     */
    private void generateProvinceList() {
        //TODO Not sure how to make this i18n compliant. Also what titles does Ken want? 
        this.availableTitles = new ArrayList<>(Arrays.asList("Mr", "Mrs", "Ms", "Dr") );    
    }
    
    /**
     * Used to validate if the user entered a name. 
     * Design decision to only validate if empty or blank string (allow user to enter 123 if they
     * really want to it is a string regardless). 
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateName(FacesContext fc, UIComponent c, Object value) {
        if (value == null || ((String) value).isBlank() || ((String) value).isEmpty()) {
            throw new ValidatorException(new FacesMessage(
                    this.bundle.getString("name_error")));
        }
    }
    
    /**
     * Helper method to generate a user object based on the inputs 
     * set in the backing bean. 
     * @return user object with attributes reflecting what was entered in the form. 
     * @author Jeffrey Boisvert
     */
    private Users generateUserBasedOnInput() {
        Users user = new Users(); 
        user.setTitle(titleInput);
        user.setFirstName(firstNameInput);
        user.setLastName(lastNameInput);
        user.setCompanyName(companyInput);
        user.setAddress1(firstAddressInput);
        user.setAddress2(secondAddressInput);
        user.setCity(cityInput);
        user.setProvince(provinceInput);
        user.setCountry(countryInput);
        user.setPostalCode(postalInput);
        user.setCellPhone(mobilePhoneInput);
        user.setHomePhone(homePhoneInput);
        user.setEmail(emailInput);
        user.setPassword(passwordInput);
        
        return user;
    }
    
}
