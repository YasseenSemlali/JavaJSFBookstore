
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the clients report. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@RequestScoped
public class ClientReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ClientReportBackingBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    private List<Users> users; 

    public List<Users> getUsers() {
        if (users == null){
             this.users = usersJpaController.findUsersEntities();
        }
        return this.users;
    }
    
}
