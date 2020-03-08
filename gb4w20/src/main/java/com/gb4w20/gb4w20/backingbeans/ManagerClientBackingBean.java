package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput; 
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
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

    private final static Logger LOG = LoggerFactory.getLogger(ManagerClientBackingBean.class);

    @Inject
    private UsersJpaController usersJpaController;

    //To know if editing or not
    private boolean edit;

    //User values
    private Long selectedUserId;
    private String selectedUserTitle; 
    private String selectedUserFirstName; 
    private String selectedLastName;
    private String selectedCompany;
    private String selectedAddress1; 
    private String selectedAddress2; 
    private String selectedCity; 
    private String selectedProvince; 
    private String selectedCountry; 
    private String selectedPostalCode; 
    private String selectedHomePhone; 
    private String selectedCellPhone; 
    private String selectedEmail; 
    //This is just for the sake of the project this would not be in production. 
    private String selectedPassword; 
    private boolean selectedIsManagerState; 

    //Total sales of selected User
    private BigDecimal totalSales;

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
            LOG.debug("Clearing fields");
            clearSelectedFields();
        } else {
            LOG.debug("Setting to user with id " + userId);
            this.edit = true;
            setFieldsBasedOnSelectedUser(userId);

        }
    }

    /**
     * Submits the user form. Will either edit an existing user, or add a new
     * user, depending on the selected field.
     *
     * @return redirection
     * @author Jeffrey Boisvert
     */
    public String submitUser() {
        if (this.edit) {
            return editUser();
        } else {
            return addUser();
        }
    }

    /**
     * Method to add a user based on set fields
     *
     * @return redirection
     * @author Jeffrey Boisvert
     */
    private String addUser() {
        try {
            Users user = new Users();

            setUserObjectBasedOnSelectedInputs(user);

            this.usersJpaController.create(user);

            return "/action-responses//action-responses/action-success";
        } catch (Exception ex) {
            return "/action-responses/action-failure";

        }
    }

    /**
     * Method to edit a user based on set fields
     *
     * @return redirection
     * @author Jeffrey Boisvert
     */
    private String editUser() {
        try {
            Users user = this.usersJpaController.findUsers(this.selectedUserId);
            setUserObjectBasedOnSelectedInputs(user);
            
            this.usersJpaController.edit(user);
            
            //TODO make it show a good pop up 
            return "/action-responses/action-success";
        } catch (Exception ex) {
            //TODO make it show a bad pop up error
            return "/action-responses/action-failure";
        }
    }
    
    /**
     * Helper method used to construct the user object based on the selected attributes
     * @param user to have the values set on
     * @return the constructed user
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
            this.selectedPassword = "";  
            this.totalSales = new BigDecimal(0);
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
            this.selectedPassword = user.getPassword(); 
            //TODO get purchased items ever total not just by dates
            this.totalSales = new BigDecimal(1000);    
    }
    
    //Getters and Setters
    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
    
    public BigDecimal getTotalSales() {
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
