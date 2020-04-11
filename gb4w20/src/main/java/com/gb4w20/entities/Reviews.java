/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeanrobatto
 */
@Entity
@Table(name = "reviews")
@NamedQueries({
    @NamedQuery(name = "Reviews.findAll", query = "SELECT r FROM Reviews r"),
    @NamedQuery(name = "Reviews.findByReviewId", query = "SELECT r FROM Reviews r WHERE r.reviewId = :reviewId"),
    @NamedQuery(name = "Reviews.findByRating", query = "SELECT r FROM Reviews r WHERE r.rating = :rating"),
    @NamedQuery(name = "Reviews.findByReview", query = "SELECT r FROM Reviews r WHERE r.review = :review"),
    @NamedQuery(name = "Reviews.findByApprovedStatus", query = "SELECT r FROM Reviews r WHERE r.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "Reviews.findByTimestamp", query = "SELECT r FROM Reviews r WHERE r.timestamp = :timestamp")})
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "review_id")
    private Long reviewId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private short rating;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "review")
    private String review;
    @Basic(optional = false)
    @NotNull
    @Column(name = "approved_status")
    private boolean approvedStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private Books isbn;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;

    public Reviews() {
    }

    public Reviews(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Reviews(Long reviewId, short rating, String review, boolean approvedStatus, Date timestamp) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.review = review;
        this.approvedStatus = approvedStatus;
        this.timestamp = timestamp;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(boolean approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Books getIsbn() {
        return isbn;
    }

    public void setIsbn(Books isbn) {
        this.isbn = isbn;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewId != null ? reviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reviews)) {
            return false;
        }
        Reviews other = (Reviews) object;
        if ((this.reviewId == null && other.reviewId != null) || (this.reviewId != null && !this.reviewId.equals(other.reviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.entities.Reviews[ reviewId=" + reviewId + " ]";
    }
    
}
