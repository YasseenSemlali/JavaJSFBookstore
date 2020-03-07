
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Books_;
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
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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
        try {
            if (users.getReviewsCollection() == null) {
                users.setReviewsCollection(new ArrayList<Reviews>());
            }
            if (users.getOrdersCollection() == null) {
                users.setOrdersCollection(new ArrayList<Orders>());
            }
            
            utx.begin();
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
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in user controller method.");
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
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with edit in users controller method.", ex);
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
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with delete in users controller method.", ex);
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
     * @author Jeffrey Boisvert
     */
    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {

            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            
            Root<Users> users = cq.from(Users.class);
            cq.select(users).orderBy(cb.asc((users.get(Users_.firstName))));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
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
     * Used to find a user with a given email. 
     * @param email
     * @return user object of that id. 
     * @author Jeffrey Boisvert
     */
    public Users findUsers(String email) throws NoResultException, NonUniqueResultException{
        
          CriteriaBuilder cb = em.getCriteriaBuilder();
          CriteriaQuery<Users> cq = cb.createQuery(Users.class);
          Root<Users> user = cq.from(Users.class);
          
          cq.select(user);
          cq.where(cb.and(
                  cb.equal(user.get(Users_.email), email)
            )
          );
          
          TypedQuery<Users> query = em.createQuery(cq);
          return query.getSingleResult();
        
    }
    
    /**
     * Used to find the user entity that have the email and password. 
     * @param email of the user
     * @param password of the user
     * @throws NoResultException if no result is found
     * @throws NonUniqueResultException if more than one result is returned
     * @return user entity with matching email and password
     */
    public Users findUserByEmailAndPassword(String email, String password) throws NoResultException, NonUniqueResultException{
          
          CriteriaBuilder cb = em.getCriteriaBuilder();
          CriteriaQuery<Users> cq = cb.createQuery(Users.class);
          Root<Users> user = cq.from(Users.class);
          
          cq.select(user);
          cq.where(cb.and(
                  cb.equal(user.get(Users_.email), email),
                  cb.equal(user.get(Users_.password), password)
            )
          );
          
          TypedQuery<Users> query = em.createQuery(cq);
          return query.getSingleResult();
    }
    
    /**
     * Used to get the total sales of a client between a given start 
     * and end date. 
     * @param id of the user
     * @param startDate in format YYYY-MM-DD
     * @param endDate in format YYYY-MM-DD
     * @return total of sales
     * @author Jeffrey Boisvert
     */
    public double getUsersTotalSales(Long id, String startDate, String endDate){

        LOG.info("Looking for total sales for user with id " + id);
        
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        Root<Orders> orders = cq.from(Orders.class);
        Join<Orders, Bookorder> bookorder = orders.join("bookorderCollection", JoinType.INNER);
        Join<Orders, Users> user = orders.join("userId", JoinType.INNER);

        cq.select(em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .where(cb.and(
                        cb.equal(user.get("userId"), id),
                        cb.between(orders.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                ));

        Query query = em.createQuery(cq);
        return query.getSingleResult() != null ? ((BigDecimal) query.getSingleResult()).doubleValue() : 0.00;
        
    }
    
    /**
     * Used to get all the books purchased by a user and the totals. 
     * This includes if a user ordered an item more than once in theory. 
     * 
     * @param id of the user
     * @param startDate in format YYYY-MM-DD
     * @param endDate in format YYYY-MM-DD
     * @return list of all the items and their totals. 
     * @author Jeffrey Boisvert
     */
    public List<NameAndNumberBean> getUserPurchasedBooks(Long id, String startDate, String endDate){
        
        LOG.info("Looking for books bought by user with id " + id);
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(NameAndNumberBean.class);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        Root<Books> book = cq.from(Books.class);
        Join<Books, Bookorder> bookorder = book.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Orders> order = bookorder.join("orderId", JoinType.INNER);
        Join<Orders, Users> user = order.join("userId", JoinType.INNER);
        
        cq.multiselect(book.get(Books_.title), em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .groupBy(book.get(Books_.title))
                .where(cb.and(
                        cb.equal(user.get("userId"), id),
                        cb.between(order.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                ))
                .orderBy(cb.asc(em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax"))));

        Query query = em.createQuery(cq);
        return query.getResultList();
    }
    
     /**
     * Used to return a list of all the users who match the given firs name
     * @param firstName of the user
     * @return collection of users who have that name or similar
     * @author Jeffrey Boisvert
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
     * @author Jeffrey Boisvert
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
    
    /**
     * Used to the top clients (based on sales) within the 
     * given start and end dates. 
     * @param startDate of the report 
     * @param endDate of the report
     * @return the report of top sellers of the names of the users and their total sales
     * @author Jeffrey Boisvert
     */
    public List<NameAndNumberBean> findTopUsersBySales(String startDate, String endDate){
        
        LOG.info("Looking for top users by sales between " + startDate + " and " + endDate);
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(NameAndNumberBean.class);
        CriteriaBuilder cb = em.getCriteriaBuilder();

        Root<Users> bookorder = cq.from(Bookorder.class);
        Join<Bookorder, Orders> order = bookorder.join("orderId", JoinType.INNER);
        Join<Orders, Users> user = order.join("userId", JoinType.INNER);
        
        cq.multiselect(
                cb.concat(
                        cb.concat(user.get(Users_.firstName), " "),
                            user.get(Users_.lastName)
                    ), 
                cb.sum(bookorder.get("amountPaidPretax")))
                .groupBy(user.get(Users_.userId))
                .where(
                        cb.between(order.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                )
                .orderBy(cb.desc(cb.sum(bookorder.get("amountPaidPretax"))));

        Query query = em.createQuery(cq);
        return query.getResultList();
    }
    
}
