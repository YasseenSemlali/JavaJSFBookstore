/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.ReviewsJpaController;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
    
    //private Reviews reviews;
    protected String review;
    protected short rating;
    
    @Inject
    private ReviewsJpaController reviewsJpaController;
    
    public BookReviewBackingBean(){}
    /**
     * Creates a review created by a logged in user to a specific book
     * in the book page
     * 
     * @param book
     * @param user
     * @return 
     */
    public void getReview(Books book, Users user){
        LOG.info("Review being created from the book page");
        
        /*if(this.reviews == null){
            this.reviews = new Reviews();  
            this.reviews.setIsbn(book);
            this.reviews.setUserId(user);
        }
        return this.reviews; */
        Reviews rev = new Reviews();
        rev.setReview(review);
        rev.setRating(rating);
        rev.setUserId(user);
        rev.setIsbn(book);
        
        reviewsJpaController.create(rev);
        
    }
    public String getReview(){
        return this.review;
    }
    public void setReview(String review){
        this.review = review;
    }
    public short getRating(){
        return this.rating;
    }
    public void setRating(short rating){
        this.rating = rating;
    }
    /**
     * Getter for reviews
     * @return 
     */
    /*public Reviews getReviews(){
        return this.reviews;
    }*/
    
    /**
     * Setter for reviews
     * @param reviews 
     */
    /*public void setReviews(Reviews reviews){
        this.reviews = reviews;
    }*/
}
