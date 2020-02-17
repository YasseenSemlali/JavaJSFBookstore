package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to hold the information from the Add Book form
 * @author jeanrobatto
 */
@Named
@RequestScoped
public class ManagerInventory implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);
    
    protected Long isbn = 9898989898L;
    protected String title;
    protected Date dateOfPublication;
    protected int pages;
    protected String synopsis;
    protected String cover;
    protected BigDecimal wholesalePrice = new BigDecimal("0");
    protected BigDecimal listPrice = new BigDecimal("0");
    protected BigDecimal salePrice = new BigDecimal("0");
    protected Date timestamp = new Date();
    protected boolean active;
    
    public ManagerInventory() {}
    
    //ACTIONS
    public Books getBook() {
            return new Books(this.isbn, this.title, this.dateOfPublication, 
                                this.pages, this.synopsis, this.cover, this.wholesalePrice,
                                    this.listPrice, this.salePrice, this.timestamp, this.active);
    }

    //Setters
    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    //Getters
    public Long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public int getPages() {
        return pages;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCover() {
        return cover;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isActive() {
        return active;
    }
   
}
