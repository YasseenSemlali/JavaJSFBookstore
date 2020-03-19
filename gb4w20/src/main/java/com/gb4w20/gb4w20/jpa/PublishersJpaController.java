
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Bookorder;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Books_;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Publishers;
import com.gb4w20.gb4w20.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import com.gb4w20.gb4w20.querybeans.NameTotalAndCountBean;
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

    public void create(Publishers publishers) throws BackendException {
        if (publishers.getBooksCollection() == null) {
            publishers.setBooksCollection(new ArrayList<Books>());
        }
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in publishers controller method.", ex);
            throw new BackendException("Error in create method in publishers controller.");
        } 
    }

    public void edit(Publishers publishers) throws NonexistentEntityException, Exception {
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with edit in publishers controller method.", ex);
            throw new BackendException("Error in edit method in publishers controller.");
        } 
    }

    public void destroy(Long id) throws NonexistentEntityException {
        try {
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with delete in publishers controller method.", ex);
        } 
    }

    public List<Publishers> findPublishersEntities() {
        return findPublishersEntities(true, -1, -1);
    }

    public List<Publishers> findPublishersEntities(int maxResults, int firstResult) {
        return findPublishersEntities(false, maxResults, firstResult);
    }

    private List<Publishers> findPublishersEntities(boolean all, int maxResults, int firstResult) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publishers.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    }

    public Publishers findPublishers(Long id) {
            return em.find(Publishers.class, id);
    }

    public int getPublishersCount() {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publishers> rt = cq.from(Publishers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
    }
    
    /**
     * Used to get the total sales a given publisher has made in a certain date range. 
     * @param id of the author in question. 
     * @param startDate of the report
     * @param endDate of the report
     * @return total sales
     * @author Jeffrey Boisvert
     */
    public double getPublisherTotalSales(long id, String startDate, String endDate){
        LOG.info("Looking for total sales for publisher with id " + id);
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        Root<Orders> orders = cq.from(Orders.class);
        Join<Orders, Bookorder> bookorder = orders.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Books> books = bookorder.join("isbn", JoinType.INNER);
        Join<Books, Publishers> publishers = books.join(Books_.publishersCollection);

        cq.select(em.getCriteriaBuilder().sum(bookorder.get("amountPaidPretax")))
                .where(cb.and(
                        cb.equal(publishers.get("publisherId"), id),
                        cb.between(orders.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                ));

        Query query = em.createQuery(cq);
        return query.getSingleResult() != null ? ((BigDecimal) query.getSingleResult()).doubleValue() : 0.00;
    }
    
    /**
     * Used to get a list of all the books purchased of an publisher and the totals each book has made.
     * @param id of the publisher in question
     * @param startDate to search for
     * @param endDate to search for
     * @return a list of the book titles and total sales. 
     * @author Jeffrey Boisvert
     */
    public List<NameTotalAndCountBean> getPurchasedBooksByPublisher(long id, String startDate, String endDate){
        LOG.info("Looking for books bought by publisher with id " + id);
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(NameTotalAndCountBean.class);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        Root<Orders> order = cq.from(Orders.class);
        Join<Orders, Bookorder> bookorder = order.join("bookorderCollection", JoinType.INNER);
        Join<Bookorder, Books> book = bookorder.join("isbn", JoinType.INNER);
        Join<Books, Publishers> publishers = book.join(Books_.publishersCollection);
        
        cq.multiselect(
                    book.get(Books_.title), 
                    cb.sum(bookorder.get("amountPaidPretax")),
                    cb.count(bookorder.get("orderId"))
                )
                .groupBy(book.get(Books_.title))
                .where(cb.and(
                        cb.equal(publishers.get("publisherId"), id),
                        cb.between(order.get("timestamp"), startDate + " 00:00:00", endDate + " 23:59:59")
                ))
                .orderBy(cb.asc((book.get(Books_.title))));

        Query query = em.createQuery(cq);
        return query.getResultList();
    }
    
}
