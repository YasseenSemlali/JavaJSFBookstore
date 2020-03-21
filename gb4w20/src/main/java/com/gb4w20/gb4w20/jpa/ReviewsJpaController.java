
package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Books_;
import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.entities.Reviews_;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
 * Used to interact with the reviews table in the database. 
 * @author Jeffrey Boisvert, Jean Robatto
 */
@Named
@SessionScoped
public class ReviewsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ReviewsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(Reviews reviews) throws RollbackFailureException, BackendException {
        try {
            utx.begin();
            Books isbn = reviews.getIsbn();
            if (isbn != null) {
                isbn = em.getReference(isbn.getClass(), isbn.getIsbn());
                reviews.setIsbn(isbn);
            }
            Users userId = reviews.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                reviews.setUserId(userId);
            }
            em.persist(reviews);
            if (isbn != null) {
                isbn.getReviewsCollection().add(reviews);
                isbn = em.merge(isbn);
            }
            if (userId != null) {
                userId.getReviewsCollection().add(reviews);
                userId = em.merge(userId);
            }
            utx.commit();
            LOG.debug(reviews.getReview());
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                utx.rollback();
                LOG.error("Rollback");
                throw new BackendException("Error in create method in reviews controller.");
            } catch (IllegalStateException | SecurityException | SystemException re) {
                LOG.error("Rollback2");

                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
        }
    }

    public void edit(Reviews reviews) throws NonexistentEntityException, Exception {
        try {
            utx.begin();
            Reviews persistentReviews = em.find(Reviews.class, reviews.getReviewId());
            Books isbnOld = persistentReviews.getIsbn();
            Books isbnNew = reviews.getIsbn();
            Users userIdOld = persistentReviews.getUserId();
            Users userIdNew = reviews.getUserId();
            if (isbnNew != null) {
                isbnNew = em.getReference(isbnNew.getClass(), isbnNew.getIsbn());
                reviews.setIsbn(isbnNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                reviews.setUserId(userIdNew);
            }
            reviews = em.merge(reviews);
            if (isbnOld != null && !isbnOld.equals(isbnNew)) {
                isbnOld.getReviewsCollection().remove(reviews);
                isbnOld = em.merge(isbnOld);
            }
            if (isbnNew != null && !isbnNew.equals(isbnOld)) {
                isbnNew.getReviewsCollection().add(reviews);
                isbnNew = em.merge(isbnNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getReviewsCollection().remove(reviews);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getReviewsCollection().add(reviews);
                userIdNew = em.merge(userIdNew);
            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with edit in reviews controller method.");
            throw new BackendException("Error in edit method in reviews controller.");
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        try {
            em.getTransaction().begin();
            Reviews reviews;
            try {
                reviews = em.getReference(Reviews.class, id);
                reviews.getReviewId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reviews with id " + id + " no longer exists.", enfe);
            }
            Books isbn = reviews.getIsbn();
            if (isbn != null) {
                isbn.getReviewsCollection().remove(reviews);
                isbn = em.merge(isbn);
            }
            Users userId = reviews.getUserId();
            if (userId != null) {
                userId.getReviewsCollection().remove(reviews);
                userId = em.merge(userId);
            }
            em.remove(reviews);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reviews> findReviewsEntities() {
        return findReviewsEntities(true, -1, -1);
    }

    public List<Reviews> findReviewsEntities(int maxResults, int firstResult) {
        return findReviewsEntities(false, maxResults, firstResult);
    }

    private List<Reviews> findReviewsEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reviews.class));
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

    public Reviews findReviews(Long id) {
            return em.find(Reviews.class, id);
    }

    public int getReviewsCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reviews> rt = cq.from(Reviews.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get average rating of a book and
     * some books do not have any ratings yet so it returns 0 for those
     * 
     * @author Jasmar
     * @param isbn
     * @return 
     */
    public double getAverageRating(Long isbn){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Reviews.class);
        Root<Books> bookrating = cq.from(Books.class);
        Join rate = bookrating.join(Books_.reviewsCollection);
        
        cq.select(cb.avg(rate.get(Reviews_.rating)))
                .where(cb.equal(bookrating.get(Books_.isbn), isbn));
        
        TypedQuery<Double> avgrating = em.createQuery(cq);
        
        if (avgrating.getSingleResult() != null){
            //rounding the average rating to 1 decimal place
            return Math.round(avgrating.getSingleResult()*10)/10.0;
        }
        else{
            return 0.0;
        }
    }
    
    /**
     * Getting only approved reviews by the manager from 
     * a specific book that will be displayed in the 
     * book page
     * @param isbn
     * @return
     * @author Jasmar Badion
     */
    public List<Reviews> getApprovedReviews(Long isbn){
        LOG.info("Getting approved reviews from a specific book");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reviews> cq = cb.createQuery(Reviews.class);
        Root<Books> book = cq.from(Books.class);
        Join review = book.join(Books_.reviewsCollection);
        cq.select(review)
                .where(cb.and(
                    cb.equal(book.get(Books_.isbn), isbn),
                    cb.equal(review.get(Reviews_.approvedStatus), true)
                ));
        Query query = em.createQuery(cq);
        return query.getResultList();
    }
    
    /**
     * Returns reviews whose approval status matches the param
     * 
     * @param approved
     * @return List of reviews
     * @author Jean Robatto
     */
    public List<Reviews> getReviewsOnApproved(boolean approved){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reviews> cq = cb.createQuery(Reviews.class);
        
        Root<Reviews> review = cq.from(Reviews.class);
        
        cq.select(review)
                .where(
                    cb.equal(review.get(Reviews_.approvedStatus), approved)
                );
        
        Query query = em.createQuery(cq);
        return query.getResultList();
    }
    
}
