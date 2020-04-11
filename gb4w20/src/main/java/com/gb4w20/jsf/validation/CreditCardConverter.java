/*
 * Package for all validations and covertions
 */
package com.gb4w20.jsf.validation;

import com.gb4w20.backingbeans.CreditCardBackingBean;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.slf4j.LoggerFactory;

/**
 * <h1>Credit Card Converter</h1>
 * <p>
 * This is a converter class that inserts the spaces in the appropriate location
 * for a credit card number
 * </p>
 *
 * @author Jasmar Badion
 */
@FacesConverter("com.gb4w20.gb4w20.jsf.validation.CreditCardConverter")
public class CreditCardConverter implements Converter, Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(CreditCardConverter.class);

    private String separator;

    public void setSeparator(String newValue) {
        separator = newValue;
    }

    /**
     * Used when input a value Accept the string and check that includes digits
     * or white space
     *
     * @param context
     * @param component
     * @param newValue
     * @return
     */
    
    public Object getAsObject(FacesContext context, UIComponent component,
            String newValue) throws ConverterException {
        StringBuilder builder = new StringBuilder(newValue);
        boolean foundInvalidCharacter = false;
        char invalidCharacter = '\0';
        int i = 0;
        while (i < builder.length() && !foundInvalidCharacter) {
            char ch = builder.charAt(i);
            if (Character.isDigit(ch)) {
                i++;
            } else if (Character.isWhitespace(ch)) {
                builder.deleteCharAt(i);
            } else {
                foundInvalidCharacter = true;
                invalidCharacter = ch;
            }
        }
        
        if (foundInvalidCharacter) {
            FacesMessage message = CreditCardMessages.getMessage(
                    "com.gb4w20.gb4w20.bundles.messages", "badCreditCardCharacter",
                    new Object[]{invalidCharacter});
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
        }

        return new CreditCardBackingBean(builder.toString());
    }

    /**
     * Used when display the value Depending on the length of the string add
     * spaces where appropriate
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    
    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        if (!(value instanceof CreditCardBackingBean)) {
            throw new ConverterException();
        }
        String v = ((CreditCardBackingBean) value).toString();
        String sep = separator;
        if (sep == null) {
            sep = " ";
        }

        int[] boundaries = null;
        int length = v.length();
        switch (length) {
            case 13:
                boundaries = new int[]{4, 7, 10};
                break;
            case 14:
                boundaries = new int[]{5, 9};
                break;
            case 15:
                boundaries = new int[]{4, 10};
                break;
            case 16:
                boundaries = new int[]{4, 8, 12};
                break;
            case 22:
                boundaries = new int[]{6, 14};
                break;
            default:
                return v;
        }
        StringBuilder result = new StringBuilder();
        int start = 0;
        for (int i = 0; i < boundaries.length; i++) {
            int end = boundaries[i];
            result.append(v.substring(start, end));
            result.append(sep);
            start = end;
        }
        result.append(v.substring(start));
        return result.toString();
    }
}
