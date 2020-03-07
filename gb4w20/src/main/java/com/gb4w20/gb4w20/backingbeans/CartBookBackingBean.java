/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Cart Book Backing Bean</h1>
 * <p>
 * This is the backing bean for adding books to the cart by adding them to the
 * session.
 * </p>
 *
 * @author Jasmar Badion
 */
@Named("cartSession")
@SessionScoped
public class CartBookBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(CartBookBackingBean.class);
    //HashSet is used so that cart only has unique books meaning
    //user cannot add the same book to the cart, user can still add amount of 
    //that book in the cart page
    private HashSet<Books> books;
    private BigDecimal total;

    @Inject
    private BooksJpaController bookJpaController;

    /**
     * Default constructor initializing the HashSet
     */
    public CartBookBackingBean() {
        this.books = new HashSet<Books>();
        this.total = new BigDecimal(0);
    }
    
    /**
     * Getter for books
     * @return 
     */
    public HashSet<Books> getBooks() {
        return this.books;
    }
    
    /**
     * Setter for the books
     * @param books 
     */
    public void setBooks(HashSet<Books> books){
        this.books = books;
    }
    
    /**
     * Getter for total
     * @return 
     */
    public BigDecimal getTotal(){
        return this.total;
    }
    
    /**
     * Setter for total
     * @param total 
     */
    public void setTotal(BigDecimal total){
        this.total = total;
    }

    /**
     * Adding book to the books list that will be in 
     * session scope
     * @param isbn
     * @return 
     */
    public boolean addBookToSession(Long isbn) {
        LOG.info(isbn + " being added to the cart");
        return this.books.add(this.bookJpaController.findBooks(isbn));
    }
    
    /**
     * Removing the book from the books list that will be in 
     * session scope
     * @param book
     * @return 
     */
    public boolean removeBookFromSession(Books book){
        LOG.info(book.getIsbn() + " being removed from the ccart");
        return this.books.remove(book);
    }
    
    /**
     * If there are books in the cart, this will calculate
     * the total amount of the book or books in the cart
     * that the user will need to pay
     * @return 
     */
    public BigDecimal calculateTotalAmount(){
        if (!this.books.isEmpty()){
            BigDecimal listSale;
            this.total = new BigDecimal(0);
             for (Books book : this.books){
                 listSale = book.getListPrice().subtract(book.getSalePrice());
                 LOG.debug("listsale is " + listSale);
                 this.total = this.total.add(listSale);
                 LOG.debug("total is " + this.total);
             }
             return this.total;
        }
        else{
            return new BigDecimal(0);
        }
    } 
}
