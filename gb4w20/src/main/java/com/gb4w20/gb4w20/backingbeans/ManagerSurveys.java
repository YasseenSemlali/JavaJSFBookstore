package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.SurveyQuestions;
import com.gb4w20.gb4w20.jpa.SurveyQuestionsJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
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

    private final static Logger LOG = LoggerFactory.getLogger(ManagerSurveys.class);

    @Inject
    private SurveyQuestionsJpaController surveysController;

    private String[] questions;
    private Boolean[] enabled;

    private String newQuestion;
    
    /**
     * Method to initialize variables
     */
    @PostConstruct
    private void init() {
        int size = surveysController.getSurveyQuestionsCount();
        questions = new String[size];
        enabled = new Boolean[size];
        for (int i = 0; i < size; i++) {
            questions[i] = "";
            enabled[i] = Boolean.FALSE;
        }
    }

    /**
     * Method to alter the state of a survey question
     *
     * @param id
     * @param index
     * @throws java.io.IOException
     */
    public void editSurvey(Long id, int index) throws IOException {
        try {
            SurveyQuestions question = surveysController.findSurveyQuestions(id);

            //Only edit fields which have been edited
            if (!question.getEnabled().equals(enabled[index])) {
                question.setEnabled(enabled[index]);
            }
            if (!questions[index].isEmpty()) {
                question.setQuestion(questions[index]);
            }

            surveysController.edit(question);

            //Reset inputs
            enabled[index] = Boolean.FALSE;
            questions[index] = "";

        } catch (Exception ex) {
            LOG.info(ex.toString());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
        }
    }
    
    /**
     * Method to create a new survey.
     *
     * @return redirection
     */
    public String createSurvey() {
        try {
            SurveyQuestions survey = new SurveyQuestions();
            survey.setQuestion(newQuestion);
            survey.setTimestamp(new Date());
            survey.setEnabled(Boolean.TRUE);
            
            surveysController.create(survey);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    public String[] getQuestions() {
        return questions;
    }

    public Boolean[] getEnabled() {
        return enabled;
    }

    public String getNewQuestion() {
        return newQuestion;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public void setEnabled(Boolean[] enabled) {
        this.enabled = enabled;
    }

    public void setNewQuestion(String newQuestion) {
        this.newQuestion = newQuestion;
    }

}
