/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
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
    
    @Inject
    private ReviewsJpaController reviewsJpaController;
    //@Inject 
    //private BooksJpaController booksJpaController;

    private String review;
    private short rating;
    //private Reviews reviews;
    
    public void makeReview(Books book, Users user){
        Reviews reviews = new Reviews();
        reviews.setReview(this.review);
        reviews.setRating(this.rating); 
        LOG.debug(book.getIsbn() + "");
        reviews.setIsbn(book);
        reviews.setUserId(user);
        saveReview(reviews);
    }
    
    private void saveReview(Reviews reviews){
        this.reviewsJpaController.create(reviews);
        LOG.debug("<<<<<<<<<<<<<<<<SUCCESS>>>>>>>>>>>>>>>>>");
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
}