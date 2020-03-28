package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.SurveyResponses;
import com.gb4w20.gb4w20.jpa.SurveyResponsesJpaController;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * Used to test a method inside the SurveyResponsesJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jean Robatto
 * Inspired by Jeffrey's Survey Questions tests
 */
@RunWith(Enclosed.class)
public class SurveyResponsesJpaControllerTest {
    
    /**
     * Used to run valid tests for the voteForQuestion 
     * method inside of the SurveyResponsesJpaController class. 
     * @author Jean Robatto
     */
    public static class VoteForQuestion extends ArquillianTestBase {
        
        @Inject
        private SurveyResponsesJpaController surveyResponsesJpaController;
        
        /**
         * Used to test that when the method is called, the vote is increased by one
         * @author Jean Robatto
         * @throws com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException
         */
        @Test
        public void testCorrectIncrease() throws RollbackFailureException {
            Long id = 1L;
            SurveyResponses response = surveyResponsesJpaController.findSurveyResponses(id);
            int currentCount = response.getCount();
            surveyResponsesJpaController.voteForQuestion(id);
            int newCount = surveyResponsesJpaController.findSurveyResponses(id).getCount();
            
            assertEquals("Did not correcty increment voting count:", currentCount+1, newCount);
        }
        
    }
    
}
