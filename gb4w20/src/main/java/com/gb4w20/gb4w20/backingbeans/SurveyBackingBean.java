package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.SurveyResponsesJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/** Backing bean for the survey on the front page
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
    
    public void vote() {
        if(questionId == null) {
            // TODO show error
            return;
        }
        
        try {
            responsesController.voteForQuestion(questionId);
            System.out.println(session.isHasRespondedToSurvey());
            session.setHasRespondedToSurvey(true);
            System.out.println(session.isHasRespondedToSurvey());
        } catch (RollbackFailureException ex) {
            // TODO show error
        }
    }
}
