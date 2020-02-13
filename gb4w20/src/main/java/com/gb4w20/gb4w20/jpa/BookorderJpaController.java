/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Bookorder;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jeffrey Boisvert
 */
public class BookorderJpaController implements Serializable {

    public BookorderJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bookorder bookorder) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Books isbn = bookorder.getIsbn();
            if (isbn != null) {
                isbn = em.getReference(isbn.getClass(), isbn.getIsbn());
                bookorder.setIsbn(isbn);
            }
            Orders orderId = bookorder.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getOrderId());
                bookorder.setOrderId(orderId);
            }
            em.persist(bookorder);
            if (isbn != null) {
                isbn.getBookorderCollection().add(bookorder);
                isbn = em.merge(isbn);
            }
            if (orderId != null) {
                orderId.getBookorderCollection().add(bookorder);
                orderId = em.merge(orderId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bookorder bookorder) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bookorder persistentBookorder = em.find(Bookorder.class, bookorder.getBookorderId());
            Books isbnOld = persistentBookorder.getIsbn();
            Books isbnNew = bookorder.getIsbn();
            Orders orderIdOld = persistentBookorder.getOrderId();
            Orders orderIdNew = bookorder.getOrderId();
            if (isbnNew != null) {
                isbnNew = em.getReference(isbnNew.getClass(), isbnNew.getIsbn());
                bookorder.setIsbn(isbnNew);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getOrderId());
                bookorder.setOrderId(orderIdNew);
            }
            bookorder = em.merge(bookorder);
            if (isbnOld != null && !isbnOld.equals(isbnNew)) {
                isbnOld.getBookorderCollection().remove(bookorder);
                isbnOld = em.merge(isbnOld);
            }
            if (isbnNew != null && !isbnNew.equals(isbnOld)) {
                isbnNew.getBookorderCollection().add(bookorder);
                isbnNew = em.merge(isbnNew);
            }
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getBookorderCollection().remove(bookorder);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getBookorderCollection().add(bookorder);
                orderIdNew = em.merge(orderIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = bookorder.getBookorderId();
                if (findBookorder(id) == null) {
                    throw new NonexistentEntityException("The bookorder with id " + id + " no longer exists.");
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
            Bookorder bookorder;
            try {
                bookorder = em.getReference(Bookorder.class, id);
                bookorder.getBookorderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bookorder with id " + id + " no longer exists.", enfe);
            }
            Books isbn = bookorder.getIsbn();
            if (isbn != null) {
                isbn.getBookorderCollection().remove(bookorder);
                isbn = em.merge(isbn);
            }
            Orders orderId = bookorder.getOrderId();
            if (orderId != null) {
                orderId.getBookorderCollection().remove(bookorder);
                orderId = em.merge(orderId);
            }
            em.remove(bookorder);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bookorder> findBookorderEntities() {
        return findBookorderEntities(true, -1, -1);
    }

    public List<Bookorder> findBookorderEntities(int maxResults, int firstResult) {
        return findBookorderEntities(false, maxResults, firstResult);
    }

    private List<Bookorder> findBookorderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bookorder.class));
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

    public Bookorder findBookorder(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bookorder.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookorderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bookorder> rt = cq.from(Bookorder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
