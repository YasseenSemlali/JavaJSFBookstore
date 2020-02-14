
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Reviews;
import java.util.ArrayList;
import java.util.Collection;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.entities.Users_;
import com.gb4w20.gb4w20.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.exceptions.NonexistentEntityException;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used as a data access object between user entities.
 * 
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class UsersJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(UsersJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;
    
    /**
     * Used to create an entry in the users table with values of the given user. 
     * @param users 
     */
    public void create(Users users) {
        if (users.getReviewsCollection() == null) {
            users.setReviewsCollection(new ArrayList<Reviews>());
        }
        if (users.getOrdersCollection() == null) {
            users.setOrdersCollection(new ArrayList<Orders>());
        }
        try {
            em.getTransaction().begin();
            Collection<Reviews> attachedReviewsCollection = new ArrayList<Reviews>();
            for (Reviews reviewsCollectionReviewsToAttach : users.getReviewsCollection()) {
                reviewsCollectionReviewsToAttach = em.getReference(reviewsCollectionReviewsToAttach.getClass(), reviewsCollectionReviewsToAttach.getReviewId());
                attachedReviewsCollection.add(reviewsCollectionReviewsToAttach);
            }
            users.setReviewsCollection(attachedReviewsCollection);
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : users.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getOrderId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            users.setOrdersCollection(attachedOrdersCollection);
            em.persist(users);
            for (Reviews reviewsCollectionReviews : users.getReviewsCollection()) {
                Users oldUserIdOfReviewsCollectionReviews = reviewsCollectionReviews.getUserId();
                reviewsCollectionReviews.setUserId(users);
                reviewsCollectionReviews = em.merge(reviewsCollectionReviews);
                if (oldUserIdOfReviewsCollectionReviews != null) {
                    oldUserIdOfReviewsCollectionReviews.getReviewsCollection().remove(reviewsCollectionReviews);
                    oldUserIdOfReviewsCollectionReviews = em.merge(oldUserIdOfReviewsCollectionReviews);
                }
            }
            for (Orders ordersCollectionOrders : users.getOrdersCollection()) {
                Users oldUserIdOfOrdersCollectionOrders = ordersCollectionOrders.getUserId();
                ordersCollectionOrders.setUserId(users);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldUserIdOfOrdersCollectionOrders != null) {
                    oldUserIdOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldUserIdOfOrdersCollectionOrders = em.merge(oldUserIdOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Used to update a record for a user in the database. 
     * @param users to be edited 
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception 
     */
    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getUserId());
            Collection<Reviews> reviewsCollectionOld = persistentUsers.getReviewsCollection();
            Collection<Reviews> reviewsCollectionNew = users.getReviewsCollection();
            Collection<Orders> ordersCollectionOld = persistentUsers.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = users.getOrdersCollection();
            List<String> illegalOrphanMessages = null;
            for (Reviews reviewsCollectionOldReviews : reviewsCollectionOld) {
                if (!reviewsCollectionNew.contains(reviewsCollectionOldReviews)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reviews " + reviewsCollectionOldReviews + " since its userId field is not nullable.");
                }
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Reviews> attachedReviewsCollectionNew = new ArrayList<Reviews>();
            for (Reviews reviewsCollectionNewReviewsToAttach : reviewsCollectionNew) {
                reviewsCollectionNewReviewsToAttach = em.getReference(reviewsCollectionNewReviewsToAttach.getClass(), reviewsCollectionNewReviewsToAttach.getReviewId());
                attachedReviewsCollectionNew.add(reviewsCollectionNewReviewsToAttach);
            }
            reviewsCollectionNew = attachedReviewsCollectionNew;
            users.setReviewsCollection(reviewsCollectionNew);
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getOrderId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            users.setOrdersCollection(ordersCollectionNew);
            users = em.merge(users);
            for (Reviews reviewsCollectionNewReviews : reviewsCollectionNew) {
                if (!reviewsCollectionOld.contains(reviewsCollectionNewReviews)) {
                    Users oldUserIdOfReviewsCollectionNewReviews = reviewsCollectionNewReviews.getUserId();
                    reviewsCollectionNewReviews.setUserId(users);
                    reviewsCollectionNewReviews = em.merge(reviewsCollectionNewReviews);
                    if (oldUserIdOfReviewsCollectionNewReviews != null && !oldUserIdOfReviewsCollectionNewReviews.equals(users)) {
                        oldUserIdOfReviewsCollectionNewReviews.getReviewsCollection().remove(reviewsCollectionNewReviews);
                        oldUserIdOfReviewsCollectionNewReviews = em.merge(oldUserIdOfReviewsCollectionNewReviews);
                    }
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Users oldUserIdOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getUserId();
                    ordersCollectionNewOrders.setUserId(users);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldUserIdOfOrdersCollectionNewOrders != null && !oldUserIdOfOrdersCollectionNewOrders.equals(users)) {
                        oldUserIdOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldUserIdOfOrdersCollectionNewOrders = em.merge(oldUserIdOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = users.getUserId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Used to delete a user form the database if needed. 
     * @param id of the user to delete. 
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException 
     */
    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        try {
            em.getTransaction().begin();
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reviews> reviewsCollectionOrphanCheck = users.getReviewsCollection();
            for (Reviews reviewsCollectionOrphanCheckReviews : reviewsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Reviews " + reviewsCollectionOrphanCheckReviews + " in its reviewsCollection field has a non-nullable userId field.");
            }
            Collection<Orders> ordersCollectionOrphanCheck = users.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Returns all the user entities. 
     * @return a list of all users in database
     */
    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    /**
     * Used to find users. 
     * @param maxResults max number of users to return
     * @param firstResult which record should be first looked for. 
     * @return 
     */
    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }
    
    /**
     * Used to find users. 
     * @param all users if true false otherwise
     * @param maxResults max number of users to return
     * @param firstResult which record should be first looked for. 
     * @return 
     */
    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Used to find a user with a given id. 
     * @param id
     * @return user object of that id. 
     */
    public Users findUsers(Long id) {
        
        return em.find(Users.class, id);
        
    }
    
    /**
     * Used to get the total sales of a client between a given start 
     * and end date. 
     * @param id of the user
     * @param startDate in format YYYY-MM-DD
     * @param endDate in format YYYY-MM-DD
     * @return total of sales
     */
    public double getUsersTotalSales(Long id, String startDate, String endDate){

        LOG.info("Looking for user with id " + id);
        
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        Root<Orders> orders = cq.from(Orders.class);
        Join<Orders, Bookorder> bookorder = orders.join("bookorderCollection", JoinType.INNER);
        Join<Orders, Users> user = orders.join("userId", JoinType.INNER);
        
        //TODO filter by date
        cq.select(em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .where(cb.equal(user.get("userId"), id));

        Query query = em.createQuery(cq);
        return ((BigDecimal) query.getSingleResult()).doubleValue();
        
    }
    
     /**
     * Used to return a list of all the users who match the given firs name
     * @param firstName of the user
     * @return collection of users who have that name or similar
     */
    public List<Users> findUsersByFirstName(String firstName){

          CriteriaBuilder cb = em.getCriteriaBuilder();
          CriteriaQuery<Users> cq = cb.createQuery(Users.class);
          Root<Users> user = cq.from(Users.class);
          
          cq.select(user);
          cq.where(cb.like(user.get(Users_.firstName), "%" + firstName + "%"));
          cq.orderBy(cb.asc(user.get((Users_.firstName))));
          
          TypedQuery<Users> query = em.createQuery(cq);
          return query.getResultList();
          
    }
    
    /**
     * Used to return a list of all the users who match the given last name
     * @param lastName of the user
     * @return collection of users who have that name or similar
     */
    public List<Users> findUsersByLastName(String lastName){

          CriteriaBuilder cb = em.getCriteriaBuilder();
          CriteriaQuery<Users> cq = cb.createQuery(Users.class);
          Root<Users> user = cq.from(Users.class);
          
          cq.select(user);
          cq.where(cb.like(user.get(Users_.lastName), "%" + lastName + "%"));
          cq.orderBy(cb.asc(user.get((Users_.lastName))));
          
          TypedQuery<Users> query = em.createQuery(cq);
          return query.getResultList();
          
    }
    
}
