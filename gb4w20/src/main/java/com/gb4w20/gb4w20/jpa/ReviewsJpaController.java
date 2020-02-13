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
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jeffrey Boisvert
 */
public class ReviewsJpaController implements Serializable {

    public ReviewsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reviews reviews) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reviews reviews) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = reviews.getReviewId();
                if (findReviews(id) == null) {
                    throw new NonexistentEntityException("The reviews with id " + id + " no longer exists.");
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
            em = getEntityManager();
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
        EntityManager em = getEntityManager();
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
        EntityManager em = getEntityManager();
        try {
            return em.find(Reviews.class, id);
        } finally {
            em.close();
        }
    }

    public int getReviewsCount() {
        EntityManager em = getEntityManager();
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
    
}
