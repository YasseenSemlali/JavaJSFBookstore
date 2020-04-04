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
 * <h1>Reviews JPA Controller Test</h1>
 * <p>
 * Used to test various methods inside the ReviewsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * </p>
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
            LOG.info("Testing book average rating");
            int testNumber = param.getValue0();
            double expectedAverage = param.getValue2();
            LOG.info("Expected average of book is " + expectedAverage);
            
            assertEquals( "Test " + testNumber + ": Expected average rating of book found was incorrect", expectedAverage, this.result, 0.1f);

        }    
    }
    
    /**
     * Used to run valid tests for the getApprovedReviews 
     * method inside of the ReviewsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetApprovedReviews extends ArquillianTestBase {
        
        private Triplet<Integer, Long, List<String>> param;
        
        @Inject
        private ReviewsJpaController reviewsJpaController;
        
        //holds the result of the method being tested
        private List<String> result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, List<String>>> rule = new ParameterRule("param", "result", 
                () -> takeReviewsFrom(reviewsJpaController.getApprovedReviews(param.getValue1())),
                Triplet.with(1, 9781401323585l, new ArrayList<String>(Arrays.asList("And Another Thing...: test review by John"))),
                Triplet.with(2, 9780000000000l, new ArrayList<String>(Arrays.asList("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch: test review by Jane"))),
                Triplet.with(3, 9780316251303l, new ArrayList<String>()),
                Triplet.with(4, 9780765377067l, new ArrayList<String>()));
        
        /**
         * Takes the actual reviews written as a String from the list of reviews 
         * from the result of getApprovedReviews method of a specific book
         * @param approvedRevs
         * @return 
         */
        private List<String> takeReviewsFrom(List<Reviews> approvedRevs){
            List<String> reviewsApproved = new ArrayList<>();
            for(Reviews approvedRev : approvedRevs){
                reviewsApproved.add(approvedRev.getReview());
            }
            return reviewsApproved;
        }
        
        /**
         * Used to test if it returns the right approved
         * reviews according to given ISBN by comparing the 
         * reviews written and also checks if it gives
         * an empty list of reviews
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectBookApprovedReviews() {
            LOG.info("Testing book approved reviews");
            int testNumber = param.getValue0();
            List<String> expectedReviews = param.getValue2();
            LOG.info("Expected size of approved reviews is " + expectedReviews.size());
            
            assertEquals( "Test " + testNumber + ": Expected approved reviews of book found was incorrect", expectedReviews, this.result);

        }    
    }
    
    /**
     * Used to run valid tests for the getReviewsOnApproved 
     * method inside of the ReviewsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetReviewsOnApproved extends ArquillianTestBase {
        
        private Triplet<Integer, Boolean, List<String>> param;
        
        @Inject
        private ReviewsJpaController reviewsJpaController;
        
        //holds the result of the method being tested
        private List<String> result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Boolean, List<String>>> rule = new ParameterRule("param", "result", 
                () -> takeReviewsFrom(reviewsJpaController.getReviewsOnApproved(param.getValue1())),
                Triplet.with(1, true, new ArrayList<String>(Arrays.asList("And Another Thing...: test review by John", "Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch: test review by Jane"))),
                Triplet.with(2, false, new ArrayList<String>()));
        
        /**
         * Takes the actual reviews written as a String from the list of reviews 
         * from the result of getReviewsOnApproved method of a specific 
         * approved status
         * @param approvedRevs
         * @return 
         */
        private List<String> takeReviewsFrom(List<Reviews> approvedRevs){
            List<String> reviewsApproved = new ArrayList<>();
            for(Reviews approvedRev : approvedRevs){
                reviewsApproved.add(approvedRev.getReview());
            }
            return reviewsApproved;
        }
        
        /**
         * Used to test if it returns the right list of
         * reviews based on if it is approved or not
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectReviewsOnApproved() {
            LOG.info("Testing reviews on approved");
            int testNumber = param.getValue0();
            List<String> expectedReviews = param.getValue2();
            LOG.info("Expected size of reviews on approved is " + expectedReviews.size());
            
            assertEquals( "Test " + testNumber + ": Expected list of reviews on approved found was incorrect", expectedReviews, this.result);

        }    
    }
}
