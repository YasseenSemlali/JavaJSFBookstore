package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yasseen
 */
@Named
@SessionScoped
public class BookActionBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(BookActionBean.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;
    
    public BookActionBean() {
    }
    
    public void create(Books book) throws RollbackFailureException {
        try {
            utx.begin();
            em.persist(book);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                utx.rollback();
                LOG.error("Rollback");
            } catch (IllegalStateException | SecurityException | SystemException re) {
                LOG.error("Rollback2");

                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
        }
    }

    public void edit(Books book) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            book = em.merge(book);
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long isbn = book.getIsbn();
                if (findBook(isbn) == null) {
                    throw new NonexistentEntityException("The book with isbn " + isbn + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Long isbn) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Books book;
            try {
                book = em.getReference(Books.class, isbn);
                book.getIsbn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The book with isbn " + isbn + " no longer exists.", enfe);
            }
            em.remove(book);
            utx.commit();
        } catch (NonexistentEntityException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }
    
    public List<Books> getAllBooks(){
        return getAllBooks(-1, -1);
    }
    
    public List<Books> getAllBooks(int maxResults) {
        return getAllBooks(maxResults, -1);
    }

    public List<Books> getAllBooks(int maxResults, int firstResult) {
        LOG.info("getting all books");
        
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Books.class));
        Query q = em.createQuery(cq);
        if (firstResult != -1) {
            q.setMaxResults(maxResults);
        }
        if (maxResults != -1) {
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Books findBook(Long isbn) {
        return em.find(Books.class, isbn);
    }

    public int getFishCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Books> rt = cq.from(Books.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
