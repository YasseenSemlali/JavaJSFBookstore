package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Books_;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * Used to interact with the orders table in the database. 
 * 
 * @author Jeffrey Boisvert, Jean Robatto
 */
@Named
@SessionScoped
public class OrdersJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(OrdersJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Orders orders) throws BackendException {
        if (orders.getBookorderCollection() == null) {
            orders.setBookorderCollection(new ArrayList<Bookorder>());
        }
        try {
            utx.begin();
            Users userId = orders.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                orders.setUserId(userId);
            }
            Collection<Bookorder> attachedBookorderCollection = new ArrayList<Bookorder>();
            for (Bookorder bookorderCollectionBookorderToAttach : orders.getBookorderCollection()) {
                bookorderCollectionBookorderToAttach = em.getReference(bookorderCollectionBookorderToAttach.getClass(), bookorderCollectionBookorderToAttach.getBookorderId());
                attachedBookorderCollection.add(bookorderCollectionBookorderToAttach);
            }
            orders.setBookorderCollection(attachedBookorderCollection);
            em.persist(orders);
            if (userId != null) {
                userId.getOrdersCollection().add(orders);
                userId = em.merge(userId);
            }
            for (Bookorder bookorderCollectionBookorder : orders.getBookorderCollection()) {
                Orders oldOrderIdOfBookorderCollectionBookorder = bookorderCollectionBookorder.getOrderId();
                bookorderCollectionBookorder.setOrderId(orders);
                bookorderCollectionBookorder = em.merge(bookorderCollectionBookorder);
                if (oldOrderIdOfBookorderCollectionBookorder != null) {
                    oldOrderIdOfBookorderCollectionBookorder.getBookorderCollection().remove(bookorderCollectionBookorder);
                    oldOrderIdOfBookorderCollectionBookorder = em.merge(oldOrderIdOfBookorderCollectionBookorder);
                }
            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in orders controller method.");
            throw new BackendException("Error in create method in orders controller.");
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            utx.begin();
            Orders persistentOrders = em.find(Orders.class, orders.getOrderId());
            Users userIdOld = persistentOrders.getUserId();
            Users userIdNew = orders.getUserId();
            Collection<Bookorder> bookorderCollectionOld = persistentOrders.getBookorderCollection();
            Collection<Bookorder> bookorderCollectionNew = orders.getBookorderCollection();
            List<String> illegalOrphanMessages = null;
            for (Bookorder bookorderCollectionOldBookorder : bookorderCollectionOld) {
                if (!bookorderCollectionNew.contains(bookorderCollectionOldBookorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bookorder " + bookorderCollectionOldBookorder + " since its orderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                orders.setUserId(userIdNew);
            }
            Collection<Bookorder> attachedBookorderCollectionNew = new ArrayList<Bookorder>();
            for (Bookorder bookorderCollectionNewBookorderToAttach : bookorderCollectionNew) {
                bookorderCollectionNewBookorderToAttach = em.getReference(bookorderCollectionNewBookorderToAttach.getClass(), bookorderCollectionNewBookorderToAttach.getBookorderId());
                attachedBookorderCollectionNew.add(bookorderCollectionNewBookorderToAttach);
            }
            bookorderCollectionNew = attachedBookorderCollectionNew;
            orders.setBookorderCollection(bookorderCollectionNew);
            orders = em.merge(orders);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getOrdersCollection().remove(orders);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getOrdersCollection().add(orders);
                userIdNew = em.merge(userIdNew);
            }
            for (Bookorder bookorderCollectionNewBookorder : bookorderCollectionNew) {
                if (!bookorderCollectionOld.contains(bookorderCollectionNewBookorder)) {
                    Orders oldOrderIdOfBookorderCollectionNewBookorder = bookorderCollectionNewBookorder.getOrderId();
                    bookorderCollectionNewBookorder.setOrderId(orders);
                    bookorderCollectionNewBookorder = em.merge(bookorderCollectionNewBookorder);
                    if (oldOrderIdOfBookorderCollectionNewBookorder != null && !oldOrderIdOfBookorderCollectionNewBookorder.equals(orders)) {
                        oldOrderIdOfBookorderCollectionNewBookorder.getBookorderCollection().remove(bookorderCollectionNewBookorder);
                        oldOrderIdOfBookorderCollectionNewBookorder = em.merge(oldOrderIdOfBookorderCollectionNewBookorder);
                    }
                }
            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with edit in orders controller method.");
            throw new BackendException("Error in edit method in orders controller.");
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        try {
            utx.begin();
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bookorder> bookorderCollectionOrphanCheck = orders.getBookorderCollection();
            for (Bookorder bookorderCollectionOrphanCheckBookorder : bookorderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Bookorder " + bookorderCollectionOrphanCheckBookorder + " in its bookorderCollection field has a non-nullable orderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users userId = orders.getUserId();
            if (userId != null) {
                userId.getOrdersCollection().remove(orders);
                userId = em.merge(userId);
            }
            em.remove(orders);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with delete in orders controller method.");
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    }

    public Orders findOrders(Long id) {
            return em.find(Orders.class, id);
    }

    public int getOrdersCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Used to get the total sales between a start and end date
     * @param startDate of the report
     * @param endDate of the report. 
     * @return total sales.
     * @author Jeffrey Boisvert 
     */
    public double getTotalSales(String startDate, String endDate){
        LOG.info("Looking for total sales for orders between " + startDate + " and " + endDate);
        
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        Root<Orders> orders = cq.from(Orders.class);
        Join<Orders, Bookorder> bookorder = orders.join("bookorderCollection", JoinType.INNER);

        cq.select(em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .where(
                     cb.between(orders.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                );

        Query query = em.createQuery(cq);
        return query.getSingleResult() != null ? ((BigDecimal) query.getSingleResult()).doubleValue() : 0.00;
    }
    
    /**
     * Used to get all the books purchased in a date range and their totals. 
     * @param startDate in format YYYY-MM-DD
     * @param endDate in format YYYY-MM-DD
     * @return list of all the items and their totals. 
     * @author Jeffrey Boisvert
     */
    public List<NameAndNumberBean> getPurchasedBooks(String startDate, String endDate){
        
        LOG.info("Looking for books ordered between " + startDate + " and " + endDate);
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(NameAndNumberBean.class);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        Root<Books> book = cq.from(Books.class);
        Join<Books, Bookorder> bookorder = book.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Orders> order = bookorder.join("orderId", JoinType.INNER);
        
        cq.multiselect(book.get(Books_.title), em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .groupBy(book.get(Books_.title))
                .where(
                   cb.between(order.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                )
                .orderBy(cb.asc((book.get(Books_.title))));

        Query query = em.createQuery(cq);
        return query.getResultList();
    }
    
}
