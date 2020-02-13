/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.jpa;

import com.gb4w20.gb4w20.entities.Taxes;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.jpa.exceptions.PreexistingEntityException;
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
public class TaxesJpaController implements Serializable {

    public TaxesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Taxes taxes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(taxes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTaxes(taxes.getProvince()) != null) {
                throw new PreexistingEntityException("Taxes " + taxes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Taxes taxes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            taxes = em.merge(taxes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = taxes.getProvince();
                if (findTaxes(id) == null) {
                    throw new NonexistentEntityException("The taxes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Taxes taxes;
            try {
                taxes = em.getReference(Taxes.class, id);
                taxes.getProvince();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The taxes with id " + id + " no longer exists.", enfe);
            }
            em.remove(taxes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Taxes> findTaxesEntities() {
        return findTaxesEntities(true, -1, -1);
    }

    public List<Taxes> findTaxesEntities(int maxResults, int firstResult) {
        return findTaxesEntities(false, maxResults, firstResult);
    }

    private List<Taxes> findTaxesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Taxes.class));
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

    public Taxes findTaxes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Taxes.class, id);
        } finally {
            em.close();
        }
    }

    public int getTaxesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Taxes> rt = cq.from(Taxes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
