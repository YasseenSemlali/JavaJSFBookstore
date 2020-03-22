package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
import javax.inject.Inject;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to test various methods inside the AuthorsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jasmar Badion
 */
@RunWith(Enclosed.class)
public class AuthorsJpaControllerTest {
    
    private final static Logger LOG = LoggerFactory.getLogger(AuthorsJpaControllerTest.class);
    
    /**
     * Used to run valid tests for the getAuthorsTotalSales 
     * method inside of the AuthorsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetAuthorsTotalSales extends ArquillianTestBase {

        @Inject
        private AuthorsJpaController authorsJpaController;
        
        @Rule
        public ParameterRule rule = new ParameterRule("param",
                //test number, id, startDate, endDate, expected result
                new Quintet<Integer, Long, String, String, Double>(1, 1l, "2020-01-23", "2020-03-21", 17.0),
                new Quintet<Integer, Long, String, String, Double>(2, 2l, "2020-01-23", "2020-03-21", 17.0),
                new Quintet<Integer, Long, String, String, Double>(3, 3l, "2020-01-23", "2020-03-21", 0.00)
                );
        
        private Quintet<Integer, Long, String, String, Double> param;
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen author
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectTotalSales(){
            int testNumber = param.getValue0();
            long authorId = param.getValue1();
            String startDate = param.getValue2();
            String endDate = param.getValue3();
            double expectedTotalSales = param.getValue4();
            
            double totalSales = authorsJpaController.getAuthorsTotalSales(authorId, startDate, endDate);
            
            assertEquals("Test " + testNumber + " did not return the correct total sales of this author", expectedTotalSales, totalSales, 0.01f);
        }
    }   
}
