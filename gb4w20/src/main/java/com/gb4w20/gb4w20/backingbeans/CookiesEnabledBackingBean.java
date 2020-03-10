/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.backingbeans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/** Checks if cookies are enabled or disabled
 *
 * @author Yasseen
 */
@Named
@RequestScoped
public class CookiesEnabledBackingBean {
    private boolean cookiesEnabled;

    public boolean isCookiesEnabled() {
        return cookiesEnabled;
    }

    public void setCookiesEnabled(boolean cookiesEnabled) {
        this.cookiesEnabled = cookiesEnabled;
    }
    
    
    
}
