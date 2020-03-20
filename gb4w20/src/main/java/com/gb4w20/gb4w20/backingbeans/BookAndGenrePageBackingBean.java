/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 * <h1>Book and Genre Backing Bean</h1>
 * <p>
 * This is the backing bean for setting the ISBN of a book and ID 
 * of a genre in a session scope so they can be used in a viewParam
 * instead of using the actual query string in the URL
 * </p>
 *
 * @author Jasmar Badion
 */
@Named("bookAndGenre")
@SessionScoped
public class BookAndGenrePageBackingBean implements Serializable {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(BookAndGenrePageBackingBean.class);
    
    //for the book ISBN
    private Long isbn;
    //for the genre ID
    private Long id;
    
    /**
     * Getter for ISBN
     * @return 
     */
    public Long getIsbn(){
        return this.isbn;
    }
    
    /**
     * Setter for ISBN
     * @param isbn 
     */
    public void setIsbn(Long isbn){
        this.isbn = isbn;
    }
    
    /**
     * Getter for ID
     * @return 
     */
    public Long getId(){
        return this.id;
    }
    
    /**
     * Setter for ID
     * @param id 
     */
    public void setId(Long id){
        this.id = id;
    }
}
