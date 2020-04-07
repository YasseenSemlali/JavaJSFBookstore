package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.SurveyQuestions;
import com.gb4w20.gb4w20.entities.SurveyResponses;
import com.gb4w20.gb4w20.jpa.SurveyQuestionsJpaController;
import com.gb4w20.gb4w20.jpa.SurveyResponsesJpaController;
import com.gb4w20.gb4w20.jpa.exceptions.BackendException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to edit surveys.
 *
 * @author Jean Robatto
 */
@Named
@SessionScoped
public class ManagerSurveys implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerSurveys.class);

    @Inject
    private SurveyQuestionsJpaController surveysController;

    @Inject
    private SurveyResponsesJpaController answerController;

    private SurveyQuestions survey;

    private String[] questions;
    private Boolean[] enabled;

    private Collection<SurveyResponses> responses;

    @Size(min = 1, max = 1024)
    private String newQuestion;
    @Size(min = 1, max = 1024)
    private String newAnswer;

    /**
     * Method to initialize variables
     *
     * @author Jean Robatto
     */
    @PostConstruct
    private void init() {
        LOG.debug("Initializing Manager Survey variables");
        List<SurveyQuestions> allQuestions = surveysController.findSurveyQuestionsEntities();
        int size = allQuestions.size();
        questions = new String[size];
        enabled = new Boolean[size];
        for (int i = 0; i < size; i++) {
            questions[i] = "";
            enabled[i] = allQuestions.get(i).getEnabled();
        }
    }

    /**
     * Sets the survey variable for the survey questions to be changed
     *
     * @param survey
     * @author Jean Robatto
     * @return redirection
     */
    public String selectSurvey(SurveyQuestions survey) {
        this.survey = survey;
        this.responses = answerController.getResponsesFromQuestion(survey);
        return "/manager-secured/manager-forms/manager-surveys-answers";
    }

    /**
     * Method to alter the state of a survey question
     *
     * Redirects on failure instead of returning a string because it is an ajax
     * call
     *
     * @param id
     * @param index
     * @throws java.io.IOException
     * @author Jean Robatto
     */
    public void editSurvey(Long id, int index) throws IOException {
        LOG.debug("Editing survey with id: " + Long.toString(id));
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
     * @author Jean Robatto
     */
    public String createSurvey() {
        LOG.debug("Creating a new survey");
        try {
            SurveyQuestions survey = new SurveyQuestions();
            survey.setQuestion(newQuestion);
            survey.setTimestamp(new Date());
            survey.setEnabled(Boolean.TRUE);

            surveysController.create(survey);

            init();
                    
            return "/manager-secured/manager-forms/manager-surveys";
        } catch (BackendException ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to alter the state of a survey answer
     *
     * @return Redirection
     * @author Jean Robatto
     */
    public String editAnswers() {
        LOG.debug("Editing answers for survey with id: " + Long.toString(survey.getId()));
        try {
            for (SurveyResponses response : responses) {
                answerController.edit(response);
            }
            init();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/manager-secured/manager-forms/manager-surveys.xhtml"); //Fixes a display bug
            return "/manager-secured/manager-forms/manager-surveys"; 
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to create a new survey answer.
     * 
     * @author Jean Robatto
     */
    public void createAnswer() {
        LOG.debug("Creating a new answer for survey " + Long.toString(survey.getId()));
        try {
            SurveyResponses answer = new SurveyResponses();
            answer.setResponse(newAnswer);
            answer.setTimestamp(new Date());
            answer.setEnabled(Boolean.TRUE);
            answer.setSurveyQuestionId(survey);

            answerController.create(answer);
            
            responses.add(answer);
            
            newAnswer = " ";

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

    public String getNewQuestion() {
        return newQuestion;
    }

    public String getNewAnswer() {
        return newAnswer;
    }

    public SurveyQuestions getSurvey() {
        return survey;
    }

    public Collection<SurveyResponses> getResponses() {
        return responses;
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

    public void setNewAnswer(String newAnswer) {
        this.newAnswer = newAnswer;
    }

    public void setSurvey(SurveyQuestions survey) {
        this.survey = survey;
    }

    public void setResponses(Collection<SurveyResponses> responses) {
        this.responses = responses;
    }

}
