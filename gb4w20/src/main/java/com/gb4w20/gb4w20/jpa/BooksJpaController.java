/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Genres;
import java.util.ArrayList;
import java.util.Collection;
import com.gb4w20.gb4w20.entities.Publishers;
import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.BookFiles;
import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Books_;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.jpa.exceptions.PreexistingEntityException;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
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
 * @author Yasseen Semlali
 */
@Named
@SessionScoped
public class BooksJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(BooksJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Books books) throws RollbackFailureException {
        if (books.getGenresCollection() == null) {
            books.setGenresCollection(new ArrayList<Genres>());
        }
        if (books.getPublishersCollection() == null) {
            books.setPublishersCollection(new ArrayList<Publishers>());
        }
        if (books.getAuthorsCollection() == null) {
            books.setAuthorsCollection(new ArrayList<Authors>());
        }
        if (books.getBookFilesCollection() == null) {
            books.setBookFilesCollection(new ArrayList<BookFiles>());
        }
        if (books.getReviewsCollection() == null) {
            books.setReviewsCollection(new ArrayList<Reviews>());
        }
        if (books.getBookorderCollection() == null) {
            books.setBookorderCollection(new ArrayList<Bookorder>());
        }
        try {
            utx.begin();
            Collection<Genres> attachedGenresCollection = new ArrayList<Genres>();
            for (Genres genresCollectionGenresToAttach : books.getGenresCollection()) {
                genresCollectionGenresToAttach = em.getReference(genresCollectionGenresToAttach.getClass(), genresCollectionGenresToAttach.getGenreId());
                attachedGenresCollection.add(genresCollectionGenresToAttach);
            }
            books.setGenresCollection(attachedGenresCollection);
            Collection<Publishers> attachedPublishersCollection = new ArrayList<Publishers>();
            for (Publishers publishersCollectionPublishersToAttach : books.getPublishersCollection()) {
                publishersCollectionPublishersToAttach = em.getReference(publishersCollectionPublishersToAttach.getClass(), publishersCollectionPublishersToAttach.getPublisherId());
                attachedPublishersCollection.add(publishersCollectionPublishersToAttach);
            }
            books.setPublishersCollection(attachedPublishersCollection);
            Collection<Authors> attachedAuthorsCollection = new ArrayList<Authors>();
            for (Authors authorsCollectionAuthorsToAttach : books.getAuthorsCollection()) {
                authorsCollectionAuthorsToAttach = em.getReference(authorsCollectionAuthorsToAttach.getClass(), authorsCollectionAuthorsToAttach.getAuthorId());
                attachedAuthorsCollection.add(authorsCollectionAuthorsToAttach);
            }
            books.setAuthorsCollection(attachedAuthorsCollection);
            Collection<BookFiles> attachedBookFilesCollection = new ArrayList<BookFiles>();
            for (BookFiles bookFilesCollectionBookFilesToAttach : books.getBookFilesCollection()) {
                bookFilesCollectionBookFilesToAttach = em.getReference(bookFilesCollectionBookFilesToAttach.getClass(), bookFilesCollectionBookFilesToAttach.getBookFileId());
                attachedBookFilesCollection.add(bookFilesCollectionBookFilesToAttach);
            }
            books.setBookFilesCollection(attachedBookFilesCollection);
            Collection<Reviews> attachedReviewsCollection = new ArrayList<Reviews>();
            for (Reviews reviewsCollectionReviewsToAttach : books.getReviewsCollection()) {
                reviewsCollectionReviewsToAttach = em.getReference(reviewsCollectionReviewsToAttach.getClass(), reviewsCollectionReviewsToAttach.getReviewId());
                attachedReviewsCollection.add(reviewsCollectionReviewsToAttach);
            }
            books.setReviewsCollection(attachedReviewsCollection);
            Collection<Bookorder> attachedBookorderCollection = new ArrayList<Bookorder>();
            for (Bookorder bookorderCollectionBookorderToAttach : books.getBookorderCollection()) {
                bookorderCollectionBookorderToAttach = em.getReference(bookorderCollectionBookorderToAttach.getClass(), bookorderCollectionBookorderToAttach.getBookorderId());
                attachedBookorderCollection.add(bookorderCollectionBookorderToAttach);
            }
            books.setBookorderCollection(attachedBookorderCollection);
            em.persist(books);
            for (Genres genresCollectionGenres : books.getGenresCollection()) {
                genresCollectionGenres.getBooksCollection().add(books);
                genresCollectionGenres = em.merge(genresCollectionGenres);
            }
            for (Publishers publishersCollectionPublishers : books.getPublishersCollection()) {
                publishersCollectionPublishers.getBooksCollection().add(books);
                publishersCollectionPublishers = em.merge(publishersCollectionPublishers);
            }
            for (Authors authorsCollectionAuthors : books.getAuthorsCollection()) {
                authorsCollectionAuthors.getBooksCollection().add(books);
                authorsCollectionAuthors = em.merge(authorsCollectionAuthors);
            }
            for (BookFiles bookFilesCollectionBookFiles : books.getBookFilesCollection()) {
                Books oldIsbnOfBookFilesCollectionBookFiles = bookFilesCollectionBookFiles.getIsbn();
                bookFilesCollectionBookFiles.setIsbn(books);
                bookFilesCollectionBookFiles = em.merge(bookFilesCollectionBookFiles);
                if (oldIsbnOfBookFilesCollectionBookFiles != null) {
                    oldIsbnOfBookFilesCollectionBookFiles.getBookFilesCollection().remove(bookFilesCollectionBookFiles);
                    oldIsbnOfBookFilesCollectionBookFiles = em.merge(oldIsbnOfBookFilesCollectionBookFiles);
                }
            }
            for (Reviews reviewsCollectionReviews : books.getReviewsCollection()) {
                Books oldIsbnOfReviewsCollectionReviews = reviewsCollectionReviews.getIsbn();
                reviewsCollectionReviews.setIsbn(books);
                reviewsCollectionReviews = em.merge(reviewsCollectionReviews);
                if (oldIsbnOfReviewsCollectionReviews != null) {
                    oldIsbnOfReviewsCollectionReviews.getReviewsCollection().remove(reviewsCollectionReviews);
                    oldIsbnOfReviewsCollectionReviews = em.merge(oldIsbnOfReviewsCollectionReviews);
                }
            }
            for (Bookorder bookorderCollectionBookorder : books.getBookorderCollection()) {
                Books oldIsbnOfBookorderCollectionBookorder = bookorderCollectionBookorder.getIsbn();
                bookorderCollectionBookorder.setIsbn(books);
                bookorderCollectionBookorder = em.merge(bookorderCollectionBookorder);
                if (oldIsbnOfBookorderCollectionBookorder != null) {
                    oldIsbnOfBookorderCollectionBookorder.getBookorderCollection().remove(bookorderCollectionBookorder);
                    oldIsbnOfBookorderCollectionBookorder = em.merge(oldIsbnOfBookorderCollectionBookorder);
                }
            }
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

    public void edit(Books books) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            utx.begin();
            Books persistentBooks = em.find(Books.class, books.getIsbn());
            Collection<Genres> genresCollectionOld = persistentBooks.getGenresCollection();
            Collection<Genres> genresCollectionNew = books.getGenresCollection();
            Collection<Publishers> publishersCollectionOld = persistentBooks.getPublishersCollection();
            Collection<Publishers> publishersCollectionNew = books.getPublishersCollection();
            Collection<Authors> authorsCollectionOld = persistentBooks.getAuthorsCollection();
            Collection<Authors> authorsCollectionNew = books.getAuthorsCollection();
            Collection<BookFiles> bookFilesCollectionOld = persistentBooks.getBookFilesCollection();
            Collection<BookFiles> bookFilesCollectionNew = books.getBookFilesCollection();
            Collection<Reviews> reviewsCollectionOld = persistentBooks.getReviewsCollection();
            Collection<Reviews> reviewsCollectionNew = books.getReviewsCollection();
            Collection<Bookorder> bookorderCollectionOld = persistentBooks.getBookorderCollection();
            Collection<Bookorder> bookorderCollectionNew = books.getBookorderCollection();
            List<String> illegalOrphanMessages = null;
            for (BookFiles bookFilesCollectionOldBookFiles : bookFilesCollectionOld) {
                if (!bookFilesCollectionNew.contains(bookFilesCollectionOldBookFiles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BookFiles " + bookFilesCollectionOldBookFiles + " since its isbn field is not nullable.");
                }
            }
            for (Reviews reviewsCollectionOldReviews : reviewsCollectionOld) {
                if (!reviewsCollectionNew.contains(reviewsCollectionOldReviews)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reviews " + reviewsCollectionOldReviews + " since its isbn field is not nullable.");
                }
            }
            for (Bookorder bookorderCollectionOldBookorder : bookorderCollectionOld) {
                if (!bookorderCollectionNew.contains(bookorderCollectionOldBookorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bookorder " + bookorderCollectionOldBookorder + " since its isbn field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Genres> attachedGenresCollectionNew = new ArrayList<Genres>();
            for (Genres genresCollectionNewGenresToAttach : genresCollectionNew) {
                genresCollectionNewGenresToAttach = em.getReference(genresCollectionNewGenresToAttach.getClass(), genresCollectionNewGenresToAttach.getGenreId());
                attachedGenresCollectionNew.add(genresCollectionNewGenresToAttach);
            }
            genresCollectionNew = attachedGenresCollectionNew;
            books.setGenresCollection(genresCollectionNew);
            Collection<Publishers> attachedPublishersCollectionNew = new ArrayList<Publishers>();
            for (Publishers publishersCollectionNewPublishersToAttach : publishersCollectionNew) {
                publishersCollectionNewPublishersToAttach = em.getReference(publishersCollectionNewPublishersToAttach.getClass(), publishersCollectionNewPublishersToAttach.getPublisherId());
                attachedPublishersCollectionNew.add(publishersCollectionNewPublishersToAttach);
            }
            publishersCollectionNew = attachedPublishersCollectionNew;
            books.setPublishersCollection(publishersCollectionNew);
            Collection<Authors> attachedAuthorsCollectionNew = new ArrayList<Authors>();
            for (Authors authorsCollectionNewAuthorsToAttach : authorsCollectionNew) {
                authorsCollectionNewAuthorsToAttach = em.getReference(authorsCollectionNewAuthorsToAttach.getClass(), authorsCollectionNewAuthorsToAttach.getAuthorId());
                attachedAuthorsCollectionNew.add(authorsCollectionNewAuthorsToAttach);
            }
            authorsCollectionNew = attachedAuthorsCollectionNew;
            books.setAuthorsCollection(authorsCollectionNew);
            Collection<BookFiles> attachedBookFilesCollectionNew = new ArrayList<BookFiles>();
            for (BookFiles bookFilesCollectionNewBookFilesToAttach : bookFilesCollectionNew) {
                bookFilesCollectionNewBookFilesToAttach = em.getReference(bookFilesCollectionNewBookFilesToAttach.getClass(), bookFilesCollectionNewBookFilesToAttach.getBookFileId());
                attachedBookFilesCollectionNew.add(bookFilesCollectionNewBookFilesToAttach);
            }
            bookFilesCollectionNew = attachedBookFilesCollectionNew;
            books.setBookFilesCollection(bookFilesCollectionNew);
            Collection<Reviews> attachedReviewsCollectionNew = new ArrayList<Reviews>();
            for (Reviews reviewsCollectionNewReviewsToAttach : reviewsCollectionNew) {
                reviewsCollectionNewReviewsToAttach = em.getReference(reviewsCollectionNewReviewsToAttach.getClass(), reviewsCollectionNewReviewsToAttach.getReviewId());
                attachedReviewsCollectionNew.add(reviewsCollectionNewReviewsToAttach);
            }
            reviewsCollectionNew = attachedReviewsCollectionNew;
            books.setReviewsCollection(reviewsCollectionNew);
            Collection<Bookorder> attachedBookorderCollectionNew = new ArrayList<Bookorder>();
            for (Bookorder bookorderCollectionNewBookorderToAttach : bookorderCollectionNew) {
                bookorderCollectionNewBookorderToAttach = em.getReference(bookorderCollectionNewBookorderToAttach.getClass(), bookorderCollectionNewBookorderToAttach.getBookorderId());
                attachedBookorderCollectionNew.add(bookorderCollectionNewBookorderToAttach);
            }
            bookorderCollectionNew = attachedBookorderCollectionNew;
            books.setBookorderCollection(bookorderCollectionNew);
            books = em.merge(books);
            for (Genres genresCollectionOldGenres : genresCollectionOld) {
                if (!genresCollectionNew.contains(genresCollectionOldGenres)) {
                    genresCollectionOldGenres.getBooksCollection().remove(books);
                    genresCollectionOldGenres = em.merge(genresCollectionOldGenres);
                }
            }
            for (Genres genresCollectionNewGenres : genresCollectionNew) {
                if (!genresCollectionOld.contains(genresCollectionNewGenres)) {
                    genresCollectionNewGenres.getBooksCollection().add(books);
                    genresCollectionNewGenres = em.merge(genresCollectionNewGenres);
                }
            }
            for (Publishers publishersCollectionOldPublishers : publishersCollectionOld) {
                if (!publishersCollectionNew.contains(publishersCollectionOldPublishers)) {
                    publishersCollectionOldPublishers.getBooksCollection().remove(books);
                    publishersCollectionOldPublishers = em.merge(publishersCollectionOldPublishers);
                }
            }
            for (Publishers publishersCollectionNewPublishers : publishersCollectionNew) {
                if (!publishersCollectionOld.contains(publishersCollectionNewPublishers)) {
                    publishersCollectionNewPublishers.getBooksCollection().add(books);
                    publishersCollectionNewPublishers = em.merge(publishersCollectionNewPublishers);
                }
            }
            for (Authors authorsCollectionOldAuthors : authorsCollectionOld) {
                if (!authorsCollectionNew.contains(authorsCollectionOldAuthors)) {
                    authorsCollectionOldAuthors.getBooksCollection().remove(books);
                    authorsCollectionOldAuthors = em.merge(authorsCollectionOldAuthors);
                }
            }
            for (Authors authorsCollectionNewAuthors : authorsCollectionNew) {
                if (!authorsCollectionOld.contains(authorsCollectionNewAuthors)) {
                    authorsCollectionNewAuthors.getBooksCollection().add(books);
                    authorsCollectionNewAuthors = em.merge(authorsCollectionNewAuthors);
                }
            }
            for (BookFiles bookFilesCollectionNewBookFiles : bookFilesCollectionNew) {
                if (!bookFilesCollectionOld.contains(bookFilesCollectionNewBookFiles)) {
                    Books oldIsbnOfBookFilesCollectionNewBookFiles = bookFilesCollectionNewBookFiles.getIsbn();
                    bookFilesCollectionNewBookFiles.setIsbn(books);
                    bookFilesCollectionNewBookFiles = em.merge(bookFilesCollectionNewBookFiles);
                    if (oldIsbnOfBookFilesCollectionNewBookFiles != null && !oldIsbnOfBookFilesCollectionNewBookFiles.equals(books)) {
                        oldIsbnOfBookFilesCollectionNewBookFiles.getBookFilesCollection().remove(bookFilesCollectionNewBookFiles);
                        oldIsbnOfBookFilesCollectionNewBookFiles = em.merge(oldIsbnOfBookFilesCollectionNewBookFiles);
                    }
                }
            }
            for (Reviews reviewsCollectionNewReviews : reviewsCollectionNew) {
                if (!reviewsCollectionOld.contains(reviewsCollectionNewReviews)) {
                    Books oldIsbnOfReviewsCollectionNewReviews = reviewsCollectionNewReviews.getIsbn();
                    reviewsCollectionNewReviews.setIsbn(books);
                    reviewsCollectionNewReviews = em.merge(reviewsCollectionNewReviews);
                    if (oldIsbnOfReviewsCollectionNewReviews != null && !oldIsbnOfReviewsCollectionNewReviews.equals(books)) {
                        oldIsbnOfReviewsCollectionNewReviews.getReviewsCollection().remove(reviewsCollectionNewReviews);
                        oldIsbnOfReviewsCollectionNewReviews = em.merge(oldIsbnOfReviewsCollectionNewReviews);
                    }
                }
            }
            for (Bookorder bookorderCollectionNewBookorder : bookorderCollectionNew) {
                if (!bookorderCollectionOld.contains(bookorderCollectionNewBookorder)) {
                    Books oldIsbnOfBookorderCollectionNewBookorder = bookorderCollectionNewBookorder.getIsbn();
                    bookorderCollectionNewBookorder.setIsbn(books);
                    bookorderCollectionNewBookorder = em.merge(bookorderCollectionNewBookorder);
                    if (oldIsbnOfBookorderCollectionNewBookorder != null && !oldIsbnOfBookorderCollectionNewBookorder.equals(books)) {
                        oldIsbnOfBookorderCollectionNewBookorder.getBookorderCollection().remove(bookorderCollectionNewBookorder);
                        oldIsbnOfBookorderCollectionNewBookorder = em.merge(oldIsbnOfBookorderCollectionNewBookorder);
                    }
                }
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
                Long isbn = books.getIsbn();
                if (findBooks(isbn) == null) {
                    throw new NonexistentEntityException("The book with isbn " + isbn + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {

            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Books books;
            try {
                books = em.getReference(Books.class, id);
                books.getIsbn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The books with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BookFiles> bookFilesCollectionOrphanCheck = books.getBookFilesCollection();
            for (BookFiles bookFilesCollectionOrphanCheckBookFiles : bookFilesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Books (" + books + ") cannot be destroyed since the BookFiles " + bookFilesCollectionOrphanCheckBookFiles + " in its bookFilesCollection field has a non-nullable isbn field.");
            }
            Collection<Reviews> reviewsCollectionOrphanCheck = books.getReviewsCollection();
            for (Reviews reviewsCollectionOrphanCheckReviews : reviewsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Books (" + books + ") cannot be destroyed since the Reviews " + reviewsCollectionOrphanCheckReviews + " in its reviewsCollection field has a non-nullable isbn field.");
            }
            Collection<Bookorder> bookorderCollectionOrphanCheck = books.getBookorderCollection();
            for (Bookorder bookorderCollectionOrphanCheckBookorder : bookorderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Books (" + books + ") cannot be destroyed since the Bookorder " + bookorderCollectionOrphanCheckBookorder + " in its bookorderCollection field has a non-nullable isbn field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Genres> genresCollection = books.getGenresCollection();
            for (Genres genresCollectionGenres : genresCollection) {
                genresCollectionGenres.getBooksCollection().remove(books);
                genresCollectionGenres = em.merge(genresCollectionGenres);
            }
            Collection<Publishers> publishersCollection = books.getPublishersCollection();
            for (Publishers publishersCollectionPublishers : publishersCollection) {
                publishersCollectionPublishers.getBooksCollection().remove(books);
                publishersCollectionPublishers = em.merge(publishersCollectionPublishers);
            }
            Collection<Authors> authorsCollection = books.getAuthorsCollection();
            for (Authors authorsCollectionAuthors : authorsCollection) {
                authorsCollectionAuthors.getBooksCollection().remove(books);
                authorsCollectionAuthors = em.merge(authorsCollectionAuthors);
            }
            em.remove(books);
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

    public List<Books> findBooksEntities() {
        return findBooksEntities(-1, -1);
    }

    public List<Books> findBooksEntities(int maxResults) {
        return findBooksEntities(maxResults, -1);
    }

    private List<Books> findBooksEntities(int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Books.class));
        Query q = em.createQuery(cq);
        if (maxResults != -1) {
            q.setMaxResults(maxResults);
        }
        if (firstResult != -1) {
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Books findBooks(Long id) {
        return em.find(Books.class, id);
    }

    public List<Books> getBooksOnSale() {
        return this.getBooksOnSale(-1);
    }

    public List<Books> getBooksOnSale(int maxResults) {
        LOG.info("getting books on sale");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);

        Root<Books> book = cq.from(Books.class);

        cq.select(book).where(cb.gt(book.get("salePrice"), 0));

        Query query = em.createQuery(cq);
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }

        return query.getResultList();
    }

    public List<Books> getRecentlyBoughtBooks(int maxResults) {
        LOG.info("getting " + maxResults + " recent books");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);

        Root<Books> book = cq.from(Books.class);
        Join<Books, Bookorder> bookorder = book.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Orders> order = bookorder.join("orderId", JoinType.INNER);
        Join<Orders, Users> user = order.join("userId", JoinType.INNER);

        // TODO get email from session
        cq.select(book).where(cb.equal(user.get("email"), "cst.send@gmail.com"));
        cq.orderBy(cb.desc(order.get("timestamp")));

        Query query = em.createQuery(cq);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }
    
    public int getBooksCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Books> rt = cq.from(Books.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    /**
     * Getting three books from the same author that
     * will be displayed in the book page
     * 
     * @param isbn
     * @param maxResults
     * @return 
     */
    public List<Books> getBooksBySameAuthor(long isbn, int maxResults){
        LOG.info("getting " + maxResults + " books from the same author");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);
        
        Root<Books> book = cq.from(Books.class);
        Join<Books, Authors> authors = book.join(Books_.authorsCollection, JoinType.INNER);
        
        cq.select(book);
        if(isbn != -1){
            cq.where(cb.equal(book.get("isbn"), isbn));
        }
        
        Query query = em.createQuery(cq); 
        
        return query.getResultList();
        
    }
    
    /**
     * Getting three books from the same genre that
     * will be displayed in the book page
     * 
     * @param maxResults
     * @return 
     */
    public List<Books> getBooksBySameGenre(int maxResults){
        LOG.info("getting " + maxResults + " books from the same genre");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);
    } 
     /**   
     * Used to find the books that were top sellers in the specified date range
     * and returns their titles and totals. 
     * @param startDate of the report
     * @param endDate of the report
     * @return the report of book titles and total sales of the book. 
     * @author Jeffrey Boisvert
     */
    public List<NameAndNumberBean> findTopSellers(String startDate, String endDate){
        
        LOG.info("Looking for top sellering books between " + startDate + " and " + endDate);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(NameAndNumberBean.class);
        
        Root<Books> book = cq.from(Books.class);
        Join<Books, Bookorder> bookorder = book.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Orders> order = bookorder.join("orderId", JoinType.INNER);
        
        cq.multiselect(
                    book.get(Books_.title), 
                    em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax"))
                 )
                .groupBy(book.get(Books_.title))
                .where(
                        cb.between(order.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                 )
                .orderBy(cb.desc(cb.sum(bookorder.get("amountPaidPretax"))));

        Query query = em.createQuery(cq);
        return query.getResultList();
    }
    
    /**
     * Used to find the books that were never sold. 
     * @param startDate of the report
     * @param endDate of the report
     * @return books that were never sold. 
     * @author Jeffrey Boisvert
     */
    public List<Books> findBooksThatWereNeverSold(String startDate, String endDate){
        
        LOG.info("Looking for books that were never sold between " + startDate + " and " + endDate);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery booksSoldCriteraQuery = cb.createQuery(Books.class);
        
        //Get all the books sold
        Root<Books> bookSold = booksSoldCriteraQuery.from(Books.class);
        Join<Books, Bookorder> bookorder = bookSold.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Orders> order = bookorder.join("orderId", JoinType.INNER);
       
        booksSoldCriteraQuery.select(bookSold.get("isbn"))
                .where(cb.between(order.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                );
        Query booksSoldQuery = em.createQuery(booksSoldCriteraQuery);
        
        //Find books not sold
        CriteriaQuery booksNotSoldCriteraQuery = cb.createQuery(Books.class);
        Root<Books> booksNotSold = booksNotSoldCriteraQuery.from(Books.class);
        Predicate notSoldIsbns = booksNotSold.get(Books_.isbn).in(booksSoldQuery.getResultList()).not();
        booksNotSoldCriteraQuery.select(booksNotSold)
                .where(
                   notSoldIsbns
                )
                .orderBy(cb.asc(booksNotSold.get("title")));
        Query booksNotSoldQuery = em.createQuery(booksNotSoldCriteraQuery);
        
        return booksNotSoldQuery.getResultList();
    }

    /**
     * Used to get all active books in the database. 
     * @return a list of all the books. 
     * @author Jeffrey Boisvert
     */
    public List<Books> getActiveBooks() {
        return this.getBooksOnSale(-1);
    }

    /**
     * Used to get a certain number of active books. 
     * @param maxResults 
     * @return a list of active books sorted by title
     * @author Jeffrey Boisvert
     */
    public List<Books> getActiveBooks(int maxResults) {
        LOG.info("Getting all active books");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);

        Root<Books> book = cq.from(Books.class);
        cq.select(book)
                .where(cb.isTrue(book.get("active")))
                .orderBy(cb.asc(book.get("title")));
        Query query = em.createQuery(cq);
        
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }

        return query.getResultList();
    }

}
