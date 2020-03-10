package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.RssFeeds;
import com.gb4w20.gb4w20.entities.RssFeeds_;
import com.gb4w20.gb4w20.entities.SurveyQuestions;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with the rssfeeds table in the database.
 *
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class RssFeedsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(RssFeedsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(RssFeeds rssFeeds) {
        try {
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
        try {
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
        try {
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
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(RssFeeds.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public RssFeeds findRssFeeds(Long id) {
        try {
            return em.find(RssFeeds.class, id);
        } finally {
            em.close();
        }
    }

    public int getRssFeedsCount() {
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
    
     public RssFeeds getActiveFeed() {
        LOG.info("getting active rssFeed");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RssFeeds> cq = cb.createQuery(RssFeeds.class);

        Root<RssFeeds> feed = cq.from(RssFeeds.class);
        cq.select(feed).where(cb.isTrue(feed.get(RssFeeds_.enabled)));

        Query query = em.createQuery(cq);

        List<RssFeeds> result = query.getResultList();
        
        if(result.size() == 0) {
            LOG.warn("No active rss feed");
        } else if(result.size() > 1) {
            LOG.warn("More than 1 active rss feed, returning only 1");
        }
        return result.size() == 0 ? null : result.get(0);
    }

}
