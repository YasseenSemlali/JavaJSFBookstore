
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to hold the data about the user during the session. 
 * 
 * @author Jeffrey Boisvert
 */
@Named("userSession")
@RequestScoped
public class UserSessionBean {

    private final static Logger LOG = LoggerFactory.getLogger(UserSessionBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    //Used to hold the user entity if logged in
    private Users user; 
    
    /**
     * Checks whether the user is logged in or not.
     * @return true if logged in false if not
     * @author Jeffrey Boisvert
     */
    public boolean isLoggedIn(){
        return this.user == null; 
    }
    
    /**
     * Method to check whether the user is a manager or not
     * @return true if logged in manager and false otherwise
     * @author Jeffrey Boisvert
     */
    public boolean isLoggedInManager(){
        return this.user != null && this.user.getIsManager(); 
    }
    
    /**
     * Used to login the user. 
     * @param email passed to login
     * @param password passed to login
     * @return true if login was successful, false otherwise
     * @author Jeffrey Boisvert
     */
    public boolean loginUser(String email, String password){
        
        //Default is false
        boolean result = false; 
        
        try {
            this.user = this.usersJpaController.findUserByEmailAndPassword(email, password);
            result = true; 
        }
        //No user with that username or password was found
        catch(NoResultException e){
            LOG.trace("Login attempt with email " + email + " and password " + password + " yielded no results");
        }
        catch(NonUniqueResultException e){
            LOG.error("There is more than one user with email " + email + " and password " + password + "!");
        }
        
        return result;
        
    }
    
    /**
     * Used to logout the user from the session
     * @author Jeffrey Boisvert
     */
    public void logout(){
        this.user = null; 
        clearSessionState();
    }
    
    /**
     * Used to clear cart and any other values associated to login state. 
     * @author Jeffrey Boisvert
     */
    private void clearSessionState() {
        LOG.info("Currently clearing session state");
    }
    
    
}
