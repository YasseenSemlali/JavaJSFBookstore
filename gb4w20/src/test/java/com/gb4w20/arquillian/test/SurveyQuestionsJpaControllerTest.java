package com.gb4w20.arquillian.test;

import com.gb4w20.entities.SurveyQuestions;
import com.gb4w20.jpa.SurveyQuestionsJpaController;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * Used to test various methods inside the SurveyQuestionsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jeffrey Boisvert
 */
@RunWith(Enclosed.class)
public class SurveyQuestionsJpaControllerTest {
    
    /**
     * Used to run valid tests for the getActiveQuestions 
     * method inside of the SurveyQuestionsJpaController class. 
     * @author Jeffrey Boisvert
     */
    public static class GetActiveQuestions extends ArquillianTestBase {
        
        @Inject
        private SurveyQuestionsJpaController surveyQuestionsJpaController;
        
        /**
         * Used to test that expected active question is returned
         * @author Jeffrey Boisvert
         */
        @Test
        public void testReturnOnlyActiveResult() {
            
            long expectedIdOfActiveQuestion = 1l; 
            
            SurveyQuestions question = surveyQuestionsJpaController.getActiveQuestion();
            
            assertEquals("Did not get the expected active question", expectedIdOfActiveQuestion, question.getId().longValue());

        }
        
        /**
         * Used to test that null is returned when there is no 
         * active questions available
         * @author Jeffrey Boisvert
         */
        @Test
        public void testNullWhenNoActiveQuestions() throws Exception {
            
            //Disable the only active question
            SurveyQuestions activeQuestion = surveyQuestionsJpaController.getActiveQuestion();
            activeQuestion.setEnabled(false);
            surveyQuestionsJpaController.edit(activeQuestion);
            
            SurveyQuestions question = surveyQuestionsJpaController.getActiveQuestion();
            
            assertNull("Question was not null even though there should be no active questions", question);
            
        }
        
    }
    
}
