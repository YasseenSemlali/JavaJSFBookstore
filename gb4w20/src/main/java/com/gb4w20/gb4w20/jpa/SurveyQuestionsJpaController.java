package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.SurveyQuestions;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.SurveyResponses;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with survey questions table in the database.
 *
 * @author Jeffrey Boisvert, Yasseen Semlali
 */
@Named
@SessionScoped
public class SurveyQuestionsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(SurveyQuestionsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(SurveyQuestions surveyQuestions) {
        if (surveyQuestions.getSurveyResponsesCollection() == null) {
            surveyQuestions.setSurveyResponsesCollection(new ArrayList<SurveyResponses>());
        }
        em.getTransaction().begin();
        Collection<SurveyResponses> attachedSurveyResponsesCollection = new ArrayList<SurveyResponses>();
        for (SurveyResponses surveyResponsesCollectionSurveyResponsesToAttach : surveyQuestions.getSurveyResponsesCollection()) {
            surveyResponsesCollectionSurveyResponsesToAttach = em.getReference(surveyResponsesCollectionSurveyResponsesToAttach.getClass(), surveyResponsesCollectionSurveyResponsesToAttach.getId());
            attachedSurveyResponsesCollection.add(surveyResponsesCollectionSurveyResponsesToAttach);
        }
        surveyQuestions.setSurveyResponsesCollection(attachedSurveyResponsesCollection);
        em.persist(surveyQuestions);
        for (SurveyResponses surveyResponsesCollectionSurveyResponses : surveyQuestions.getSurveyResponsesCollection()) {
            SurveyQuestions oldSurveyQuestionIdOfSurveyResponsesCollectionSurveyResponses = surveyResponsesCollectionSurveyResponses.getSurveyQuestionId();
            surveyResponsesCollectionSurveyResponses.setSurveyQuestionId(surveyQuestions);
            surveyResponsesCollectionSurveyResponses = em.merge(surveyResponsesCollectionSurveyResponses);
            if (oldSurveyQuestionIdOfSurveyResponsesCollectionSurveyResponses != null) {
                oldSurveyQuestionIdOfSurveyResponsesCollectionSurveyResponses.getSurveyResponsesCollection().remove(surveyResponsesCollectionSurveyResponses);
                oldSurveyQuestionIdOfSurveyResponsesCollectionSurveyResponses = em.merge(oldSurveyQuestionIdOfSurveyResponsesCollectionSurveyResponses);
            }
        }
        em.getTransaction().commit();
    }

    public void edit(SurveyQuestions surveyQuestions) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            utx.begin();
            SurveyQuestions persistentSurveyQuestions = em.find(SurveyQuestions.class, surveyQuestions.getId());
            Collection<SurveyResponses> surveyResponsesCollectionOld = persistentSurveyQuestions.getSurveyResponsesCollection();
            Collection<SurveyResponses> surveyResponsesCollectionNew = surveyQuestions.getSurveyResponsesCollection();
            List<String> illegalOrphanMessages = null;
            for (SurveyResponses surveyResponsesCollectionOldSurveyResponses : surveyResponsesCollectionOld) {
                if (!surveyResponsesCollectionNew.contains(surveyResponsesCollectionOldSurveyResponses)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SurveyResponses " + surveyResponsesCollectionOldSurveyResponses + " since its surveyQuestionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SurveyResponses> attachedSurveyResponsesCollectionNew = new ArrayList<SurveyResponses>();
            for (SurveyResponses surveyResponsesCollectionNewSurveyResponsesToAttach : surveyResponsesCollectionNew) {
                surveyResponsesCollectionNewSurveyResponsesToAttach = em.getReference(surveyResponsesCollectionNewSurveyResponsesToAttach.getClass(), surveyResponsesCollectionNewSurveyResponsesToAttach.getId());
                attachedSurveyResponsesCollectionNew.add(surveyResponsesCollectionNewSurveyResponsesToAttach);
            }
            surveyResponsesCollectionNew = attachedSurveyResponsesCollectionNew;
            surveyQuestions.setSurveyResponsesCollection(surveyResponsesCollectionNew);
            surveyQuestions = em.merge(surveyQuestions);
            for (SurveyResponses surveyResponsesCollectionNewSurveyResponses : surveyResponsesCollectionNew) {
                if (!surveyResponsesCollectionOld.contains(surveyResponsesCollectionNewSurveyResponses)) {
                    SurveyQuestions oldSurveyQuestionIdOfSurveyResponsesCollectionNewSurveyResponses = surveyResponsesCollectionNewSurveyResponses.getSurveyQuestionId();
                    surveyResponsesCollectionNewSurveyResponses.setSurveyQuestionId(surveyQuestions);
                    surveyResponsesCollectionNewSurveyResponses = em.merge(surveyResponsesCollectionNewSurveyResponses);
                    if (oldSurveyQuestionIdOfSurveyResponsesCollectionNewSurveyResponses != null && !oldSurveyQuestionIdOfSurveyResponsesCollectionNewSurveyResponses.equals(surveyQuestions)) {
                        oldSurveyQuestionIdOfSurveyResponsesCollectionNewSurveyResponses.getSurveyResponsesCollection().remove(surveyResponsesCollectionNewSurveyResponses);
                        oldSurveyQuestionIdOfSurveyResponsesCollectionNewSurveyResponses = em.merge(oldSurveyQuestionIdOfSurveyResponsesCollectionNewSurveyResponses);
                    }
                }
            }
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

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        em.getTransaction().begin();
        SurveyQuestions surveyQuestions;
        try {
            surveyQuestions = em.getReference(SurveyQuestions.class, id);
            surveyQuestions.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The surveyQuestions with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        Collection<SurveyResponses> surveyResponsesCollectionOrphanCheck = surveyQuestions.getSurveyResponsesCollection();
        for (SurveyResponses surveyResponsesCollectionOrphanCheckSurveyResponses : surveyResponsesCollectionOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This SurveyQuestions (" + surveyQuestions + ") cannot be destroyed since the SurveyResponses " + surveyResponsesCollectionOrphanCheckSurveyResponses + " in its surveyResponsesCollection field has a non-nullable surveyQuestionId field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        em.remove(surveyQuestions);
        em.getTransaction().commit();
    }

    public List<SurveyQuestions> findSurveyQuestionsEntities() {
        return findSurveyQuestionsEntities(true, -1, -1);
    }

    public List<SurveyQuestions> findSurveyQuestionsEntities(int maxResults, int firstResult) {
        return findSurveyQuestionsEntities(false, maxResults, firstResult);
    }

    private List<SurveyQuestions> findSurveyQuestionsEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SurveyQuestions.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public SurveyQuestions findSurveyQuestions(Long id) {
        return em.find(SurveyQuestions.class, id);
    }

    public int getSurveyQuestionsCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<SurveyQuestions> rt = cq.from(SurveyQuestions.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public SurveyQuestions getActiveQuestion() {
        LOG.info("getting active surveyQuestion");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SurveyQuestions> cq = cb.createQuery(SurveyQuestions.class);

        Root<SurveyQuestions> question = cq.from(SurveyQuestions.class);
        cq.select(question).where(cb.isTrue(question.get("enabled")));

        Query query = em.createQuery(cq);

        List<SurveyQuestions> result = query.getResultList();
        
        if(result.size() == 0) {
            LOG.warn("No active survey question");
        } else if(result.size() > 1) {
            LOG.warn("More than 1 active survey question, returning only 1");
        }
        return result.size() == 0 ? null : result.get(0);
    }

}
