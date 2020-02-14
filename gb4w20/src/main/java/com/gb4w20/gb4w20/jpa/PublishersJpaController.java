
package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Publishers;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with publishers in the database. 
 * 
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class PublishersJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(PublishersJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Publishers publishers) {
        if (publishers.getBooksCollection() == null) {
            publishers.setBooksCollection(new ArrayList<Books>());
        }
        try {
            em.getTransaction().begin();
            Collection<Books> attachedBooksCollection = new ArrayList<Books>();
            for (Books booksCollectionBooksToAttach : publishers.getBooksCollection()) {
                booksCollectionBooksToAttach = em.getReference(booksCollectionBooksToAttach.getClass(), booksCollectionBooksToAttach.getIsbn());
                attachedBooksCollection.add(booksCollectionBooksToAttach);
            }
            publishers.setBooksCollection(attachedBooksCollection);
            em.persist(publishers);
            for (Books booksCollectionBooks : publishers.getBooksCollection()) {
                booksCollectionBooks.getPublishersCollection().add(publishers);
                booksCollectionBooks = em.merge(booksCollectionBooks);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publishers publishers) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            Publishers persistentPublishers = em.find(Publishers.class, publishers.getPublisherId());
            Collection<Books> booksCollectionOld = persistentPublishers.getBooksCollection();
            Collection<Books> booksCollectionNew = publishers.getBooksCollection();
            Collection<Books> attachedBooksCollectionNew = new ArrayList<Books>();
            for (Books booksCollectionNewBooksToAttach : booksCollectionNew) {
                booksCollectionNewBooksToAttach = em.getReference(booksCollectionNewBooksToAttach.getClass(), booksCollectionNewBooksToAttach.getIsbn());
                attachedBooksCollectionNew.add(booksCollectionNewBooksToAttach);
            }
            booksCollectionNew = attachedBooksCollectionNew;
            publishers.setBooksCollection(booksCollectionNew);
            publishers = em.merge(publishers);
            for (Books booksCollectionOldBooks : booksCollectionOld) {
                if (!booksCollectionNew.contains(booksCollectionOldBooks)) {
                    booksCollectionOldBooks.getPublishersCollection().remove(publishers);
                    booksCollectionOldBooks = em.merge(booksCollectionOldBooks);
                }
            }
            for (Books booksCollectionNewBooks : booksCollectionNew) {
                if (!booksCollectionOld.contains(booksCollectionNewBooks)) {
                    booksCollectionNewBooks.getPublishersCollection().add(publishers);
                    booksCollectionNewBooks = em.merge(booksCollectionNewBooks);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = publishers.getPublisherId();
                if (findPublishers(id) == null) {
                    throw new NonexistentEntityException("The publishers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em.getTransaction().begin();
            Publishers publishers;
            try {
                publishers = em.getReference(Publishers.class, id);
                publishers.getPublisherId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publishers with id " + id + " no longer exists.", enfe);
            }
            Collection<Books> booksCollection = publishers.getBooksCollection();
            for (Books booksCollectionBooks : booksCollection) {
                booksCollectionBooks.getPublishersCollection().remove(publishers);
                booksCollectionBooks = em.merge(booksCollectionBooks);
            }
            em.remove(publishers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publishers> findPublishersEntities() {
        return findPublishersEntities(true, -1, -1);
    }

    public List<Publishers> findPublishersEntities(int maxResults, int firstResult) {
        return findPublishersEntities(false, maxResults, firstResult);
    }

    private List<Publishers> findPublishersEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publishers.class));
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

    public Publishers findPublishers(Long id) {
        try {
            return em.find(Publishers.class, id);
        } finally {
            em.close();
        }
    }

    public int getPublishersCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publishers> rt = cq.from(Publishers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
