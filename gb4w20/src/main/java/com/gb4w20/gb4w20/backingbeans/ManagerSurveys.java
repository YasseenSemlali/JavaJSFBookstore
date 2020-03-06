package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.SurveyQuestions;
import com.gb4w20.gb4w20.jpa.SurveyQuestionsJpaController;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to edit surveys.
 *
 * @author Jean Robatto
 */
@Named
@RequestScoped
public class ManagerSurveys implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);
    
    @Inject
    private SurveyQuestionsJpaController surveysController;
    
    private String[] questions;
    private Boolean[] enabled;
    
    /**
     * Method to initialize variables
     */
    @PostConstruct
    private void init() {
        int size = surveysController.getSurveyQuestionsCount();
        this.questions = new String[size];
        this.enabled = new Boolean[size];
        for (int i = 0; i < size; i++) {
            this.questions[i] = "";
            this.enabled[i] = Boolean.FALSE;
        }
    }
    
    
    /**
     * Method to alter the state of a survey question
     * 
     * @param id
     * @param index
     */
    public void editSurvey(Long id, int index) {
        try {
            SurveyQuestions question = surveysController.findSurveyQuestions(id);
            question.setEnabled(this.enabled[index]);
            question.setQuestion(this.questions[index]);
            surveysController.edit(question);
            this.enabled[index] = Boolean.FALSE;
            this.questions[index] = "";
        } catch (Exception ex) {
            LOG.info(ex.toString());
        }
    }

    public String[] getQuestions() {
        return questions;
    }

    public Boolean[] getEnabled() {
        return enabled;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public void setEnabled(Boolean[] enabled) {
        this.enabled = enabled;
    }
    
    

    

    

}
