/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.entities.Users;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    private Reviews reviews;
    
    //default constructor
    public BookReviewBackingBean(){}
    
    /**
     * Creates a review created by a logged in user to a specific book
     * in the book page
     * 
     * @param book
     * @param user
     * @return 
     */
    public Reviews getReview(Books book, Users user){
        LOG.info("Review being created from the book page");
        
        if(this.reviews == null){
            this.reviews = new Reviews();  
            this.reviews.setIsbn(book);
            this.reviews.setUserId(user);
        }
        return this.reviews; 
    }
    
    /**
     * Getter for reviews
     * @return 
     */
    public Reviews getReviews(){
        return this.reviews;
    }
    
    /**
     * Setter for reviews
     * @param reviews 
     */
    public void setReviews(Reviews reviews){
        this.reviews = reviews;
    }
}
