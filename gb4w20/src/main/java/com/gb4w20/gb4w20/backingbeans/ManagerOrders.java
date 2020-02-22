package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to manage the manager orders page. Used to edit and/or add
 * orders.
 *
 * @author jeanrobatto
 */
@Named
@SessionScoped
public class ManagerOrders implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);
    
    //Constructor
    public ManagerOrders() {}
    
    //Fields
    protected Books book;
    
    //Actions
    public String editBook() {
        return "manager-orders-edit?id=5";
    }
    
    public void Log(String s) {
        LOG.info(s);
    }
    
    //Setters

    public void setBook(Books book) {
        this.book = book;
    }
   
    
    //Getters

    public Books getBook() {
        return this.book;
    }
    
}
