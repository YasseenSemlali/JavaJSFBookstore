package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Bookorder;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Genres;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
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
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jeffrey Boisvert, Yasseen Semlali
 */
@Named
@SessionScoped
public class GenresJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(GenresJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Genres genres) {
        if (genres.getBooksCollection() == null) {
            genres.setBooksCollection(new ArrayList<Books>());
        }
        em.getTransaction().begin();
        Collection<Books> attachedBooksCollection = new ArrayList<Books>();
        for (Books booksCollectionBooksToAttach : genres.getBooksCollection()) {
            booksCollectionBooksToAttach = em.getReference(booksCollectionBooksToAttach.getClass(), booksCollectionBooksToAttach.getIsbn());
            attachedBooksCollection.add(booksCollectionBooksToAttach);
        }
        genres.setBooksCollection(attachedBooksCollection);
        em.persist(genres);
        for (Books booksCollectionBooks : genres.getBooksCollection()) {
            booksCollectionBooks.getGenresCollection().add(genres);
            booksCollectionBooks = em.merge(booksCollectionBooks);
        }
        em.getTransaction().commit();
    }

    public void edit(Genres genres) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            Genres persistentGenres = em.find(Genres.class, genres.getGenreId());
            Collection<Books> booksCollectionOld = persistentGenres.getBooksCollection();
            Collection<Books> booksCollectionNew = genres.getBooksCollection();
            Collection<Books> attachedBooksCollectionNew = new ArrayList<Books>();
            for (Books booksCollectionNewBooksToAttach : booksCollectionNew) {
                booksCollectionNewBooksToAttach = em.getReference(booksCollectionNewBooksToAttach.getClass(), booksCollectionNewBooksToAttach.getIsbn());
                attachedBooksCollectionNew.add(booksCollectionNewBooksToAttach);
            }
            booksCollectionNew = attachedBooksCollectionNew;
            genres.setBooksCollection(booksCollectionNew);
            genres = em.merge(genres);
            for (Books booksCollectionOldBooks : booksCollectionOld) {
                if (!booksCollectionNew.contains(booksCollectionOldBooks)) {
                    booksCollectionOldBooks.getGenresCollection().remove(genres);
                    booksCollectionOldBooks = em.merge(booksCollectionOldBooks);
                }
            }
            for (Books booksCollectionNewBooks : booksCollectionNew) {
                if (!booksCollectionOld.contains(booksCollectionNewBooks)) {
                    booksCollectionNewBooks.getGenresCollection().add(genres);
                    booksCollectionNewBooks = em.merge(booksCollectionNewBooks);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = genres.getGenreId();
                if (findGenres(id) == null) {
                    throw new NonexistentEntityException("The genres with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        em.getTransaction().begin();
        Genres genres;
        try {
            genres = em.getReference(Genres.class, id);
            genres.getGenreId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The genres with id " + id + " no longer exists.", enfe);
        }
        Collection<Books> booksCollection = genres.getBooksCollection();
        for (Books booksCollectionBooks : booksCollection) {
            booksCollectionBooks.getGenresCollection().remove(genres);
            booksCollectionBooks = em.merge(booksCollectionBooks);
        }
        em.remove(genres);
        em.getTransaction().commit();
    }

    public List<Genres> findGenresEntities() {
        return findGenresEntities(true, -1, -1);
    }

    public List<Genres> findGenresEntities(int maxResults, int firstResult) {
        return findGenresEntities(false, maxResults, firstResult);
    }

    private List<Genres> findGenresEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Genres.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Genres findGenres(Long id) {
        return em.find(Genres.class, id);
    }

    public int getGenresCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Genres> rt = cq.from(Genres.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    
    
    public List<Books> getTopSelling(int maxResults) {
        return this.getTopSellingForGenre(-1, maxResults);
    }
    
    public List<Books> getTopSellingForGenre(long genreId, int maxResults) {
        LOG.info("getting " + maxResults + " top selling books for genre " + genreId);
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);

        Root<Books> book = cq.from(Books.class);
        Join<Books, Bookorder> bookorder = book.join("bookorderCollection", JoinType.INNER);

        cq.select(book);
        if(genreId != -1){
            cq.where(cb.equal(book.get("genreId"), genreId));
        }
        cq.groupBy(book.get("isbn"));   
        cq.orderBy(cb.desc(cb.count(bookorder)));

        Query query = em.createQuery(cq);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }
    
    /**
     * Getting other books of same genre but different author
     * to be displayed on the book page
     * 
     * @param isbn
     * @param genreId
     * @param authorId
     * @param maxResults
     * @return 
     * @author Jasmar
     */
    public List<Books> getOtherBooksOfSameGenre(long isbn, long genreId, long authorId, int maxResults){
        LOG.info("getting " + maxResults + " other books of same genre and different author");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);
        
        Root<Books> book = cq.from(Books.class);
        Join genre = book.join("genresCollection");
        Join author = book.join("authorsCollection");
        
        cq.select(book)
                .where(cb.and(
                        cb.notEqual(book.get("isbn"), isbn),
                        cb.notEqual(author.get("authorId"), authorId),
                        cb.equal(genre.get("genreId"), genreId)
                ));
        
        Query query = em.createQuery(cq);
        query.setMaxResults(maxResults);
        
        return query.getResultList();
    }
}
