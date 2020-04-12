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
 * @author Yasseen Semlali
 */
@Entity
@Table(name = "survey_responses")
@NamedQueries({
    @NamedQuery(name = "SurveyResponses.findAll", query = "SELECT s FROM SurveyResponses s"),
    @NamedQuery(name = "SurveyResponses.findById", query = "SELECT s FROM SurveyResponses s WHERE s.id = :id"),
    @NamedQuery(name = "SurveyResponses.findByResponse", query = "SELECT s FROM SurveyResponses s WHERE s.response = :response"),
    @NamedQuery(name = "SurveyResponses.findByEnabled", query = "SELECT s FROM SurveyResponses s WHERE s.enabled = :enabled"),
    @NamedQuery(name = "SurveyResponses.findByCount", query = "SELECT s FROM SurveyResponses s WHERE s.count = :count"),
    @NamedQuery(name = "SurveyResponses.findByTimestamp", query = "SELECT s FROM SurveyResponses s WHERE s.timestamp = :timestamp")})
public class SurveyResponses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 1024)
    @Column(name = "response")
    private String response;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "count")
    private Integer count;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "survey_question_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SurveyQuestions surveyQuestionId;

    public SurveyResponses() {
    }

    public SurveyResponses(Long id) {
        this.id = id;
    }

    public SurveyResponses(Long id, Date timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public SurveyQuestions getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(SurveyQuestions surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
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
        if (!(object instanceof SurveyResponses)) {
            return false;
        }
        SurveyResponses other = (SurveyResponses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.entities.SurveyResponses[ id=" + id + " ]";
    }
    
}
