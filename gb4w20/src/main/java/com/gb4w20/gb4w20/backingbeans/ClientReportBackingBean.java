
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is simply used as a backing bean for the clients report. 
 * 
 * @author Jeffrey Boisvert
 */
public class ClientReportBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(PersonBackingBean.class);

    @Inject
    private UsersJpaController usersJpaController;
    
    private final List<Users> users; 
        
    public ClientReportBackingBean() {
        this.users = usersJpaController.findUsersEntities();
    }

    public List<Users> getUsers() {
        return this.users;
    }
    
}
