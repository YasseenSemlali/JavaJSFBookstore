
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import com.gb4w20.gb4w20.jsf.validation.FormValues;
import com.gb4w20.gb4w20.jsf.validation.JSFFormMessageValidator;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
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

    @Inject
    private UsersJpaController userJpaController;
    
    @Inject 
    private JSFFormMessageValidator validator;
    
    @Inject
    private FormValues values;
    
    //Inputs
    @Size(min = 1, max = 5)
    private String titleInput;
    @Size(min = 1, max = 30)
    private String firstNameInput;
    @Size(min = 1, max = 30)
    private String lastNameInput;
    @Size(max = 20)
    private String companyInput; 
    @Size(min = 1, max = 50)
    private String firstAddressInput;
    @Size(max = 50)
    private String secondAddressInput;
    @Size(min = 1, max = 20)
    private String cityInput; 
    @Size(min = 1, max = 2)
    private String provinceInput;
    @Size(min = 1, max = 10)
    private String countryInput;
    @Size(min = 1, max = 10)
    private String postalInput;
    @Size(min = 1, max = 10)
    private String mobilePhoneInput;
    @Size(min = 1, max = 10)
    private String homePhoneInput;
    @Size(min = 1, max = 50)
    private String emailInput; 
    @Size(min = 1, max = 100)
    private String passwordInput;
    
    /**
     * Mainly used to set default values.
     * @author Jeffrey Boisvert
     */
    @PostConstruct
    public void init(){
        //No other country than Canada for now
        this.countryInput = "Canada";
    }
    
    /**
     * Getter for the available titles. 
     * If the value is null it creates the list. 
     * @return list of available titles
     */
    public List<String> getAvailableTitles(){
        return this.values.getAvailableTitles();
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
    public void setCityInput(String city) {
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
     * Used to get the provinceSelections value set
     * @return List of the set provinceSelections.
     * @author Jeffrey Boisvert
     */
    public List<String> getProvinceSelections() {
        return this.values.getProvinceSelections();
    }
    
    
    /**
     * Used to register the user
     * @return A string of a uri of where to redirect to
     * @author Jeffrey Boisvert
     */
    public String register(){
         
        Users user = generateUserBasedOnInput();
        LOG.info("Creating user " + user);
        this.userJpaController.create(user);
            
        //TODO show an alert saying register successful please login
        return LOGIN_PAGE;
        
    }
    
    /**
     * Used to validate if the user entered a value. 
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateIsNotBlank(FacesContext fc, UIComponent c, Object value) {
        this.validator.validateIsNotBlank((String)value);
    }
    
    /**
     * Used to validate if the user entered a value but allow it to be blank. 
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateIsNotJustWhiteSpace(FacesContext fc, UIComponent c, Object value) {
        this.validator.validateIsNotJustWhiteSpace((String)value);
    }
    
    /**
     * Used to validate if email is in the correct format and not already taken
     * Format: test@email.com
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateEmail(FacesContext fc, UIComponent c, Object value) {
        this.validator.validateEmail((String)value);
    }
    
    /**
     * Used to validate if postal code is in the correct format
     * Format: A1A2B2
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validatePostalCode(FacesContext fc, UIComponent c, Object value) {
        this.validator.validatePostalCode((String)value);
    }
    
    /**
     * Used to validate if phone number is in the correct format
     * Format: 123-123-1234
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validatePhone(FacesContext fc, UIComponent c, Object value) {
        this.validator.validatePhone((String)value);
    }
    
    /**
     * Used to validate if password entered is strong enough
     * Valid password:
     *      - at least 8 characters
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validatePassword(FacesContext fc, UIComponent c, Object value) {
        this.validator.validatePassword((String)value);
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
