package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import com.gb4w20.gb4w20.jsf.validation.FormValues;
import com.gb4w20.gb4w20.jsf.validation.JSFFormMessageValidator;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput; 
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to manage the client page. 
 * This also handles being able to create and edit the user as well.
 * Shout out to Jean Robatto for help on structuring code and logic. 
 * 
 * @author Jeffrey Boisvert
 */
@Named("managerClient")
@SessionScoped
public class ManagerClientBackingBean implements Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(ManagerClientBackingBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    @Inject
    private FormValues values;
    
    @Inject 
    private JSFFormMessageValidator validator;

    //To know if editing or not
    private boolean edit;

    //User values
    private Long selectedUserId;
    @Size(min = 1, max = 5)
    private String selectedUserTitle; 
    @Size(min = 1, max = 30)
    private String selectedUserFirstName; 
    @Size(min = 1, max = 30)
    private String selectedLastName;
    @Size(max = 20)
    private String selectedCompany;
    @Size(min = 1, max = 50)
    private String selectedAddress1; 
    @Size(max = 50)
    private String selectedAddress2; 
    @Size(min = 1, max = 20)
    private String selectedCity; 
    @Size(min = 1, max = 2)
    private String selectedProvince; 
    @Size(min = 1, max = 10)
    private String selectedCountry; 
    @Size(min = 1, max = 10)
    private String selectedPostalCode;
    @Size(min = 1, max = 10)
    private String selectedHomePhone; 
    @Size(min = 1, max = 10)
    private String selectedCellPhone; 
    @Size(min = 1, max = 50)
    private String selectedEmail; 
    //This is just for the sake of the project this would not be in production. 
    @Size(min = 1, max = 100)
    private String selectedPassword; 
    private boolean selectedIsManagerState; 

    //Total sales of selected User
    private Double totalSales;
    //Used to validate if the email changed or not.
    @Size(min = 1, max = 50)
    private String previousEmail; 

    /**
     * Whenever a user is changed, fills the form fields with the info
     * from the user, or it empties the fields if new is selected.
     *
     * @param e
     * @author Jeffrey Boisvert
     */
    public void userChanged(AjaxBehaviorEvent e) {
        Long userId = (Long) ((UIOutput) e.getSource()).getValue();
        if (userId == -1 || userId == null) {
            this.edit=false;
            LOG.info("Clearing fields");
            clearSelectedFields();
        } else {
            LOG.info("Setting to user with id " + userId);
            this.edit = true;
            setFieldsBasedOnSelectedUser(userId);

        }
    }

    /**
     * Submits the user form. Will either edit an existing user, or add a new
     * user, depending on the selected field.
     *
     * @author Jeffrey Boisvert
     */
    public void submitUser() {
        if (this.edit) {
            editUser();
        } else {
            addUser();
        }
    }

    /**
     * Method to add a user based on set fields
     *
     * @return redirection
     * @author Jeffrey Boisvert
     */
    private void addUser() {
        try {
            LOG.debug("Creating user!");
            Users user = new Users();

            setUserObjectBasedOnSelectedInputs(user);
            
            LOG.info("Creating user " + user);
            
            this.usersJpaController.create(user);
            this.validator.formSubmissionSuccessful();
            
        } catch (ValidatorException ex){
            //Let message bubble up
            throw ex; 
        } catch (Exception ex) {
            LOG.error("Was unable to add user", ex);
            this.validator.formSubmissionUnsuccessful();
        }
    }

    /**
     * Method to edit a user based on set fields
     *
     * @return redirection
     * @author Jeffrey Boisvert
     */
    private void editUser() {
        try {
            LOG.info("Editing user");
            Users user = this.usersJpaController.findUsers(this.selectedUserId);
            
            setUserObjectBasedOnSelectedInputs(user);    
            LOG.info("Editing user " + user);
            
            this.usersJpaController.edit(user);
            this.validator.formSubmissionSuccessful();
            
        } catch (ValidatorException ex){
            //Let message bubble up
            throw ex; 
        } catch (Exception ex) {
            LOG.error("Was unable to edit user", ex);
            this.validator.formSubmissionUnsuccessful();
        }
    }
    
    /**
     * Helper method used to construct the user object based on the selected attributes
     * @param user to have the values set on
     * @return the constructed user
     * @author Jeffrey Boisvert
     */
    private void setUserObjectBasedOnSelectedInputs(Users user){
            user.setTitle(this.selectedUserTitle);
            user.setFirstName(this.selectedUserFirstName);
            user.setLastName(this.selectedLastName); 
            user.setCompanyName(this.selectedCompany);
            user.setAddress1(this.selectedAddress1);
            user.setAddress2(this.selectedAddress2);
            user.setCity(this.selectedCity);
            user.setProvince(this.selectedProvince);
            user.setCountry(this.selectedCountry);
            user.setPostalCode(this.selectedPostalCode);
            user.setHomePhone(this.selectedHomePhone);
            user.setCellPhone(this.selectedCellPhone);
            user.setEmail(this.selectedEmail);
            user.setPassword(this.selectedPassword);
            user.setIsManager(this.selectedIsManagerState);
    }
    
    /**
     * A helper method to set all the selected fields to blank 
     * @author Jeffrey Boisvert
     */
    private void clearSelectedFields() {
            this.selectedUserTitle = "";
            this.selectedUserFirstName = ""; 
            this.selectedLastName = ""; 
            this.selectedCompany = "";
            this.selectedAddress1 = ""; 
            this.selectedAddress2 = ""; 
            this.selectedCity = ""; 
            this.selectedProvince = "";
            this.selectedCountry = "";
            this.selectedPostalCode = "";
            this.selectedHomePhone = "";
            this.selectedCellPhone = ""; 
            this.selectedEmail = ""; 
            //There is no previous email
            this.previousEmail = "";
            this.selectedPassword = "";  
            this.selectedIsManagerState = false;
            this.totalSales = 0.0;
    }
    
    
    /**
     * Helper method to set the selected fields to the user 
     * based on the given user id. 
     * @param userId of the user in question
     * @author Jeffrey Boisvert
     */
    private void setFieldsBasedOnSelectedUser(long userId) {
            Users user = this.usersJpaController.findUsers(userId);
            this.selectedUserTitle = user.getTitle();
            this.selectedUserFirstName = user.getFirstName(); 
            this.selectedLastName = user.getLastName(); 
            this.selectedCompany = user.getCompanyName();
            this.selectedAddress1 = user.getAddress1(); 
            this.selectedAddress2 = user.getAddress2(); 
            this.selectedCity = user.getCity(); 
            this.selectedProvince = user.getProvince();
            this.selectedCountry = user.getCountry();
            this.selectedPostalCode = user.getPostalCode();
            this.selectedHomePhone = user.getHomePhone();
            this.selectedCellPhone = user.getCellPhone(); 
            this.selectedEmail = user.getEmail();
            //Used to store and compare if ever
            this.previousEmail = user.getEmail();
            this.selectedPassword = user.getPassword(); 
            this.selectedIsManagerState = user.getIsManager();
            this.totalSales = this.usersJpaController.getUsersTotalSales(userId);    
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
     * Used to validate if email is in the correct format.
     * If editing, will only validate format
     * If adding, will validate format and if already taken.
     * Format: test@email.com
     * @param fc
     * @param c
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateEmail(FacesContext fc, UIComponent c, Object value) {
        if(this.edit && previousEmail.equals(value)){
            this.validator.validateEmailFormat((String)value);
        }
        else {
            this.validator.validateEmail((String)value);
        }

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
    
    //List of available options
    /**
     * Getter for the available titles. 
     * If the value is null it creates the list. 
     * @return list of available titles
     */
    public List<String> getAvailableTitles(){
        return this.values.getAvailableTitles();
    }
    
    /**
     * Used to get the provinceSelections value set
     * @return List of the set provinceSelections.
     * @author Jeffrey Boisvert
     */
    public List<String> getProvinceSelections() {
        return this.values.getProvinceSelections();
    }
    
    //Getters and Setters
    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
    
    public double getTotalSales() {
        if(this.totalSales == null){
            return 0.0;
        }
        return totalSales;
    }

    public Long getSelectedUserId() {
        if(selectedUserId == null){
            return -1l;
        }
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public String getSelectedUserTitle() {
        if(selectedUserTitle == null){
            return "";
        }
        return selectedUserTitle;
    }

    public void setSelectedUserTitle(String selectedUserTitle) {
        this.selectedUserTitle = selectedUserTitle;
    }

    public String getSelectedUserFirstName() {
        if(selectedUserFirstName == null){
            return "";
        }
        return selectedUserFirstName;
    }

    public void setSelectedUserFirstName(String selectedUserFirstName) {
        this.selectedUserFirstName = selectedUserFirstName;
    }

    public String getSelectedLastName() {
        if(selectedLastName == null){
            return "";
        }
        return selectedLastName;
    }

    public void setSelectedLastName(String selectedLastName) {
        this.selectedLastName = selectedLastName;
    }

    public String getSelectedCompany() {
        if(selectedCompany == null){
            return "";
        }
        return selectedCompany;
    }

    public void setSelectedCompany(String selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public String getSelectedAddress1() {
        if(selectedAddress1 == null){
            return "";
        }
        return selectedAddress1;
    }

    public void setSelectedAddress1(String selectedAddress1) {
        this.selectedAddress1 = selectedAddress1;
    }

    public String getSelectedAddress2() {
        if(selectedAddress2 == null){
            return "";
        }
        return selectedAddress2;
    }

    public void setSelectedAddress2(String selectedAddress2) {
        this.selectedAddress2 = selectedAddress2;
    }

    public String getSelectedCity() {
        if(selectedCity == null){
            return "";
        }
        return selectedCity;
    }

    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    public String getSelectedProvince() {
        if(selectedProvince == null){
            return "";
        }
        return selectedProvince;
    }

    public void setSelectedProvince(String selectedProvince) {
        this.selectedProvince = selectedProvince;
    }

    public String getSelectedCountry() {
        if(selectedCountry == null){
            return "";
        }
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public String getSelectedPostalCode() {
        if(selectedPostalCode == null){
            return "";
        }
        return selectedPostalCode;
    }

    public void setSelectedPostalCode(String selectedPostalCode) {
        this.selectedPostalCode = selectedPostalCode;
    }

    public String getSelectedHomePhone() {
        if(selectedHomePhone == null){
            return "";
        }
        return selectedHomePhone;
    }

    public void setSelectedHomePhone(String selectedHomePhone) {
        this.selectedHomePhone = selectedHomePhone;
    }

    public String getSelectedCellPhone() {
        if(selectedCellPhone == null){
            return "";
        }
        return selectedCellPhone;
    }

    public void setSelectedCellPhone(String selectedCellPhone) {
        this.selectedCellPhone = selectedCellPhone;
    }

    public String getSelectedEmail() {
        if(selectedEmail == null){
            return "";
        }
        return selectedEmail;
    }

    public void setSelectedEmail(String selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    public String getSelectedPassword() {
        if(selectedPassword == null){
            return "";
        }
        return selectedPassword;
    }

    public void setSelectedPassword(String selectedPassword) {
        this.selectedPassword = selectedPassword;
    }

    public boolean isSelectedIsManagerState() {
        return selectedIsManagerState;
    }

    public void setSelectedIsManagerState(boolean selectedIsManagerState) {
        this.selectedIsManagerState = selectedIsManagerState;
    }
 
}
