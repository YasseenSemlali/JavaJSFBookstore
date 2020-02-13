package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.SurveyQuestions;
import com.gb4w20.gb4w20.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
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
 *
 * @author Yasseen
 */
@Named
@SessionScoped
public class SurveyQuestionsActionBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(SurveyQuestionsActionBean.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public SurveyQuestionsActionBean() {
    }

    public void create(SurveyQuestions surveyQuestion) throws RollbackFailureException {
        try {
            utx.begin();
            em.persist(surveyQuestion);
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
    
    public void edit(SurveyQuestions surveyQuestion) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            surveyQuestion = em.merge(surveyQuestion);
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = surveyQuestion.getId();
                if (findSurveyQuestion(id) == null) {
                    throw new NonexistentEntityException("The surveyQuestion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }
    

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            SurveyQuestions surveyQuestion;
            try {
                surveyQuestion = em.getReference(SurveyQuestions.class, id);
                surveyQuestion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The surveyQuestion with id " + id + " no longer exists.", enfe);
            }
            em.remove(surveyQuestion);
            utx.commit();
        } catch (NonexistentEntityException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<SurveyQuestions> getAllSurveyQuestions() {
        LOG.info("getting all surveyQuestions");

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SurveyQuestions.class));
        Query q = em.createQuery(cq);
        
        return q.getResultList();
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

    public SurveyQuestions findSurveyQuestion(Long id) {
        return em.find(SurveyQuestions.class, id);
    }

    public int getSurveyQuestionCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<SurveyQuestions> rt = cq.from(SurveyQuestions.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
