/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jeanrobatto
 */
@Entity
@Table(name = "bookorder")
@NamedQueries({
    @NamedQuery(name = "Bookorder.findAll", query = "SELECT b FROM Bookorder b"),
    @NamedQuery(name = "Bookorder.findByBookorderId", query = "SELECT b FROM Bookorder b WHERE b.bookorderId = :bookorderId"),
    @NamedQuery(name = "Bookorder.findByAmountPaidPretax", query = "SELECT b FROM Bookorder b WHERE b.amountPaidPretax = :amountPaidPretax"),
    @NamedQuery(name = "Bookorder.findByHstTax", query = "SELECT b FROM Bookorder b WHERE b.hstTax = :hstTax"),
    @NamedQuery(name = "Bookorder.findByGstTax", query = "SELECT b FROM Bookorder b WHERE b.gstTax = :gstTax"),
    @NamedQuery(name = "Bookorder.findByPstTax", query = "SELECT b FROM Bookorder b WHERE b.pstTax = :pstTax"),
    @NamedQuery(name = "Bookorder.findByEnabled", query = "SELECT b FROM Bookorder b WHERE b.enabled = :enabled")})
public class Bookorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bookorder_id")
    private Long bookorderId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount_paid_pretax")
    private BigDecimal amountPaidPretax;
    @Column(name = "HST_TAX")
    private BigDecimal hstTax;
    @Column(name = "GST_TAX")
    private BigDecimal gstTax;
    @Column(name = "PST_TAX")
    private BigDecimal pstTax;
    @Column(name = "enabled")
    private Boolean enabled;
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private Books isbn;
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne(optional = false)
    private Orders orderId;

    public Bookorder() {
    }

    public Bookorder(Long bookorderId) {
        this.bookorderId = bookorderId;
    }

    public Bookorder(Long bookorderId, BigDecimal amountPaidPretax) {
        this.bookorderId = bookorderId;
        this.amountPaidPretax = amountPaidPretax;
    }

    public Long getBookorderId() {
        return bookorderId;
    }

    public void setBookorderId(Long bookorderId) {
        this.bookorderId = bookorderId;
    }

    public BigDecimal getAmountPaidPretax() {
        return amountPaidPretax;
    }

    public void setAmountPaidPretax(BigDecimal amountPaidPretax) {
        this.amountPaidPretax = amountPaidPretax;
    }

    public BigDecimal getHstTax() {
        return hstTax;
    }

    public void setHstTax(BigDecimal hstTax) {
        this.hstTax = hstTax;
    }

    public BigDecimal getGstTax() {
        return gstTax;
    }

    public void setGstTax(BigDecimal gstTax) {
        this.gstTax = gstTax;
    }

    public BigDecimal getPstTax() {
        return pstTax;
    }

    public void setPstTax(BigDecimal pstTax) {
        this.pstTax = pstTax;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Books getIsbn() {
        return isbn;
    }

    public void setIsbn(Books isbn) {
        this.isbn = isbn;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookorderId != null ? bookorderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookorder)) {
            return false;
        }
        Bookorder other = (Bookorder) object;
        if ((this.bookorderId == null && other.bookorderId != null) || (this.bookorderId != null && !this.bookorderId.equals(other.bookorderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.gb4w20.entities.Bookorder[ bookorderId=" + bookorderId + " ]";
    }
    
}
