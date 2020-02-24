
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to hold the data about the user during the session. 
 * 
 * @author Jeffrey Boisvert
 */
@Named("session")
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
     * Used to login the user. 
     * @param 
     * @author Jeffrey Boisvert
     */
    public boolean loginUser(String email, String password){
        
    }
    
    
}
