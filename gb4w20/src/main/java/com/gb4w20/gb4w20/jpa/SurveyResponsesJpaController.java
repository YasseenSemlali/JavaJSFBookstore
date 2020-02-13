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
import com.gb4w20.gb4w20.entities.SurveyQuestions;
import com.gb4w20.gb4w20.entities.SurveyResponses;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jeffrey Boisvert
 */
public class SurveyResponsesJpaController implements Serializable {

    public SurveyResponsesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SurveyResponses surveyResponses) {
        EntityManager em = null;
        try {
            em = getEntityManager();
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

    public void edit(SurveyResponses surveyResponses) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
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

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
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

    public List<SurveyResponses> findSurveyResponsesEntities() {
        return findSurveyResponsesEntities(true, -1, -1);
    }

    public List<SurveyResponses> findSurveyResponsesEntities(int maxResults, int firstResult) {
        return findSurveyResponsesEntities(false, maxResults, firstResult);
    }

    private List<SurveyResponses> findSurveyResponsesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
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

    public SurveyResponses findSurveyResponses(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SurveyResponses.class, id);
        } finally {
            em.close();
        }
    }

    public int getSurveyResponsesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SurveyResponses> rt = cq.from(SurveyResponses.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
