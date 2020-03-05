/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Cart Book Backing Bean</h1>
 * <p>
 * This is the backing bean for adding books to the cart
 * by adding them to the cookie and this class also contains
 * methods to read and write to cookies.
 * </p>
 * 
 * @author Jasmar Badion
 */
@Named
@RequestScoped
public class CartBookBackingBean implements Serializable{
    private final static Logger LOG = LoggerFactory.getLogger(CartBookBackingBean.class);
    
    private Long isbn;
    
    public Long getIsbn(){
        return this.isbn;
    }
    public void setIsbn(Long isbn){
        this.isbn = isbn;
    }
    /**
     * 
     */
    public void addIsbnToCookie(Long isbn){
        this.isbn = isbn;
        FacesContext context = FacesContext.getCurrentInstance();
        Cookie cart = (Cookie)context.getExternalContext().getRequestCookieMap().get("isbn");
        cart.getValue().split(",");
        cart.setPath("/");
        context.getExternalContext().addResponseCookie("isbn", this.isbn.toString(), null);
    }
    /*public void writeIsbnToCookie(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie("isbn", this.isbn.toString(), null);
    }*/
    
    /**
     * 
     */
    public void checkBooksInCookie(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookieMap = null;
        
        if(cookieMap == null || cookieMap.isEmpty()){
            LOG.info("No cookies");
        }
        else{
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
        if (book_cookie != null){
            LOG.info(((Cookie) book_cookie).getName());
            LOG.info(((Cookie) book_cookie).getValue());
        }
        //writeIsbnToCookie(); //setMaxAge, setPath
    }
}
