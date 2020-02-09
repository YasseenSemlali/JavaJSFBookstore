
package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import com.gb4w20.gb4w20.exceptions.*;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.registry.infomodel.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used as a DAO for the users table. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class UserActionBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(UserActionBean.class);
/*
    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager entityManager;
    
    public UserActionBean(){
        //Left empty to comply with frameworks. 
    }
    */
    /**
     * Used to create a user in the database. 
     * 
     * @param user given to be create in the user's table. 
     * @throws RollbackFailureException when a rollback error occurs. 
     */
    /*
    public void create(User user) throws RollbackFailureException {
        
        try {
            userTransaction.begin();
            entityManager.persist(user);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            
            try {
                userTransaction.rollback();
                LOG.error("UserTransaction object rollback.");
            } catch (IllegalStateException | SecurityException | SystemException re) {
                LOG.error("UserTransaction object rollback failed");
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            
        }
    }
    */
}
