package com.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.entities.SurveyQuestions;
import com.gb4w20.entities.SurveyResponses;
import com.gb4w20.entities.SurveyResponses_;
import com.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.Collection;
import java.util.List;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used as a data access object between taxes entities.
 *
 * @author Jean Robatto, Yasseen Semlali
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
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in controller method.", ex);
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
            utx.begin();
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
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with edit in authors controller method.", ex);
            throw ex;
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
            return em.find(SurveyResponses.class, id);
    }

    /**
     * Increments the number of votes a survey response has
     * @author Yasseen Semlali
     */
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
    
    /**
     * Returns a list of all answers for a question
     * 
     * @param question
     * @return 
     * @author Jean Robatto, Jeffrey Boisvert
     */
    public Collection<SurveyResponses> getResponsesFromQuestion(SurveyQuestions question) {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SurveyResponses> cq = cb.createQuery(SurveyResponses.class);

        Root<SurveyResponses> response = cq.from(SurveyResponses.class);
        
        cq.select(response).where(
                //Added logic to only get enabled survey responses - Jeffrey 
                cb.and(
                  cb.equal(response.get(SurveyResponses_.surveyQuestionId), question),
                  cb.isTrue(response.get(SurveyResponses_.enabled))
            )
        );
        
        Query query = em.createQuery(cq);

        return query.getResultList();
    }
}
