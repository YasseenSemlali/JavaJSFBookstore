package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.jpa.ReviewsJpaController;
import java.io.IOException;
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
    
    private final static Logger LOG = LoggerFactory.getLogger(ManagerReviews.class);
    
    @Inject
    private ReviewsJpaController reviewController;
    
    private Integer activeTabIndex = 0;

    /**
     * Method to set the status of a review
     * 
     * @param revId
     * @param status
     * @param activeTabIndex
     * @throws java.io.IOException
     */
    public void editApprovalStatus(Long revId, boolean status, Integer activeTabIndex) throws IOException {
        LOG.debug("Editing approval status or review with id " + Long.toString(revId));
        try {
            Reviews review = reviewController.findReviews(revId);
            review.setApprovedStatus(status);
            reviewController.edit(review);
            
            this.activeTabIndex = activeTabIndex;
            
        } catch (Exception ex) {
            LOG.info(ex.toString());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
        }
    }

    public Integer getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(Integer activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }
    
    
}
