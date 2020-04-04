package com.gb4w20.gb4w20.jsf.validation;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import org.slf4j.LoggerFactory;

/**
 * Holds useful methods to send i18n FaceMessages based on validation method
 *
 * @author Jeffrey Boisvert, Jasmar Badion
 */
@Named
@RequestScoped
public class JSFFormMessageValidator implements Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(JSFFormMessageValidator.class);

    //REGEX
    //Credit to https://stackoverflow.com/questions/8204680/java-regex-email Jason Buberel for email regex pattern 
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("[0-9]{3}[0-9]{3}[0-9]{4}");
    public static final Pattern VALID_POSTAL_CODE_REGEX = Pattern.compile("[A-Z][0-9][A-Z][0-9][A-Z][0-9]");
    public static final Pattern VALID_CVV = Pattern.compile("[0-9]{3}");

    //Bundle for i18n
    private ResourceBundle bundle;

    @Inject
    UsersJpaController userJpaController;

    /**
     * Mainly used to set the bundle.
     *
     * @author Jeffrey Boisvert
     */
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        this.bundle = context.getApplication().getResourceBundle(context, "msgs");
    }

    /**
     * Used to validate if the start date and end date combo is valid
     *
     * @param startDate given
     * @param endDate given
     * @return true if dates were valid and false otherwise
     * @author Jeffrey Boisvert
     */
    public boolean validateDatesAreValid(Date startDate, Date endDate) {

        if (startDate == null || endDate == null) {
            FacesMessage message = new FacesMessage(this.bundle.getString("missing_dates_error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        }

        if (startDate.after(endDate)) {
            FacesMessage message = new FacesMessage(this.bundle.getString("start_date_after_end_date_error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        }

        return true;

    }

    /**
     * Used to validate if the given collection is empty or not.
     *
     * @param collection given to check
     * @param errorMessagePropertyKey to get a message from the message bundle
     * to build a FaceMessage from
     * @return true if collection is not empty and false if it is empty
     * @author Jeffrey Boisvert
     */
    public boolean validateCollectionIsNotEmpty(Collection collection, String errorMessagePropertyKey) {

        if (collection.isEmpty()) {
            FacesMessage message = new FacesMessage(this.bundle.getString(errorMessagePropertyKey));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        }

        return true;

    }

    /**
     * Used to create a i18n Faces Message based on the given message property
     * key
     *
     * @param messagePropertyKey
     */
    public void createFacesMessageFromKey(String messagePropertyKey) {
        FacesMessage message = new FacesMessage(this.bundle.getString(messagePropertyKey));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Used to validate if the user entered a name. Design decision to only
     * validate if empty or blank string (allow user to enter 123 if they really
     * want to it is a string regardless).
     *
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateIsNotBlank(String value) {
        if (value.isBlank()) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("empty_error"));

            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    /**
     * Used to validate if the user entered a valid value or just white space.
     * This allows a blank string to pass but not a string like " ".
     *
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateIsNotJustWhiteSpace(String value) {
        if (value.length() > 0 && value.isBlank()) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("whitespace_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }
    }

    /**
     * Used to validate if email is in the correct format and not already taken
     * Format: test@email.com
     *
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateEmail(String value) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher((String) value);

        validateEmailFormat(value);

        try {
            LOG.debug("Looking at email " + value);
            Users user = this.userJpaController.findUsers(value);
            LOG.debug("Found user " + user);

            FacesMessage message = new FacesMessage(
                    this.bundle.getString("email_taken_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        } catch (NoResultException | NonUniqueResultException ex) {
            LOG.debug("Email is not taken " + value, ex);
        }

    }

    /**
     * Used to validate if email is in the correct format Format: test@email.com
     *
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validateEmailFormat(String value) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher((String) value);

        if (!matcher.find()) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("email_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }

    }

    /**
     * Used to validate if postal code is in the correct format Format: A1A2B2
     *
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validatePostalCode(String value) {
        Matcher matcher = VALID_POSTAL_CODE_REGEX.matcher(value);

        if (!matcher.find()) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("postal_code_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }
    }

    /**
     * Used to validate if phone number is in the correct format Format:
     * 123-123-1234
     *
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validatePhone(String value) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(value);

        if (!matcher.find()) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("phone_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }
    }
    
    /**
     * Used to validate if the passwords match 
     * @param password
     * @param retypedPassword
     * @author Jeffrey Boisvert
     */
    public void validateThatPasswordsMatch(String password, String retypedPassword){
        
        //Validate if the password retyped is even valid
        validatePassword(retypedPassword);
        
        if (!password.equals(retypedPassword)) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("passwords_do_not_match"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }
    }

    /**
     * Used to validate if password entered is strong enough Valid password: -
     * at least 8 characters
     *
     * @param value entered
     * @author Jeffrey Boisvert
     */
    public void validatePassword(String value) {
        if (value.length() < 8) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("password_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }
    }

    /**
     * Builds a FacesMessage notifying the user that their login details were
     * invalid
     */
    public void inValidLoginError() {
        FacesMessage message = new FacesMessage(this.bundle.getString("invalidLoginDetails"));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void formSubmissionSuccessful() {
        FacesMessage message = new FacesMessage(
                this.bundle.getString("form_submission_successful"));
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void formSubmissionUnsuccessful() {
        FacesMessage message = new FacesMessage(
                this.bundle.getString("form_submission_unsuccessful"));
        message.setSeverity(FacesMessage.SEVERITY_ERROR);

        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    /**
     * Validating the credit card security code or CVV
     *
     * @param value
     * @author Jasmar Badion
     */
    public void validateCVV(String value) {
        Matcher matcher = VALID_CVV.matcher(value);

        if (!matcher.find()) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("cvv_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }
    }

    /**
     * Validate the expiry date for credit card if empty 
     * or expired
     * @param date 
     * @author Jasmar Badion
     */
    public void validateExpiryDate(Date date) {
        //get current date in MM/yyyy format
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
        Date datenow = new Date();
        LOG.info(formatter.format(datenow));
        
        if (date == null || datenow.after(date)) {
            FacesMessage message = new FacesMessage(
                    this.bundle.getString("expirydate_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(message);
        }
    }
}
