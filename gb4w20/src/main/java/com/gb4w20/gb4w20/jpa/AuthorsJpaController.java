package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Authors_;
import com.gb4w20.gb4w20.entities.Bookorder;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Books_;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.exceptions.BackendException;
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
 * Used to interact with the authors table.
 *
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class AuthorsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(AuthorsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Authors authors) throws BackendException {
        if (authors.getBooksCollection() == null) {
            authors.setBooksCollection(new ArrayList<Books>());
        }
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in authors controller method.", ex);
        }
    }

    public void edit(Authors authors) throws NonexistentEntityException, Exception {
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with edit in authors controller method.", ex);
            throw new BackendException("Error in edit method in authors controller.");
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with delete in authors controller method.", ex);
        }
    }

    public List<Authors> findAuthorsEntities() {
        return findAuthorsEntities(true, -1, -1);
    }

    public List<Authors> findAuthorsEntities(int maxResults, int firstResult) {
        return findAuthorsEntities(false, maxResults, firstResult);
    }

    private List<Authors> findAuthorsEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        Root<Authors> authors = cq.from(Authors.class);
        cq.select(authors).orderBy(cb.asc((authors.get(Authors_.firstName))));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    /**
     * Find a particular author based on id.
     *
     * @param id
     * @return author matching id
     */
    public Authors findAuthors(Long id) {
        return em.find(Authors.class, id);
    }

    /**
     * Used to get the count of the authors.
     *
     * @return count of authors in database.
     */
    public int getAuthorsCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Authors> rt = cq.from(Authors.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Used to get the total sales a given author has made in a certain date
     * range.
     *
     * @param id of the author in question.
     * @param startDate of the report
     * @param endDate of the report
     * @return total sales
     * @author Jeffrey Boisvert
     */
    public double getAuthorsTotalSales(long id, String startDate, String endDate) {
        LOG.info("Looking for total sales for author with id " + id);
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        Root<Orders> orders = cq.from(Orders.class);
        Join<Orders, Bookorder> bookorder = orders.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Books> books = bookorder.join("isbn", JoinType.INNER);
        Join<Books, Authors> authors = books.join(Books_.authorsCollection);

        cq.select(em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .where(cb.and(
                        cb.equal(authors.get("authorId"), id),
                        cb.between(orders.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                ));

        Query query = em.createQuery(cq);
        return query.getSingleResult() != null ? ((BigDecimal) query.getSingleResult()).doubleValue() : 0.00;
    }

    /**
     * Used to get a list of all the books purchased of an author and the totals
     * each book has made.
     *
     * @param id of the author in question
     * @param startDate to search for
     * @param endDate to search for
     * @return a list of the book titles and total sales.
     * @author Jeffrey Boisvert
     */
    public List<NameAndNumberBean> getPurchasedBooksByAuthor(long id, String startDate, String endDate) {
        LOG.info("Looking for books bought by author with id " + id);
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(NameAndNumberBean.class);
        CriteriaBuilder cb = em.getCriteriaBuilder();

        Root<Orders> order = cq.from(Orders.class);
        Join<Orders, Bookorder> bookorder = order.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Books> book = bookorder.join("isbn", JoinType.INNER);
        Join<Books, Authors> authors = book.join(Books_.authorsCollection);

        cq.multiselect(book.get(Books_.title), em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .groupBy(book.get(Books_.title))
                .where(cb.and(
                        cb.equal(authors.get("authorId"), id),
                        cb.between(order.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                ))
                .orderBy(cb.asc((book.get(Books_.title))));

        Query query = em.createQuery(cq);
        return query.getResultList();
    }

    /**
     * Get other books by the same author that will be displayed in the book
     * page
     *
     * @param isbn
     * @param authorId
     * @param maxResults
     * @return
     * @author Jasmar
     */
    public List<Books> getOtherBooksBySameAuthor(long isbn, long authorId, int maxResults) {
        LOG.info("getting " + maxResults + " books from the same author");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Books> cq = cb.createQuery(Books.class);
        Root<Books> book = cq.from(Books.class);
        Join author = book.join(Books_.authorsCollection);
        cq.select(book)
                .where(cb.and(
                        cb.notEqual(book.get(Books_.isbn), isbn),
                        cb.equal(author.get(Authors_.authorId), authorId)
                ));

        Query query = em.createQuery(cq);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }
}
