package com.gb4w20.jpa;

import com.gb4w20.entities.Authors;
import com.gb4w20.entities.Authors_;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.entities.Books;
import com.gb4w20.entities.Books_;
import com.gb4w20.entities.Genres;
import com.gb4w20.entities.Genres_;
import com.gb4w20.jpa.exceptions.BackendException;
import com.gb4w20.jpa.exceptions.NonexistentEntityException;
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
 * @author Jeffrey Boisvert, Yasseen Semlali, Jasmar Badion
 */
@Named
@SessionScoped
public class GenresJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(GenresJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Genres genres) throws BackendException {
        if (genres.getBooksCollection() == null) {
            genres.setBooksCollection(new ArrayList<Books>());
        }
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in authors controller method.", ex);
            throw new BackendException("Error in create method in genres controller.");
        } 
    }

    public void edit(Genres genres) throws NonexistentEntityException, Exception {
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with edit in authors controller method.", ex);
            throw new BackendException("Error in edit method in genres controller.");
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
    
    /**
     * Getting other books of same genre but different author
     * to be displayed on the book page
     * 
     * @param isbn
     * @param genres
     * @param authors
     * @param maxResults
     * @return 
     * @author Jasmar
     */
    public List<Books> getOtherBooksOfSameGenre(long isbn, Collection<Genres> genres, Collection<Authors> authors, int maxResults){
        LOG.info("getting " + maxResults + " other books of same genre and different author");
        CriteriaBuilder cb = em.getCriteriaBuilder();      
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);
        Root<Books> book = cq.from(Books.class);
        Join genre = book.join(Books_.genresCollection);
        Join author = book.join(Books_.authorsCollection);
        
        List<Long> genIds = new ArrayList<>();
        genres.forEach((gen) -> {
            genIds.add(gen.getGenreId());
        });
        List<Long> authIds = new ArrayList<>();
        authors.forEach((auth) -> {
            authIds.add(auth.getAuthorId());
        });
        cq.select(book)
                .where(cb.and(
                        cb.notEqual(book.get(Books_.isbn), isbn),
                        author.get(Authors_.authorId).in(authIds).not(),
                        genre.get(Genres_.genreId).in(genIds)
                )).distinct(true);
        
        Query query = em.createQuery(cq); 
        query.setMaxResults(maxResults);
        
        return query.getResultList();
    }
}
