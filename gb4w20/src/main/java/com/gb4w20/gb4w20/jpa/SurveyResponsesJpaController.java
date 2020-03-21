package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.SurveyQuestions;
import com.gb4w20.gb4w20.entities.SurveyResponses;
import com.gb4w20.gb4w20.entities.SurveyResponses_;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.eclipse.persistence.exceptions.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used as a data access object between taxes entities.
 *
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class SurveyResponsesJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(SurveyResponsesJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    /**
     * Used to create a survey response object in the database.
     *
     * @param surveyResponses
     */
    public void create(SurveyResponses surveyResponses) {
        try {
            em.getTransaction().begin();
            SurveyQuestions surveyQuestionId = surveyResponses.getSurveyQuestionId();
            if (surveyQuestionId != null) {
                surveyQuestionId = em.getReference(surveyQuestionId.getClass(), surveyQuestionId.getId());
                surveyResponses.setSurveyQuestionId(surveyQuestionId);
            }
            em.persist(surveyResponses);
            if (surveyQuestionId != null) {
                surveyQuestionId.getSurveyResponsesCollection().add(surveyResponses);
                surveyQuestionId = em.merge(surveyQuestionId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Allows to update a survey response in the database.
     *
     * @param surveyResponses
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(SurveyResponses surveyResponses) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            SurveyResponses persistentSurveyResponses = em.find(SurveyResponses.class, surveyResponses.getId());
            SurveyQuestions surveyQuestionIdOld = persistentSurveyResponses.getSurveyQuestionId();
            SurveyQuestions surveyQuestionIdNew = surveyResponses.getSurveyQuestionId();
            if (surveyQuestionIdNew != null) {
                surveyQuestionIdNew = em.getReference(surveyQuestionIdNew.getClass(), surveyQuestionIdNew.getId());
                surveyResponses.setSurveyQuestionId(surveyQuestionIdNew);
            }
            surveyResponses = em.merge(surveyResponses);
            if (surveyQuestionIdOld != null && !surveyQuestionIdOld.equals(surveyQuestionIdNew)) {
                surveyQuestionIdOld.getSurveyResponsesCollection().remove(surveyResponses);
                surveyQuestionIdOld = em.merge(surveyQuestionIdOld);
            }
            if (surveyQuestionIdNew != null && !surveyQuestionIdNew.equals(surveyQuestionIdOld)) {
                surveyQuestionIdNew.getSurveyResponsesCollection().add(surveyResponses);
                surveyQuestionIdNew = em.merge(surveyQuestionIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = surveyResponses.getId();
                if (findSurveyResponses(id) == null) {
                    throw new NonexistentEntityException("The surveyResponses with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Used to delete a survey response from the database.
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void destroy(Long id) throws NonexistentEntityException {
        try {
            em.getTransaction().begin();
            SurveyResponses surveyResponses;
            try {
                surveyResponses = em.getReference(SurveyResponses.class, id);
                surveyResponses.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The surveyResponses with id " + id + " no longer exists.", enfe);
            }
            SurveyQuestions surveyQuestionId = surveyResponses.getSurveyQuestionId();
            if (surveyQuestionId != null) {
                surveyQuestionId.getSurveyResponsesCollection().remove(surveyResponses);
                surveyQuestionId = em.merge(surveyQuestionId);
            }
            em.remove(surveyResponses);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Returns a list of all the available survey responses.
     *
     * @return a list of survey responses.
     */
    public List<SurveyResponses> findSurveyResponsesEntities() {
        return findSurveyResponsesEntities(true, -1, -1);
    }

    /**
     * Used to get a list of survey responses given the max results and where to
     * start for paging purposes.
     *
     * @param maxResults
     * @param firstResult
     * @return a list of survey reponses.
     */
    public List<SurveyResponses> findSurveyResponsesEntities(int maxResults, int firstResult) {
        return findSurveyResponsesEntities(false, maxResults, firstResult);
    }

    /**
     * Used to get a certain number of survey responses which allows paging.
     *
     * @param all true to return all results false otherwise.
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<SurveyResponses> findSurveyResponsesEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SurveyResponses.class));
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

    /**
     * Used to find a survey response that matches the given id.
     *
     * @param id
     * @return SurveyResponses of the given survey.
     */
    public SurveyResponses findSurveyResponses(Long id) {
        try {
            return em.find(SurveyResponses.class, id);
        } finally {
            em.close();
        }
    }

    public void voteForQuestion(long id) throws RollbackFailureException {
        try {
            LOG.debug("Voting for " + id);
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<SurveyResponses> update = cb.createCriteriaUpdate(SurveyResponses.class);
            
            Root response = update.from(SurveyResponses.class);
            
            update.set(SurveyResponses_.count, cb.sum(response.get(SurveyResponses_.count), 1));
            update.where(cb.equal(response.get(SurveyResponses_.id), id));
            
            utx.begin();
            em.createQuery(update).executeUpdate();
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
}
