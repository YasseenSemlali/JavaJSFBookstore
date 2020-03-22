package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
import com.gb4w20.gb4w20.querybeans.NameTotalAndCountBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * <h1>Authors JPA Controller Unit Test</h1>
 * <p>
 * Used to test various methods inside the AuthorsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * </p>
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
                new Quintet<Integer, Long, String, String, Double>(1, 1l, "2020-01-23", "2020-03-21", 17.00),
                new Quintet<Integer, Long, String, String, Double>(2, 2l, "2020-01-23", "2020-03-21", 17.00),
                new Quintet<Integer, Long, String, String, Double>(3, 3l, "2020-01-23", "2020-03-21", 0.00),
                new Quintet<Integer, Long, String, String, Double>(4, 6l, "2020-01-01", "2020-03-21", 0.00),
                new Quintet<Integer, Long, String, String, Double>(5, 9l, "2020-01-01", "2020-03-21", 10.00)
                );
        
        private Quintet<Integer, Long, String, String, Double> param;
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen author in given dates
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
    
    /**
     * Used to run valid tests for the getPurchasedBooksByAuthor 
     * method inside of the AuthorsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetPurchasedBooksByAuthor extends ArquillianTestBase {

        @Inject
        private AuthorsJpaController authorsJpaController;
        
        @Rule
        public ParameterRule rule = new ParameterRule("param",
                //test number, id, startDate, endDate, expected result
                new Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>>(1, 1l, "2020-01-23", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal("17.00"), 1l)))),
                new Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>>(2, 2l, "2020-01-23", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal("17.00"), 1l)))),
                new Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>>(3, 3l, "2020-01-23", "2020-03-21", new ArrayList<NameTotalAndCountBean>()),
                new Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>>(4, 6l, "2020-01-01", "2020-03-21", new ArrayList<NameTotalAndCountBean>()),
                new Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>>(3, 9l, "2020-01-01", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("And Another Thing...", new BigDecimal("10.00"), 1l))))
                );
        
        private Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>> param;
        
        /**
         * Used to test if the number of books purchased given is equals to
         * the number of expected books purchased from a chosen author in given dates
         * @author Jasmar Badion
         */
        @Test
        public void testNumberOfBooksPurchasedByAuthor(){
            int testNumber = param.getValue0();
            long authorId = param.getValue1();
            String startDate = param.getValue2();
            String endDate = param.getValue3();
            List<NameTotalAndCountBean> expectedPurchasedBooks = param.getValue4();
            
            List<NameTotalAndCountBean> purchasedBooks = authorsJpaController.getPurchasedBooksByAuthor(authorId, startDate, endDate);
            
            assertEquals("Test " + testNumber + " did not return the correct number of purchased books of this author", expectedPurchasedBooks.size(), purchasedBooks.size());
        }
        
        /**
         * Used to test if the books purchased given is equals to
         * the expected books purchased from a chosen author in given dates
         * @author Jasmar Badion
         */
        @Test
        public void testCorerctBooksPurchasedByAuthor(){
            int testNumber = param.getValue0();
            long authorId = param.getValue1();
            String startDate = param.getValue2();
            String endDate = param.getValue3();
            List<NameTotalAndCountBean> expectedPurchasedBooks = param.getValue4();
            
            List<String> expectedTitles = takeTitlesFromPurchasedBooks(expectedPurchasedBooks);
            
            List<NameTotalAndCountBean> purchasedBooks = authorsJpaController.getPurchasedBooksByAuthor(authorId, startDate, endDate);
           
            assertTrue("Test " + testNumber + " does not contain expected books", expectedTitles.containsAll(takeTitlesFromPurchasedBooks(purchasedBooks)));
        }
        
        /**
         * Helper method that takes the titles from the purchased books
         * of a chosen author
         * @param books
         * @return 
         */
        private List<String> takeTitlesFromPurchasedBooks(List<NameTotalAndCountBean> books){
            List<String> titles = new ArrayList<>();
            
            for(NameTotalAndCountBean book : books){
                titles.add(book.getName());
            }
            
            return titles;
        }
    }
}
