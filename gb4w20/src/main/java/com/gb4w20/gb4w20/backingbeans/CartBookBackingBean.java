/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.io.Serializable;
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

    @Inject
    private BooksJpaController bookJpaController;

    /**
     * Default constructor initializing the HashSet
     */
    public CartBookBackingBean() {
        this.books = new HashSet<Books>();
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
}
    /**
     *
     */
    /*public void addIsbnToCookie(Long isbn) {
        this.isbn = isbn;
        FacesContext context = FacesContext.getCurrentInstance();
        Cookie cart = (Cookie) context.getExternalContext().getRequestCookieMap().get("isbn");
        cart.getValue().split(",");
        cart.setPath("/");
        context.getExternalContext().addResponseCookie("isbn", this.isbn.toString(), null);
    }*/

    /*public void writeIsbnToCookie(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie("isbn", this.isbn.toString(), null);
    }*/

    /**
     *
     */
    /*public void checkBooksInCookie() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookieMap = null;

        if (cookieMap == null || cookieMap.isEmpty()) {
            LOG.info("No cookies");
        } else {
            ArrayList<Object> ac = new ArrayList<>(cookieMap.values());

            // Streams coding to print out the contenst of the cookies found
            ac.stream().map((c) -> {
                LOG.info(((Cookie) c).getName());
                return c;
            }).forEach((c) -> {
                LOG.info(((Cookie) c).getValue());
            });
        }

        //to get a specific cookie
        Object book_cookie = context.getExternalContext().getRequestCookieMap().get("isbn");
        if (book_cookie != null) {
            LOG.info(((Cookie) book_cookie).getName());
            LOG.info(((Cookie) book_cookie).getValue());
        }
        //writeIsbnToCookie(); //setMaxAge, setPath
    }
}*/
