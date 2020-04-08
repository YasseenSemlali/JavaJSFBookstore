package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.jpa.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.ReviewsJpaController;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import com.gb4w20.gb4w20.jsf.validation.JSFFormMessageValidator;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 * <h1>Book Review Backing Bean</h1>
 * <p>
 * This is the backing bean for book review 
 * when a logged in user is submitting a review of a book
 * in the book page
 * </p>
 * 
 * @author Jasmar Badion
 */
@Named
@RequestScoped
public class BookReviewBackingBean implements Serializable{
    
    private final static Logger LOG = LoggerFactory.getLogger(BookReviewBackingBean.class);
    
    @Inject
    private ReviewsJpaController reviewsJpaController;
    @Inject 
    private BooksJpaController booksJpaController;
    @Inject
    private UsersJpaController usersJpaController;
    
    @Inject
    private JSFFormMessageValidator validator;

    private String review;
    private short rating = 1; //default value is 1 star since it's the lowest rate
    
    /**
     * Makes a review setting up its instances depending 
     * on the user inputs
     * 
     * @param book
     * @param user
     * @throws RollbackFailureException 
     */
    public void makeReview(Long book, Long user) throws RollbackFailureException{   
        Reviews reviews = new Reviews();
        reviews.setReview(this.review);
        reviews.setRating(this.rating); 
        reviews.setApprovedStatus(false); //false for now until the manager approves it
        
        //Creating current timestamp 
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        
        reviews.setTimestamp(timestamp);
        reviews.setIsbn(this.booksJpaController.findBooks(book));
        reviews.setUserId(this.usersJpaController.findUsers(user));
        saveReview(reviews);
    }
    
    /**
     * This is called after making the review from the 
     * book page to be able to save it 
     * 
     * @param reviews
     * @throws RollbackFailureException 
     */
    private void saveReview(Reviews reviews) {
        //dialog from PrimeFaces is used depending on the outcome of this method
        PrimeFaces reviewDialog = PrimeFaces.current();
        try {
            this.reviewsJpaController.create(reviews);
            reviewDialog.executeScript("PF('succeed').show()");
            clearReviewsInputs();
        } catch (BackendException | RollbackFailureException ex) {
            LOG.info(ex.toString());
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
            } catch (IOException ioex) {
                LOG.info("Problem with redirection: " + ioex.toString());
            }
        }
    }
  
    /**
     * Getter for review
     * @return 
     */
    public String getReview(){
        return this.review;
    }
    
    /**
     * Setter for review
     * @param review 
     */
    public void setReview(String review){
        this.review = review;
    }
    
    /**
     * Getter for rating
     * @return 
     */
    public short getRating(){
        return this.rating;
    }
    
    /**
     * Setter fir rating
     * @param rating 
     */
    public void setRating(short rating){
        this.rating = rating;
    }
    
    /**
     * Clears the inputs of review and rating once it's submitted
     */
    private void clearReviewsInputs(){
        this.review = null;
        this.rating = 1;
    }
    
    /**
     * Validates the review if it's empty or not
     * @param fc
     * @param c
     * @param value 
     */
    public void validateReview(FacesContext fc, UIComponent c, Object value) {
        this.validator.validateIsNotBlank((String) value);
    }
}
