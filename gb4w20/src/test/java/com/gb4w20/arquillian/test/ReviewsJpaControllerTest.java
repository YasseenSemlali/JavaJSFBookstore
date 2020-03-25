package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.gb4w20.entities.Reviews;
import com.gb4w20.gb4w20.jpa.ReviewsJpaController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.javatuples.Triplet;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to test various methods inside the ReviewsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jasmar Badion
 */
@RunWith(Enclosed.class)
public class ReviewsJpaControllerTest {
    
    private final static Logger LOG = LoggerFactory.getLogger(ReviewsJpaControllerTest.class);
    
    /**
     * Used to run valid tests for the getAverageRating 
     * method inside of the ReviewsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetAverageRating extends ArquillianTestBase {
        
        private Triplet<Integer, Long, Double> param;
        
        @Inject
        private ReviewsJpaController reviewsJpaController;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, Double>> rule = new ParameterRule("param", "result", 
                () -> reviewsJpaController.getAverageRating(param.getValue1()),
                Triplet.with(1, 9781401323585l, 4.0),
                Triplet.with(2, 9780000000000l, 2.0),
                Triplet.with(3, 9780316251303l, 0.0),
                Triplet.with(4, 9780765377067l, 0.0),
                Triplet.with(5, 9780545010221l, 0.0));
        
        /**
         * Used to test if it returns the right average either 
         * the correct average in double or 0.0 if null
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectBookAverageRating() {
            int testNumber = param.getValue0();
            double expectedAverage = param.getValue2();
            
            assertEquals( "Test " + testNumber + ": Expected average rating of book found was incorrect", expectedAverage, this.result, 0.1f);

        }    
    }
    
    /**
     * Used to run valid tests for the getApprovedReviews 
     * method inside of the ReviewsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetApprovedReviews extends ArquillianTestBase {
        
        private Triplet<Integer, Long, List<Reviews>> param;
        
        @Inject
        private ReviewsJpaController reviewsJpaController;
        
        //holds the result of the method being tested
        private List<Reviews> result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, List<Reviews>>> rule = new ParameterRule("param", "result", 
                () -> reviewsJpaController.getApprovedReviews(param.getValue1()),
                Triplet.with(1, 9781401323585l, new ArrayList<Reviews>(Arrays.asList(reviewsJpaController.findReviews(2695l)))),
                Triplet.with(2, 9780000000000l, new ArrayList<Reviews>(Arrays.asList(reviewsJpaController.findReviews(2696l)))),
                Triplet.with(3, 9780316251303l, new ArrayList<Reviews>()),
                Triplet.with(4, 9780765377067l, new ArrayList<Reviews>()),
                Triplet.with(5, 9780545010221l, new ArrayList<Reviews>()));
        
        /**
         * Used to test if it returns the right approved
         * reviews according to given ISBN
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectBookApprovedReviews() {
            int testNumber = param.getValue0();
            List<Reviews> expectedReviews = param.getValue2();
            
            assertEquals( "Test " + testNumber + ": Expected approved reviews of book found was incorrect", expectedReviews, this.result);

        }    
    }
}
