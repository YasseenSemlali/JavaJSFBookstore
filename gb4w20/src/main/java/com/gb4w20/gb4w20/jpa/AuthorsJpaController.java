
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Authors;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
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
 * Used to interact with the authors table. 
 * 
 * @author Jeffrey Boisvert
 */
public class AuthorsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(AuthorsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Authors authors) {
        if (authors.getBooksCollection() == null) {
            authors.setBooksCollection(new ArrayList<Books>());
        }
        try {
            em.getTransaction().begin();
            Collection<Books> attachedBooksCollection = new ArrayList<Books>();
            for (Books booksCollectionBooksToAttach : authors.getBooksCollection()) {
                booksCollectionBooksToAttach = em.getReference(booksCollectionBooksToAttach.getClass(), booksCollectionBooksToAttach.getIsbn());
                attachedBooksCollection.add(booksCollectionBooksToAttach);
            }
            authors.setBooksCollection(attachedBooksCollection);
            em.persist(authors);
            for (Books booksCollectionBooks : authors.getBooksCollection()) {
                booksCollectionBooks.getAuthorsCollection().add(authors);
                booksCollectionBooks = em.merge(booksCollectionBooks);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Authors authors) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            Authors persistentAuthors = em.find(Authors.class, authors.getAuthorId());
            Collection<Books> booksCollectionOld = persistentAuthors.getBooksCollection();
            Collection<Books> booksCollectionNew = authors.getBooksCollection();
            Collection<Books> attachedBooksCollectionNew = new ArrayList<Books>();
            for (Books booksCollectionNewBooksToAttach : booksCollectionNew) {
                booksCollectionNewBooksToAttach = em.getReference(booksCollectionNewBooksToAttach.getClass(), booksCollectionNewBooksToAttach.getIsbn());
                attachedBooksCollectionNew.add(booksCollectionNewBooksToAttach);
            }
            booksCollectionNew = attachedBooksCollectionNew;
            authors.setBooksCollection(booksCollectionNew);
            authors = em.merge(authors);
            for (Books booksCollectionOldBooks : booksCollectionOld) {
                if (!booksCollectionNew.contains(booksCollectionOldBooks)) {
                    booksCollectionOldBooks.getAuthorsCollection().remove(authors);
                    booksCollectionOldBooks = em.merge(booksCollectionOldBooks);
                }
            }
            for (Books booksCollectionNewBooks : booksCollectionNew) {
                if (!booksCollectionOld.contains(booksCollectionNewBooks)) {
                    booksCollectionNewBooks.getAuthorsCollection().add(authors);
                    booksCollectionNewBooks = em.merge(booksCollectionNewBooks);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = authors.getAuthorId();
                if (findAuthors(id) == null) {
                    throw new NonexistentEntityException("The authors with id " + id + " no longer exists.");
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
        try {
            em.getTransaction().begin();
            Authors authors;
            try {
                authors = em.getReference(Authors.class, id);
                authors.getAuthorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The authors with id " + id + " no longer exists.", enfe);
            }
            Collection<Books> booksCollection = authors.getBooksCollection();
            for (Books booksCollectionBooks : booksCollection) {
                booksCollectionBooks.getAuthorsCollection().remove(authors);
                booksCollectionBooks = em.merge(booksCollectionBooks);
            }
            em.remove(authors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Authors> findAuthorsEntities() {
        return findAuthorsEntities(true, -1, -1);
    }

    public List<Authors> findAuthorsEntities(int maxResults, int firstResult) {
        return findAuthorsEntities(false, maxResults, firstResult);
    }

    private List<Authors> findAuthorsEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Authors.class));
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

    public Authors findAuthors(Long id) {
        try {
            return em.find(Authors.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuthorsCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Authors> rt = cq.from(Authors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
