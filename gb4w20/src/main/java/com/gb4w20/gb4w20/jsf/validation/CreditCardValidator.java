/*
 * Package for all validations and covertions
 */
package com.gb4w20.gb4w20.jsf.validation;

import com.gb4w20.gb4w20.backingbeans.CreditCardBackingBean;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Credit Card Validator</h1>
 * <p>
 * This will validate the credit card using the Luhn test
 * </p>
 *
 * @author Jasmar Badion
 */
@FacesValidator("com.gb4w20.gb4w20.jsf.validation.CreditCardValidator")
public class CreditCardValidator implements Validator {

    private final static Logger LOG = LoggerFactory.getLogger(CreditCardValidator.class);

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) {
        if (value == null) {
            return;
        }
        String cardNumber;
        if (value instanceof CreditCardBackingBean) {
            cardNumber = value.toString();
        } else {
            cardNumber = value.toString().replaceAll("\\D", ""); // remove
        }																	// non-digits
        
        if (!luhnCheck(cardNumber) || cardNumber.equals("")) {
            FacesMessage message = CreditCardMessages.getMessage(
                    "com.gb4w20.gb4w20.bundles.messages", "badLuhnCheck", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);

        }
    }

    /**
     * Doing a Luhn check to validate credit card number
     * @param cardNumber
     * @return 
     */
    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;

        for (int i = cardNumber.length() - 1; i >= 0; i -= 2) {
            sum += Integer.parseInt(cardNumber.substring(i, i + 1));
            if (i > 0) {
                int d = 2 * Integer.parseInt(cardNumber.substring(i - 1, i));
                if (d > 9) {
                    d -= 9;
                }
                sum += d;
            }
        }

        return sum % 10 == 0;
    }
}
