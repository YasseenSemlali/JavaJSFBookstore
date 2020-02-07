/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "rss_feeds")
@NamedQueries({
    @NamedQuery(name = "RssFeeds.findAll", query = "SELECT r FROM RssFeeds r"),
    @NamedQuery(name = "RssFeeds.findById", query = "SELECT r FROM RssFeeds r WHERE r.id = :id"),
    @NamedQuery(name = "RssFeeds.findByUrl", query = "SELECT r FROM RssFeeds r WHERE r.url = :url"),
    @NamedQuery(name = "RssFeeds.findByEnabled", query = "SELECT r FROM RssFeeds r WHERE r.enabled = :enabled"),
    @NamedQuery(name = "RssFeeds.findByTimestamp", query = "SELECT r FROM RssFeeds r WHERE r.timestamp = :timestamp")})
public class RssFeeds implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 2048)
    @Column(name = "url")
    private String url;
    @Column(name = "enabled")
    private Boolean enabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public RssFeeds() {
    }

    public RssFeeds(Long id) {
        this.id = id;
    }

    public RssFeeds(Long id, Date timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RssFeeds)) {
            return false;
        }
        RssFeeds other = (RssFeeds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.gb4w20.entities.RssFeeds[ id=" + id + " ]";
    }
    
}
