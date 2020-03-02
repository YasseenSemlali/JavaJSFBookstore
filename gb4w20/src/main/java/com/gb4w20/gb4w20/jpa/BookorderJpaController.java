
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Bookorder_;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Users_;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with the book order table in the database. 
 * 
 * @author Jeffrey Boisvert, Jean Robatto
 */
@Named
@SessionScoped
public class BookorderJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(BookorderJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Bookorder bookorder) {
        try {
            utx.begin();
            Books isbn = bookorder.getIsbn();
            if (isbn != null) {
                isbn = em.getReference(isbn.getClass(), isbn.getIsbn());
                bookorder.setIsbn(isbn);
            }
            Orders orderId = bookorder.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getOrderId());
                bookorder.setOrderId(orderId);
            }
            em.persist(bookorder);
            if (isbn != null) {
                isbn.getBookorderCollection().add(bookorder);
                isbn = em.merge(isbn);
            }
            if (orderId != null) {
                orderId.getBookorderCollection().add(bookorder);
                orderId = em.merge(orderId);
            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in bookerde controller method.");
        }
    }

    public void edit(Bookorder bookorder) throws NonexistentEntityException, Exception {
        try {
            utx.begin();
            Bookorder persistentBookorder = em.find(Bookorder.class, bookorder.getBookorderId());
            Books isbnOld = persistentBookorder.getIsbn();
            Books isbnNew = bookorder.getIsbn();
            Orders orderIdOld = persistentBookorder.getOrderId();
            Orders orderIdNew = bookorder.getOrderId();
            if (isbnNew != null) {
                isbnNew = em.getReference(isbnNew.getClass(), isbnNew.getIsbn());
                bookorder.setIsbn(isbnNew);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getOrderId());
                bookorder.setOrderId(orderIdNew);
            }
            bookorder = em.merge(bookorder);
            if (isbnOld != null && !isbnOld.equals(isbnNew)) {
                isbnOld.getBookorderCollection().remove(bookorder);
                isbnOld = em.merge(isbnOld);
            }
            if (isbnNew != null && !isbnNew.equals(isbnOld)) {
                isbnNew.getBookorderCollection().add(bookorder);
                isbnNew = em.merge(isbnNew);
            }
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getBookorderCollection().remove(bookorder);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getBookorderCollection().add(bookorder);
                orderIdNew = em.merge(orderIdNew);
            }
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long bookorderId = bookorder.getBookorderId();
                if (findBookorder(bookorderId) == null) {
                    throw new NonexistentEntityException("The bookorder with id " + bookorderId + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {

            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        try {
            em.getTransaction().begin();
            Bookorder bookorder;
            try {
                bookorder = em.getReference(Bookorder.class, id);
                bookorder.getBookorderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bookorder with id " + id + " no longer exists.", enfe);
            }
            Books isbn = bookorder.getIsbn();
            if (isbn != null) {
                isbn.getBookorderCollection().remove(bookorder);
                isbn = em.merge(isbn);
            }
            Orders orderId = bookorder.getOrderId();
            if (orderId != null) {
                orderId.getBookorderCollection().remove(bookorder);
                orderId = em.merge(orderId);
            }
            em.remove(bookorder);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bookorder> findBookorderEntities() {
        return findBookorderEntities(true, -1, -1);
    }

    public List<Bookorder> findBookorderEntities(int maxResults, int firstResult) {
        return findBookorderEntities(false, maxResults, firstResult);
    }

    private List<Bookorder> findBookorderEntities(boolean all, int maxResults, int firstResult) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bookorder.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    }

    public Bookorder findBookorder(Long id) {
            return em.find(Bookorder.class, id);
    }

    public int getBookorderCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bookorder> rt = cq.from(Bookorder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Returns the total sales for a specific book
     * 
     * @author Jean Robatto
     * @param isbn
     * @return 
     */
    public BigDecimal getTotalSalesForBook(Books isbn) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);

            Root<Bookorder> order = cq.from(Bookorder.class);
            
            cq.select(
                cb.sum(order.get(Bookorder_.amountPaidPretax)))
                .groupBy(order.get(Bookorder_.isbn))
                .where(cb.equal(order.get(Bookorder_.isbn), isbn), cb.equal(order.get(Bookorder_.enabled), Boolean.TRUE));
                
            Query query = em.createQuery(cq);
            
            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }
    
    /**
     * Returns the total sales for a specific order
     * 
     * @author Jean Robatto
     * @param inputOrder
     * @return 
     */
    public BigDecimal getTotalSalesForOrderPreTax(Orders inputOrder) {
        try {
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);

            Root<Bookorder> order = cq.from(Bookorder.class);
            
            cq.select(
                cb.sum(order.get(Bookorder_.amountPaidPretax)))
                .groupBy(order.get(Bookorder_.orderId))
                .where(cb.equal(order.get(Bookorder_.orderId), inputOrder), cb.equal(order.get(Bookorder_.enabled), Boolean.TRUE));
                
            Query query = em.createQuery(cq);
            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }
    
    /**
     * Returns the total HST tax for a specific order
     * 
     * @author Jean Robatto
     * @param inputOrder
     * @return 
     */
    public BigDecimal getHSTForOrder(Orders inputOrder) {
        try {
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);

            Root<Bookorder> order = cq.from(Bookorder.class);
            
            cq.select(
                cb.sum(order.get(Bookorder_.hstTax)))
                .groupBy(order.get(Bookorder_.orderId))
                .where(cb.equal(order.get(Bookorder_.orderId), inputOrder), cb.equal(order.get(Bookorder_.enabled), Boolean.TRUE));
                
            Query query = em.createQuery(cq);
            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }
    
    /**
     * Returns the total GST tax for a specific order
     * 
     * @author Jean Robatto
     * @param inputOrder
     * @return 
     */
    public BigDecimal getGSTForOrder(Orders inputOrder) {
        try {
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);

            Root<Bookorder> order = cq.from(Bookorder.class);
            
            cq.select(
                cb.sum(order.get(Bookorder_.gstTax)))
                .groupBy(order.get(Bookorder_.orderId))
                .where(cb.equal(order.get(Bookorder_.orderId), inputOrder), cb.equal(order.get(Bookorder_.enabled), Boolean.TRUE));
                
            Query query = em.createQuery(cq);
            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }
    
    /**
     * Returns the total PST tax for a specific order
     * 
     * @author Jean Robatto
     * @param inputOrder
     * @return 
     */
    public BigDecimal getPSTForOrder(Orders inputOrder) {
        try {
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);

            Root<Bookorder> order = cq.from(Bookorder.class);
            
            cq.select(
                cb.sum(order.get(Bookorder_.pstTax)))
                .groupBy(order.get(Bookorder_.orderId))
                .where(cb.equal(order.get(Bookorder_.orderId), inputOrder), cb.equal(order.get(Bookorder_.enabled), Boolean.TRUE));
                
            Query query = em.createQuery(cq);
            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }
    
    
    
    
}
