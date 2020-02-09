
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.entities.Users_;
import java.io.Serializable;
import com.gb4w20.gb4w20.exceptions.*;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.MapAttribute;
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

    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager entityManager;

    /**
     * Used to create a user in the database. 
     * 
     * @param user given to be create in the user's table. 
     * @throws RollbackFailureException when a rollback error occurs. 
     */
    public void create(Users user) throws RollbackFailureException {
        
        try {
            userTransaction.begin();
            LOG.info("Adding user " + user);
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
    
    /**
     * Used to edit a user in the database. 
     * @param user to be edited
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void edit(Users user) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            userTransaction.begin();
            user = entityManager.merge(user);
            userTransaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    /**
     * Used to find the user object (record) associated to the id
     * @param id
     * @return the found user object 
     */
    public Users findUser(Long id) {
        return entityManager.find(Users.class, id);
    }
    
    /**
     * Used to return a list of all the users who match the given firs name
     * @param firstName of the user
     * @return collection of users who have that name or similar
     */
    public List<Users> findUser(String firstName){

          CriteriaBuilder cb = entityManager.getCriteriaBuilder();
          CriteriaQuery<Users> cq = cb.createQuery(Users.class);
          Root<Users> user = cq.from(Users.class);
          
          cq.select(user);
          cq.where(cb.like(user.get(Users_.firstName), "%" + firstName + "%"));
          cq.orderBy(cb.asc(user.get((Users_.firstName))));
          
          TypedQuery<Users> query = entityManager.createQuery(cq);
          return query.getResultList();
          
    }

}
