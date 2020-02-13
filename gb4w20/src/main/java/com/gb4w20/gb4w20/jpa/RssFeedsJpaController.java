/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.RssFeeds;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Jeffrey Boisvert
 */
public class RssFeedsJpaController implements Serializable {

    public RssFeedsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RssFeeds rssFeeds) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rssFeeds);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RssFeeds rssFeeds) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rssFeeds = em.merge(rssFeeds);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = rssFeeds.getId();
                if (findRssFeeds(id) == null) {
                    throw new NonexistentEntityException("The rssFeeds with id " + id + " no longer exists.");
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
            RssFeeds rssFeeds;
            try {
                rssFeeds = em.getReference(RssFeeds.class, id);
                rssFeeds.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rssFeeds with id " + id + " no longer exists.", enfe);
            }
            em.remove(rssFeeds);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RssFeeds> findRssFeedsEntities() {
        return findRssFeedsEntities(true, -1, -1);
    }

    public List<RssFeeds> findRssFeedsEntities(int maxResults, int firstResult) {
        return findRssFeedsEntities(false, maxResults, firstResult);
    }

    private List<RssFeeds> findRssFeedsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RssFeeds.class));
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

    public RssFeeds findRssFeeds(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RssFeeds.class, id);
        } finally {
            em.close();
        }
    }

    public int getRssFeedsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RssFeeds> rt = cq.from(RssFeeds.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
