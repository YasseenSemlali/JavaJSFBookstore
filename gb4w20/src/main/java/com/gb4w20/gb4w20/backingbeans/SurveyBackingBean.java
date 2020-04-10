package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.SurveyResponsesJpaController;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 * Backing bean for the survey on the front page
 *
 * @author Yasseen
 */
@Named
@RequestScoped
public class SurveyBackingBean {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(SurveyBackingBean.class);

    @Inject
    UserSessionBean session;

    @Inject
    SurveyResponsesJpaController responsesController;

    private Long questionId;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /** Votes for the selected question
     * 
     * @author Yasseen Semlali
     */
    public void vote() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
        if (questionId == null) {
            return;
        }

        try {
            responsesController.voteForQuestion(questionId);
            session.setHasRespondedToSurvey(true);
        } catch (RollbackFailureException ex) {
            context.addMessage("surveyform:surveysubmit", new FacesMessage(bundle.getString("could_not_send_vote"), bundle.getString("could_not_send_vote_long")));
        }
    }
}
