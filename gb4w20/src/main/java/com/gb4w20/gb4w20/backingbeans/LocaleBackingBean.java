
package com.gb4w20.gb4w20.backingbeans;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Used to hold the locale of the current session. 
 * This only applies logic of changing the locale not generating messages. 
 * This is heavily based on Ken Fogel's project JSFi18nChanger
 * found here: https://gitlab.com/omniprof/JSFi18nChanger.git
 * 
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class LocaleBackingBean implements Serializable{
    
    private Locale locale;

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    /**
     * Used to get a copy of the locale
     * @return current locale
     * @author Jeffrey Boisvert
     */
    public Locale getLocale() {
        return locale;
    }
    
    /**
     * Used to get the language associated to the locale
     * @return a String of the current language
     * @author Jeffrey Boisvert
     */
    public String getLanguage() {
        return locale.getLanguage();
    }
    
    /**
     * Used to set the language of the locale. 
     * @param language being set ex: fr
     * @param country of the language being set ex: CA
     */
    public void setLanguage(String language, String country) {
        locale = new Locale(a, b);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    
}
