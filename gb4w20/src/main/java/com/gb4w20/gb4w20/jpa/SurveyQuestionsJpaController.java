
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.SurveyQuestions;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.SurveyResponses;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with survey questions table in the database. 
 * @author Jeffrey Boisvert
 */
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
        try {
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SurveyQuestions surveyQuestions) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = surveyQuestions.getId();
                if (findSurveyQuestions(id) == null) {
                    throw new NonexistentEntityException("The surveyQuestions with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        try {
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SurveyQuestions> findSurveyQuestionsEntities() {
        return findSurveyQuestionsEntities(true, -1, -1);
    }

    public List<SurveyQuestions> findSurveyQuestionsEntities(int maxResults, int firstResult) {
        return findSurveyQuestionsEntities(false, maxResults, firstResult);
    }

    private List<SurveyQuestions> findSurveyQuestionsEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SurveyQuestions.class));
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

    public SurveyQuestions findSurveyQuestions(Long id) {
        try {
            return em.find(SurveyQuestions.class, id);
        } finally {
            em.close();
        }
    }

    public int getSurveyQuestionsCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SurveyQuestions> rt = cq.from(SurveyQuestions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
