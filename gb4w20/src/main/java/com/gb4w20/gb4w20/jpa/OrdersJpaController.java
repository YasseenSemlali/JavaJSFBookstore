
package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with the orders table in the database. 
 * 
 * @author Jeffrey Boisvert
 */
public class OrdersJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(OrdersJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Orders orders) {
        if (orders.getBookorderCollection() == null) {
            orders.setBookorderCollection(new ArrayList<Bookorder>());
        }
        try {
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = orders.getOrderId();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        try {
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Long id) {
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
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
    
}
