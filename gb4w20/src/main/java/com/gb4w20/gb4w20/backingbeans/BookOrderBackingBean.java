package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Taxes;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
import com.gb4w20.gb4w20.jpa.TaxesJpaController;
import com.gb4w20.gb4w20.jpa.exceptions.BackendException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * <h1>Book Order Backing Bean</h1>
 * <p>
 * This is the backing bean for book order
 * where all books purchased are added to book order
 * from a user
 * </p>
 * 
 * @author Jasmar Badion
 */
@Named
@SessionScoped
public class BookOrderBackingBean implements Serializable{
    
    private final static Logger LOG = LoggerFactory.getLogger(BookReviewBackingBean.class);

    @Inject
    private BookorderJpaController bookorderJpaController;
    @Inject
    private TaxesJpaController taxesJpaController;
    
    @Inject
    private UserSessionBean userSession;
    @Inject
    private CartBookBackingBean cart;

    private Orders order;
    
    public void createOrder(){
        order = new Orders();
        order.setUserId(userSession.getUser());
        order.setBillingAddress(userSession.getUser().getAddress1());
        order.setTimestamp(takeTheCurrentDate());
        order.setEnabled(false); 
        //order.setBookorderCollection(bookorderCollection);
    }
    
    /**
     * Adding a book purchased to the book order
     * for the user who have purchased it
     * @param book 
     */
    public void addBookToOrder(Books book) {
        //getting all taxes depeding on the user's province
        Taxes taxes = taxesJpaController.findByProvince(userSession.getUser().getProvince());
        LOG.info("GST is " + taxes.getGSTpercentage());
        LOG.info("HST is " + taxes.getHSTpercentage());
        LOG.info("PST is " + taxes.getPSTpercentage());
        
        //Orders order = createOrder();
        
        //creating a bookorder
        Bookorder bookorder = new Bookorder();
        bookorder.setIsbn(book); 
        bookorder.setGstTax(taxes.getGSTpercentage());
        bookorder.setHstTax(taxes.getHSTpercentage());
        bookorder.setPstTax(taxes.getPSTpercentage());
        bookorder.setOrderId(order);
        bookorder.setAmountPaidPretax(cart.calculateTotalAmount()); 
        bookorder.setEnabled(false);

        try {
            this.bookorderJpaController.create(bookorder);
        } catch (BackendException ex) {
            LOG.info(ex.toString());
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
            } catch (IOException ioex) {
                LOG.info("Problem with redirection: " + ioex.toString());
            }
        }
    }

    private Timestamp takeTheCurrentDate(){
        //Creating current timestamp 
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        return timestamp;
    }

}
