
package com.gb4w20.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "taxes")
@NamedQueries({
    @NamedQuery(name = "Taxes.findAll", query = "SELECT t FROM Taxes t"),
    @NamedQuery(name = "Taxes.findByProvince", query = "SELECT t FROM Taxes t WHERE t.province = :province"),
    @NamedQuery(name = "Taxes.findByHSTpercentage", query = "SELECT t FROM Taxes t WHERE t.hSTpercentage = :hSTpercentage"),
    @NamedQuery(name = "Taxes.findByGSTpercentage", query = "SELECT t FROM Taxes t WHERE t.gSTpercentage = :gSTpercentage"),
    @NamedQuery(name = "Taxes.findByPSTpercentage", query = "SELECT t FROM Taxes t WHERE t.pSTpercentage = :pSTpercentage"),
    @NamedQuery(name = "Taxes.findByTimestamp", query = "SELECT t FROM Taxes t WHERE t.timestamp = :timestamp")})
public class Taxes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "province")
    private String province;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "HST_percentage")
    private BigDecimal hSTpercentage;
    @Column(name = "GST_percentage")
    private BigDecimal gSTpercentage;
    @Column(name = "PST_percentage")
    private BigDecimal pSTpercentage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Taxes() {
    }

    public Taxes(String province) {
        this.province = province;
    }

    public Taxes(String province, Date timestamp) {
        this.province = province;
        this.timestamp = timestamp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public BigDecimal getHSTpercentage() {
        return hSTpercentage;
    }

    public void setHSTpercentage(BigDecimal hSTpercentage) {
        this.hSTpercentage = hSTpercentage;
    }

    public BigDecimal getGSTpercentage() {
        return gSTpercentage;
    }

    public void setGSTpercentage(BigDecimal gSTpercentage) {
        this.gSTpercentage = gSTpercentage;
    }

    public BigDecimal getPSTpercentage() {
        return pSTpercentage;
    }

    public void setPSTpercentage(BigDecimal pSTpercentage) {
        this.pSTpercentage = pSTpercentage;
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
        hash += (province != null ? province.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxes)) {
            return false;
        }
        Taxes other = (Taxes) object;
        if ((this.province == null && other.province != null) || (this.province != null && !this.province.equals(other.province))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.entities.Taxes[ province=" + province + " ]";
    }
    
}
