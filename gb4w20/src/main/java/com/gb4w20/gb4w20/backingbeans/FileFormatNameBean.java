package com.gb4w20.gb4w20.backingbeans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/** 
 * Gets the i18n name for a file format
 * @author Yasseen
 */
@Named
@SessionScoped
public class FileFormatNameBean implements Serializable{
    /** Returns the name of a file format depending on the language
     * @author Yasseen Semlali
     */
    public String getInternationalizedFormatName(String format) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        switch(format) {
            case "mobi":
                return context.getApplication().evaluateExpressionGet(context, "#{msgs.file_format_mobi}", String.class);
            case "pdf":
                return context.getApplication().evaluateExpressionGet(context, "#{msgs.file_format_pdf}", String.class);
            case "epub":
                return context.getApplication().evaluateExpressionGet(context, "#{msgs.file_format_epub}", String.class);
            default:
                return context.getApplication().evaluateExpressionGet(context, "#{msgs.file_format_unknown}", String.class);
            
        }
    }
}
