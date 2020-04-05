
package com.gb4w20.gb4w20.backingbeans;

import static com.gb4w20.gb4w20.backingbeans.LastViewedGenreBean.RECENT_GENRE_COOKIE_NAME;
import com.gb4w20.gb4w20.entities.Genres;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

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
    
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(LocaleBackingBean.class);
    
    public final static String LOCALE_VALUE = "userLocale";
    
    private final String NO_LANGUAGE_SET_IN_COOKIE = "NO COOKIE SET";  
    
    private Locale locale;

    /**
     * Used to get the current local 
     * @author Jeffrey Boisvert
     */
    @PostConstruct
    public void init() {
        
        String userPreferedLanguage = this.getUsersLanguagePreference();
        
        if(userPreferedLanguage.equals(NO_LANGUAGE_SET_IN_COOKIE)){
           locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();   
        } else {
            setLanguage(userPreferedLanguage, "CA");
        }
        
    }
    
    /**
     * Used to get a copy of the locale
     * @return current locale
     * @author Jeffrey Boisvert
     */
    public Locale getLocale() {
        if(locale==null){
            return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
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
        locale = new Locale(language, country);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        LOG.info("Local is now set to " + locale.toString());
        
        //Save the user's choice in the cookie
        setUserLanguagePreferenceCookie();
    }
    
    /**
     * Used to validate if the locale is in french or not
     * @return true if set to french, false otherwise
     * @author Jeffrey Boisvert
     */
    public boolean isSetToFrench(){
        return this.getLanguage().equals("fr");
    }

    /**
     * Used to set the cookie of the user's language preference
     * @author Jeffrey Boisvert
     */
    public void setUserLanguagePreferenceCookie() {
        LOG.info("Setting mose recent genre");
    
        FacesContext context = FacesContext.getCurrentInstance();
        
        LOG.info("Setting language preference to " + this.getLanguage());
        Cookie cookie = new Cookie(LOCALE_VALUE, this.getLanguage());
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);

        ((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(cookie);
    }
    
    /**
     * Used to get the user's language preference if 
     * the cookie was stored and is found
     * @return a String of the language saved if not default value indicating no cookie set
     * @author Jeffrey Boisvert
     */
    private String getUsersLanguagePreference() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        
        Cookie cookie = (Cookie) requestCookieMap.get(LOCALE_VALUE);
        
        if(cookie == null) {
            return NO_LANGUAGE_SET_IN_COOKIE;
        }
        
        return cookie.getValue();
    }
    
}
