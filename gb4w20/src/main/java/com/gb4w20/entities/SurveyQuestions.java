
package com.gb4w20.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "survey_questions")
@NamedQueries({
    @NamedQuery(name = "SurveyQuestions.findAll", query = "SELECT s FROM SurveyQuestions s"),
    @NamedQuery(name = "SurveyQuestions.findById", query = "SELECT s FROM SurveyQuestions s WHERE s.id = :id"),
    @NamedQuery(name = "SurveyQuestions.findByQuestion", query = "SELECT s FROM SurveyQuestions s WHERE s.question = :question"),
    @NamedQuery(name = "SurveyQuestions.findByEnabled", query = "SELECT s FROM SurveyQuestions s WHERE s.enabled = :enabled"),
    @NamedQuery(name = "SurveyQuestions.findByTimestamp", query = "SELECT s FROM SurveyQuestions s WHERE s.timestamp = :timestamp")})
public class SurveyQuestions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 1024)
    @Column(name = "question")
    private String question;
    @Column(name = "enabled")
    private Boolean enabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "surveyQuestionId")
    private Collection<SurveyResponses> surveyResponsesCollection;

    public SurveyQuestions() {
    }

    public SurveyQuestions(Long id) {
        this.id = id;
    }

    public SurveyQuestions(Long id, Date timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public Collection<SurveyResponses> getSurveyResponsesCollection() {
        return surveyResponsesCollection;
    }

    public void setSurveyResponsesCollection(Collection<SurveyResponses> surveyResponsesCollection) {
        this.surveyResponsesCollection = surveyResponsesCollection;
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
        if (!(object instanceof SurveyQuestions)) {
            return false;
        }
        SurveyQuestions other = (SurveyQuestions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.gb4w20.entities.SurveyQuestions[ id=" + id + " ]";
    }
    
}
