
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
public class UserSessionBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(UserSessionBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    //Used to hold the user entity if logged in
    private Users user; 
    
    //Default is false
    private boolean hasRespondedToSurvey;
    
    //PostConstruct -> after indepecndy inject 
    
    /**
     * Getter for user
     * @return 
     * @author Jasmar
     */
    public Users getUser(){
        return this.user;
    }
    
    /**
     * Setter for user
     * @param user 
     * @author Jasmar 
     */
    public void setUser(Users user){
        this.user = user;
    }
    
    /**
     * Getting the user id of user logged in
     * @return 
     * @Jasmar
     */
    public long getLoggedInUserId(){
        return this.user.getUserId(); 
    }

    /**
     * Checks if the user has responded to a survey this session
     * @return true if has responded to a survey, false otherwise
     * @author Yasseen Semlali
     */
    public boolean isHasRespondedToSurvey() {
        return hasRespondedToSurvey;
    }

    
    public void setHasRespondedToSurvey(boolean hasRespondedToSurvey) {
        this.hasRespondedToSurvey = hasRespondedToSurvey;
    }
    
    /**
     * Checks whether the user is logged in or not.
     * @return true if logged in false if not
     * @author Jeffrey Boisvert
     */
    public boolean isLoggedIn(){
        LOG.info("User is " + (this.user != null));
        return this.user != null; 
    }
    
    /**
     * Used to get the logged in user's name. 
     * @return the name of the logged in user
     * @author Jeffrey Boisvert
     */
    public String getLoggedInUserName(){
        if(this.user == null){
            return ""; 
        }
        return this.user.getFirstName(); 
    }
    
    /**
     * Method to check whether the user is a manager or not
     * @return true if logged in manager and false otherwise
     * @author Jeffrey Boisvert
     */
    public boolean isLoggedInManager(){
        LOG.info("Logged in manager " + (this.user != null && this.user.getIsManager()));
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
     * @return Where the app redirects to
     * @author Jeffrey Boisvert
     */
    public String logout(){
        LOG.info("Logging out user");
        this.user = null; 
        LOG.info("User is null");
        clearSessionState();
        return "index.xhtml"; 
    }
    
    /**
     * Used to clear cart and any other values associated to login state. 
     * @author Jeffrey Boisvert
     */
    private void clearSessionState() {
        LOG.info("Currently clearing session state");
    }
    
    
}
