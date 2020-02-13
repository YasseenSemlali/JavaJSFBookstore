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
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jeffrey Boisvert
 */
public class BooksJpaController implements Serializable {

    public BooksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Books books) throws PreexistingEntityException, Exception {
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBooks(books.getIsbn()) != null) {
                throw new PreexistingEntityException("Books " + books + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Books books) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = books.getIsbn();
                if (findBooks(id) == null) {
                    throw new NonexistentEntityException("The books with id " + id + " no longer exists.");
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Books> findBooksEntities() {
        return findBooksEntities(true, -1, -1);
    }

    public List<Books> findBooksEntities(int maxResults, int firstResult) {
        return findBooksEntities(false, maxResults, firstResult);
    }

    private List<Books> findBooksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Books.class));
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

    public Books findBooks(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Books.class, id);
        } finally {
            em.close();
        }
    }

    public int getBooksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Books> rt = cq.from(Books.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
