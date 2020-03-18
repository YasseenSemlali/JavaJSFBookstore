/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeanrobatto
 */
@Entity
@Table(name = "books")
@NamedQueries({
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
    @NamedQuery(name = "Books.findByIsbn", query = "SELECT b FROM Books b WHERE b.isbn = :isbn"),
    @NamedQuery(name = "Books.findByTitle", query = "SELECT b FROM Books b WHERE b.title = :title"),
    @NamedQuery(name = "Books.findByDateOfPublication", query = "SELECT b FROM Books b WHERE b.dateOfPublication = :dateOfPublication"),
    @NamedQuery(name = "Books.findByPages", query = "SELECT b FROM Books b WHERE b.pages = :pages"),
    @NamedQuery(name = "Books.findBySynopsis", query = "SELECT b FROM Books b WHERE b.synopsis = :synopsis"),
    @NamedQuery(name = "Books.findByCover", query = "SELECT b FROM Books b WHERE b.cover = :cover"),
    @NamedQuery(name = "Books.findByWholesalePrice", query = "SELECT b FROM Books b WHERE b.wholesalePrice = :wholesalePrice"),
    @NamedQuery(name = "Books.findByListPrice", query = "SELECT b FROM Books b WHERE b.listPrice = :listPrice"),
    @NamedQuery(name = "Books.findBySalePrice", query = "SELECT b FROM Books b WHERE b.salePrice = :salePrice"),
    @NamedQuery(name = "Books.findByTimestamp", query = "SELECT b FROM Books b WHERE b.timestamp = :timestamp"),
    @NamedQuery(name = "Books.findByActive", query = "SELECT b FROM Books b WHERE b.active = :active")})
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "isbn")
    private Long isbn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_of_publication")
    @Temporal(TemporalType.DATE)
    private Date dateOfPublication;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pages")
    private int pages;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "synopsis")
    private String synopsis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cover")
    private String cover;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "wholesale_price")
    private BigDecimal wholesalePrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "list_price")
    private BigDecimal listPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sale_price")
    private BigDecimal salePrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @ManyToMany(mappedBy = "booksCollection")
    private Collection<Genres> genresCollection;
    @ManyToMany(mappedBy = "booksCollection")
    private Collection<Publishers> publishersCollection;
    @ManyToMany(mappedBy = "booksCollection")
    private Collection<Authors> authorsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn")
    private Collection<BookFiles> bookFilesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn")
    private Collection<Reviews> reviewsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn")
    private Collection<Bookorder> bookorderCollection;

    public Books() {
    }

    public Books(Long isbn) {
        this.isbn = isbn;
    }

    public Books(Long isbn, String title, Date dateOfPublication, int pages, String synopsis, String cover, BigDecimal wholesalePrice, BigDecimal listPrice, BigDecimal salePrice, Date timestamp, boolean active) {
        this.isbn = isbn;
        this.title = title;
        this.dateOfPublication = dateOfPublication;
        this.pages = pages;
        this.synopsis = synopsis;
        this.cover = cover;
        this.wholesalePrice = wholesalePrice;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.timestamp = timestamp;
        this.active = active;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Collection<Genres> getGenresCollection() {
        return genresCollection;
    }

    public void setGenresCollection(Collection<Genres> genresCollection) {
        this.genresCollection = genresCollection;
    }

    public Collection<Publishers> getPublishersCollection() {
        return publishersCollection;
    }

    public void setPublishersCollection(Collection<Publishers> publishersCollection) {
        this.publishersCollection = publishersCollection;
    }

    public Collection<Authors> getAuthorsCollection() {
        return authorsCollection;
    }

    public void setAuthorsCollection(Collection<Authors> authorsCollection) {
        this.authorsCollection = authorsCollection;
    }

    public Collection<BookFiles> getBookFilesCollection() {
        return bookFilesCollection;
    }

    public void setBookFilesCollection(Collection<BookFiles> bookFilesCollection) {
        this.bookFilesCollection = bookFilesCollection;
    }

    public Collection<Reviews> getReviewsCollection() {
        return reviewsCollection;
    }

    public void setReviewsCollection(Collection<Reviews> reviewsCollection) {
        this.reviewsCollection = reviewsCollection;
    }

    public Collection<Bookorder> getBookorderCollection() {
        return bookorderCollection;
    }

    public void setBookorderCollection(Collection<Bookorder> bookorderCollection) {
        this.bookorderCollection = bookorderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    /**
     * Edited by: Jean Robatto
     * @return 
     */
    @Override
    public String toString() {
        return "com.gb4w20.gb4w20.entities.Books[ isbn=" + isbn + " ] ";
    }
    
}
