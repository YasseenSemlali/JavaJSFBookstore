package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
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
 * <h1>Bookorder JPA Controller Unit Test</h1>
 * <p>
 * Used to test various methods inside the BookorderJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * </p>
 * @author Jean Robatto
 * Inspired by the other test classes made by my teammates
 */
@RunWith(Enclosed.class)
public class BookorderJpaControllerTest {
    
    private final static Logger LOG = LoggerFactory.getLogger(BookorderJpaControllerTest.class);
    
    /**
     * Used to run valid tests for the getTotalSalesForBook 
     * method inside of the BookorderJpaController class. 
     * @author Jean Robatto
     */
    public static class GetTotalSalesForBook extends ArquillianTestBase {

        @Inject
        private BookorderJpaController bookorderJpaController;
        
        private Triplet<Integer, Long, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getTotalSalesForBook(param.getValue1()).doubleValue(),
                Triplet.with(1, 9780000000000L, 17.0), //Other order is disabled
                Triplet.with(2, 9780765377067L, 36.0),
                Triplet.with(3, 9780316251303L, 16.0));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen book
         * @author Jean Robatto
         */
        @Test
        public void testCorrectTotal(){
            double expectedTotalSales = param.getValue2();
            assertEquals("Test " + param.getValue0() + " did not return the correct total sales", expectedTotalSales, this.result, 0.01f);
        }
    }
    
    /**
     * Used to run valid tests for the getTotalSalesForOrderPreTax 
     * method inside of the BookorderJpaController class. 
     * @author Jean Robatto
     */
    public static class GetTotalSalesForOrderPreTax extends ArquillianTestBase {

        @Inject
        private BookorderJpaController bookorderJpaController;
        
        private Triplet<Integer, Long, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getTotalSalesForOrderPreTax(param.getValue1()).doubleValue(),
                Triplet.with(1, 2L, 18.0),
                Triplet.with(2, 4L, 25.0),
                Triplet.with(3, 5L, 42.0));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen order
         * @author Jean Robatto
         */
        @Test
        public void testCorrectTotal(){
            int testNumber = param.getValue0();
            double expectedTotalSales = param.getValue2();
            
            assertEquals("Test " + testNumber + " did not return the correct total sales", expectedTotalSales, this.result, 0.01f);
        }
        
    }
        
    
    /**
     * Used to run valid tests for the getHSTForOrder 
     * method inside of the BookorderJpaController class. 
     * @author Jean Robatto
     */
    public static class GetHSTForOrder extends ArquillianTestBase {
        
        @Inject
        private BookorderJpaController bookorderJpaController;
        
        private Triplet<Integer, Long, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getHSTForOrder(param.getValue1()).doubleValue(),
                Triplet.with(1, 5L, 13.0),
                Triplet.with(2, 4L, 13.0),
                Triplet.with(3, 3L, 13.0));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen order
         * @author Jean Robatto
         */
        @Test
        public void testCorrectTotal(){
            int testNumber = param.getValue0();
            double expectedTotalTax = param.getValue2();
            
            assertEquals("Test " + testNumber + " did not return the correct total tax", expectedTotalTax, this.result, 0.01f);
        }

    }
    
    /**
     * Used to run valid tests for the getPSTForOrder 
     * method inside of the BookorderJpaController class. 
     * @author Jean Robatto
     */
    public static class GetPSTForOrder extends ArquillianTestBase {
        
        @Inject
        private BookorderJpaController bookorderJpaController;
        
        private Triplet<Integer, Long, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getPSTForOrder(param.getValue1()).doubleValue(),
                Triplet.with(1, 5L, 10.0),
                Triplet.with(2, 6L, 15.0),
                Triplet.with(3, 1L, 0.0)); //Disabled
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen order
         * @author Jean Robatto
         */
        @Test
        public void testCorrectTotal(){
            int testNumber = param.getValue0();
            double expectedTotalTax = param.getValue2();
            
            assertEquals("Test " + testNumber + " did not return the correct total tax", expectedTotalTax, this.result, 0.01f);
        }

    }
    
    /**
     * Used to run valid tests for the getGSTForOrder 
     * method inside of the BookorderJpaController class. 
     * @author Jean Robatto
     */
    public static class GetGSTForOrder extends ArquillianTestBase {
        
        @Inject
        private BookorderJpaController bookorderJpaController;
        
        private Triplet<Integer, Long, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Long, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getGSTForOrder(param.getValue1()).doubleValue(),
                Triplet.with(1, 4L, 6.0),
                Triplet.with(2, 5L, 12.0),
                Triplet.with(3, 2L, 9.975));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen order
         * @author Jean Robatto
         */
        @Test
        public void testCorrectTotal(){
            int testNumber = param.getValue0();
            double expectedTotalTax = param.getValue2();
            
            assertEquals("Test " + testNumber + " did not return the correct total tax", expectedTotalTax, this.result, 0.01f);
        }

    }
    
}
