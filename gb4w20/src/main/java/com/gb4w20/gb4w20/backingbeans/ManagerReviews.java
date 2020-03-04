package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.jpa.ReviewsJpaController;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to manage the manager reviews page. Used to approve or disapprove reviews.
 *
 * @author Jean Robatto
 */
@Named
@SessionScoped
public class ManagerReviews implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);
    
    @Inject
    private ReviewsJpaController reviewController;

    /**
     * Method to set the status of a review
     * 
     * @param revId
     * @param status
     */
    public void editApprovalStatus(Long revId, boolean status) {
        try {
            Reviews review = reviewController.findReviews(revId);
            review.setApprovedStatus(status);
            reviewController.edit(review);
            FacesContext.getCurrentInstance().getExternalContext().redirect("manager-reviews.xhtml");
        } catch (Exception ex) {
            LOG.info(ex.toString());
        }
    }

    

}