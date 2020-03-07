
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.RssFeeds;
import com.gb4w20.gb4w20.exceptions.BackendException;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with the rssfeeds table in the database. 
 * 
 * @author Jeffrey Boisvert, Jean Robatto
 */
@Named
@SessionScoped
public class RssFeedsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(RssFeedsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(RssFeeds rssFeeds) throws BackendException {
        try {
            utx.begin();
            em.persist(rssFeeds);
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in authors controller method.", ex);
            throw new BackendException("Error in create method in authors controller.");
        }
    }

    public void edit(RssFeeds rssFeeds) throws NonexistentEntityException, Exception {
        try {
            utx.begin();
            rssFeeds = em.merge(rssFeeds);
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
            return em.find(RssFeeds.class, id);
    }

    public int getRssFeedsCount() {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RssFeeds> rt = cq.from(RssFeeds.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
    }
    
}
