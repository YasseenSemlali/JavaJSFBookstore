
package com.gb4w20.jpa;

import com.gb4w20.entities.Taxes;
import com.gb4w20.entities.Taxes_;
import com.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.jpa.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
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
 * Used as a data access object between taxes entities.
 * 
 * @author Jeffrey Boisvert
 */
@Named
@SessionScoped
public class TaxesJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(TaxesJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;
    
    /**
     * Used to insert a taxes object into the database. 
     * 
     * @param taxes to be inserted
     * @throws PreexistingEntityException
     * @throws Exception 
     */
    public void create(Taxes taxes) throws PreexistingEntityException, Exception {
        try {
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
    
    /**
     * Used to edit a taxes object in the database.
     * 
     * @param taxes
     * @throws NonexistentEntityException
     * @throws Exception 
     */
    public void edit(Taxes taxes) throws NonexistentEntityException, Exception {
        try {
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
    
    /**
     * Used to delete taxes from the table. 
     * 
     * @param id
     * @throws NonexistentEntityException 
     */
    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
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
    
    /**
     * Used to find all taxes in the table.
     * 
     * @return a list of all the taxes. 
     */
    public List<Taxes> findTaxesEntities() {
        return findTaxesEntities(true, -1, -1);
    }
    
    /**
     * Used to get a certain number of taxes which allows paging. 
     * 
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<Taxes> findTaxesEntities(int maxResults, int firstResult) {
        return findTaxesEntities(false, maxResults, firstResult);
    }
    
    /**
     * Used to get a certain number of taxes which allows paging. 
     * 
     * @param all true to return all results false otherwise. 
     * @param maxResults
     * @param firstResult
     * @return 
     */
    private List<Taxes> findTaxesEntities(boolean all, int maxResults, int firstResult) {
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
    
    /**
     * Used to find taxes with a particular id.
     * @param id
     * @return 
     */
    public Taxes findTaxes(String id) {
        try {
            return em.find(Taxes.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Returns a tax obj based on province
     * 
     * @author Jean Robatto
     * @param inputOrder
     * @return 
     */
    public Taxes findByProvince(String province) {
        try {
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Taxes> cq = cb.createQuery(Taxes.class);

            Root<Taxes> tax = cq.from(Taxes.class);
            
            cq.where(cb.equal(tax.get(Taxes_.province), province));
                
            Query query = em.createQuery(cq);
            return (Taxes) query.getSingleResult();
            
        } catch (Exception e) {
            return null;
        }

    }
    
}
