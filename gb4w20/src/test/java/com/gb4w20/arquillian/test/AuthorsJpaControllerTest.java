package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.querybeans.NameTotalAndCountBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
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
        
        private Quintet<Integer, Long, String, String, Double> param;
        
        //holds the result of the method being tested
        private double result;
        
        @Rule
        public ParameterRule<Quintet<Integer, Long, String, String, Double>> rule = new ParameterRule("param", "result",
                () -> authorsJpaController.getAuthorsTotalSales(param.getValue1(), param.getValue2(), param.getValue3()),
                Quintet.with(1, 1l, "2020-01-23", "2020-03-21", 64.00),
                Quintet.with(2, 2l, "2020-01-23", "2020-03-21", 34.00),
                Quintet.with(3, 3l, "2020-01-23", "2020-03-21", 56.00),
                Quintet.with(4, 6l, "2020-01-01", "2020-03-21", 24.00),
                Quintet.with(5, 9l, "2020-01-01", "2020-03-21", 10.00));
        
        /**
         * Used to test if the total sales given is equals to
         * the expected total sales from a chosen author in given dates
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectTotalSales(){
            int testNumber = param.getValue0();
            double expectedTotalSales = param.getValue4();
            
            assertEquals("Test " + testNumber + " did not return the correct total sales of this author", expectedTotalSales, this.result, 0.01f);
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
               
        private Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>> param;
        
        //holds the result of the method being tested
        private List<NameTotalAndCountBean> result;
        
        @Rule
        public ParameterRule<Quintet<Integer, Long, String, String, List<NameTotalAndCountBean>>> rule = new ParameterRule("param", "result",
                () -> authorsJpaController.getPurchasedBooksByAuthor(param.getValue1(), param.getValue2(), param.getValue3()),
                Quintet.with(1, 1l, "2020-01-23", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal("34.00"), 2l), new NameTotalAndCountBean("Harry Potter and the Deathly Hallows", new BigDecimal("30.00"), 2l)))),
                Quintet.with(2, 2l, "2020-01-23", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal("34.00"), 2l)))),
                Quintet.with(3, 3l, "2020-01-23", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("Harry Potter and the Chamber of Secrets", new BigDecimal("26.00"), 2l), new NameTotalAndCountBean("Harry Potter and the Deathly Hallows", new BigDecimal("30.00"), 2l)))),
                Quintet.with(4, 6l, "2020-01-01", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("Red Seas Under Red Skies", new BigDecimal("24.00"), 2l)))),
                Quintet.with(5, 9l, "2020-01-01", "2020-03-21", new ArrayList<NameTotalAndCountBean>(Arrays.asList(new NameTotalAndCountBean("And Another Thing...", new BigDecimal("10.00"), 1l)))));
        
        /**
         * Used to test if the number of books purchased given is equals to
         * the number of expected books purchased from a chosen author in given dates
         * @author Jasmar Badion
         */
        @Test
        public void testNumberOfBooksPurchasedByAuthor(){
            int testNumber = param.getValue0();
            List<NameTotalAndCountBean> expectedPurchasedBooks = param.getValue4();
            
            assertEquals("Test " + testNumber + " did not return the correct number of purchased books of this author", expectedPurchasedBooks.size(), this.result.size());
        }
        
        /**
         * Used to test if the books purchased given is equals to
         * the expected books purchased from a chosen author in given dates
         * @author Jasmar Badion
         */
        @Test
        public void testCorerctBooksPurchasedByAuthor(){
            int testNumber = param.getValue0();
            List<NameTotalAndCountBean> expectedPurchasedBooks = param.getValue4();
            
            List<String> expectedTitles = takeTitlesFromPurchasedBooks(expectedPurchasedBooks);
           
            assertTrue("Test " + testNumber + " does not contain expected books", expectedTitles.containsAll(takeTitlesFromPurchasedBooks(this.result)));
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
    
    /**
     * Used to run valid tests for the getOtherBooksBySameAuthor 
     * method inside of the AuthorsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetOtherBooksBySameAuthor extends ArquillianTestBase {

        @Inject
        private AuthorsJpaController authorsJpaController;
        
        private Quintet<Integer, Long, Collection<Authors>, Integer, List<Books>> param;
        
        //holds the result of the method being tested
        private List<Books> result;
        
        @Rule
        public ParameterRule<Quintet<Integer, Long, Collection<Authors>, Integer, List<Books>>> rule = new ParameterRule("param", "result",
                () -> authorsJpaController.getOtherBooksBySameAuthor(param.getValue1(), param.getValue2(), param.getValue3()),
                Quintet.with(1, 9780000000000l, new ArrayList<Authors>(Arrays.asList(new Authors(1l), new Authors(2l))), 3, new ArrayList<Books>(Arrays.asList(new Books(9780545010221l)))),
                Quintet.with(2, 9780439064866l, new ArrayList<Authors>(Arrays.asList(new Authors(3l), new Authors(4l))), 3, new ArrayList<Books>(Arrays.asList(new Books(9780545010221l)))),
                Quintet.with(3, 9780545010221l, new ArrayList<Authors>(Arrays.asList(new Authors(3l), new Authors(1l))), 3, new ArrayList<Books>(Arrays.asList(new Books(9780000000000l), new Books(9780439064866l)))),
                Quintet.with(4, 9780316251303l, new ArrayList<Authors>(Arrays.asList(new Authors(5l))), 3, new ArrayList<Books>()),
                Quintet.with(5, 9781401323585l, new ArrayList<Authors>(Arrays.asList(new Authors(9l))), 1, new ArrayList<Books>()));
               
        /**
         * Used to test if the number of books by same author given is equals to
         * the expected number of books by same author of a chosen book
         * @author Jasmar Badion
         */
        @Test
        public void testNumberOfBooksBySameAuthor(){
            int testNumber = param.getValue0();
            List<Books> expectedOtherBooks = param.getValue4();
            
            assertEquals("Test " + testNumber + " did not return the correct number of books by same authors", expectedOtherBooks.size(), this.result.size());
        }
        
        /**
         * Used to test if the books by same author given is equals to
         * the expected books by same author of a chosen book
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectBooksBySameAuthor(){
            int testNumber = param.getValue0();
            List<Books> expectedOtherBooks = param.getValue4();
            
            assertEquals("Test " + testNumber + " did not return the correct other books by same authors", expectedOtherBooks, this.result);
        }
        
        /**
         * Tests if the result does not contain the same book given
         * @author Jasmar Badion
         */
        @Test
        public void testResultNotContainSameBook(){
            int testNumber = param.getValue0();
            long isbn = param.getValue1();
            
            assertTrue( "Test " + testNumber + ": The same book is present in the result set", booksDoNotContainTheGivenIsbn(this.result, isbn));
        }
        
        /**
         * Helper method to check if the book chosen is not part of
         * the result set
         * @param books
         * @param isbn
         * @return 
         */
        private boolean booksDoNotContainTheGivenIsbn(List<Books> books, long isbn){
            
            for(Books book : books){
                if(book.getIsbn() == isbn) {
                    return false;
                }
            }
            
            return true; 
        }
    }
}
