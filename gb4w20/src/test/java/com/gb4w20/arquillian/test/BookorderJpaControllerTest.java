package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.OrdersJpaController;
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
    
    /*
        NEED TO TEST:
    
        getHSTForOrder
        getGSTForOrder
        getPSTForOrder
    */
    
    /**
     * Used to run valid tests for the getTotalSalesForBook 
     * method inside of the BookorderJpaController class. 
     * @author Jean Robatto
     */
    public static class GetTotalSalesForBook extends ArquillianTestBase {

        @Inject
        private BookorderJpaController bookorderJpaController;
        
        private Triplet<Integer, Books, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Books, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getTotalSalesForBook(param.getValue1()),
                Triplet.with(1, bookorderJpaController.findBookorder(1L).getIsbn(), 32.0),
                Triplet.with(2, bookorderJpaController.findBookorder(2L).getIsbn(), 36.0),
                Triplet.with(3, bookorderJpaController.findBookorder(3L).getIsbn(), 32.0));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen book
         * @author Jean Robatto
         */
        @Test
        public void testCorrectTotalSales(){
            LOG.info(Long.toString(param.getValue1().getIsbn()));
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
        
        @Inject
        private OrdersJpaController ordersJpaController;
        
        private Triplet<Integer, Orders, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Orders, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getTotalSalesForOrderPreTax(param.getValue1()),
                Triplet.with(1, ordersJpaController.findOrders(1L), 17.0),
                Triplet.with(2, ordersJpaController.findOrders(2L), 18.0),
                Triplet.with(3, ordersJpaController.findOrders(3L), 31.0));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen order
         * @author Jean Robatto
         */
        @Test
        public void testNumberOfBooksPurchasedByAuthor(){
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
        
        @Inject
        private OrdersJpaController ordersJpaController;
        
        private Triplet<Integer, Orders, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Triplet<Integer, Orders, Double>> rule = new ParameterRule("param", "result",
                () -> bookorderJpaController.getHSTForOrder(param.getValue1()),
                Triplet.with(1, ordersJpaController.findOrders(1L), null),
                Triplet.with(2, ordersJpaController.findOrders(2L), null),
                Triplet.with(3, ordersJpaController.findOrders(3L), 26.0));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen order
         * @author Jean Robatto
         */
        @Test
        public void testNumberOfBooksPurchasedByAuthor(){
            int testNumber = param.getValue0();
            double expectedTotalTax = param.getValue2();
            
            assertEquals("Test " + testNumber + " did not return the correct total sales", expectedTotalTax, this.result, 0.01f);
        }

    }
    
    //TODO Repeat fot GST and PST
    
}
