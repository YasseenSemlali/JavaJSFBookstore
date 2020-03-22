package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.querybeans.NameTotalAndCountBean;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Quartet;
import org.javatuples.Septet;
import org.javatuples.Triplet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for Books queries
 *
 * @author Yasseen, Jeffrey Boisvert
 */
@RunWith(Enclosed.class)
public class BooksJpaControllerTest {

    private final static Logger LOG = LoggerFactory.getLogger(BooksJpaControllerTest.class);
    
//    public static class NonParametrized extends ArquillianTestBase {
//
//        @Test
//        public void test() {
//            System.out.println("test");
//        }
//    }
//
//    public static class SampleParametrized extends ArquillianTestBase {
//
//        @Rule
//        public ParameterRule<Pair<String, String>> rule = new ParameterRule<Pair<String, String>>("param",
//                Pair.of("test1", "test2"),
//                Pair.of("test3", "test4"),
//                Pair.of("test5", "test6"));
//        
//        private Pair<String, String> param;
//        
//        @Test
//        public void test() {
//            System.out.println(param);
//        }
//    }
    
//    /**
//     * Used to hold tests for the findBooksThatWereNeverSold method 
//     * in the BooksJpaContoller
//     * @author Jeffrey Boisvert
//     */
//    public static class FindBooksThatWereNeverSoldTest extends ArquillianTestBase{
//        
//        @Inject
//        BooksJpaController booksJpaController; 
//        
//        @Rule
//        public ParameterRule rule = new ParameterRule("param",
//                //test number, startDate, endDate, expected resultset size
//                new Quartet<Integer, String, String, Integer>(1, "2020-01-01", "2020-03-03", 6),
//                new Quartet<Integer, String, String, Integer>(2, "2019-01-01", "2019-03-03", 8),
//                new Quartet<Integer, String, String, Integer>(3, "2020-01-23", "2020-01-23", 7),
//                new Quartet<Integer, String, String, Integer>(4, "2020-02-22", "2020-02-22", 7),
//                new Quartet<Integer, String, String, Integer>(5, "1999-02-22", "2020-01-23", 7)
//                );
//        
//        private Quartet<Integer, String, String, Integer> param;
//        
//        /**
//         * Used to test if the correct number of books are returned
//         * in the result set. 
//         * @author Jeffrey Boisvert
//         */
//        @Test
//        public void testCorrectNumberOfBooksAreReturned(){
//            
//            int testNumber = param.getValue0();
//            String startDate = param.getValue1();
//            String endDate = param.getValue2();
//            int expectedResultSetSize = param.getValue3();
//            
//            List<Books> books = booksJpaController.findBooksThatWereNeverSold(startDate, endDate);
//            
//            assertEquals("Test " + testNumber + " did not return the correct number of books", expectedResultSetSize, books.size());
//            
//        }
//        
//    }

//    /**
//     * Used to hold tests for the findTopSellersTest method 
//     * in the BooksJpaContoller
//     * @author Jeffrey Boisvert
//     */
//    public static class FindTopSellersTest extends ArquillianTestBase{
//        
//        @Inject
//        BooksJpaController booksJpaController; 
//        
//        @Rule
//        public ParameterRule rule = new ParameterRule("param",
//                //test number, startDate, endDate, expected resultset size, expected title, expected amount, expected count
//                new Septet<Integer, String, String, Integer, String, BigDecimal, Long>(1, "2020-01-01", "2020-03-03", 2, "Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal(17), 1l),
//                new Septet<Integer, String, String, Integer, String, BigDecimal, Long>(2, "2019-01-01", "2019-03-03", 0, "Test", new BigDecimal(5), 4l),
//                new Septet<Integer, String, String, Integer, String, BigDecimal, Long>(3, "2020-01-23", "2020-01-23", 1, "And Another Thing...", new BigDecimal(10), 1l),
//                new Septet<Integer, String, String, Integer, String, BigDecimal, Long>(4, "2020-02-22", "2020-02-22", 1, "Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal(17), 1l),
//                new Septet<Integer, String, String, Integer, String, BigDecimal, Long>(5, "1999-02-22", "2020-01-23", 1, "And Another Thing...", new BigDecimal(10), 1l)
//                );
//        
//        private Septet<Integer, String, String, Integer, String, BigDecimal, Long> param;
//        
//        /**
//         * Used to test if the correct number of books are returned
//         * in the result set. 
//         * @author Jeffrey Boisvert
//         */
//        @Test
//        public void testCorrectNumberOfBooksAreReturned(){
//            
//            int testNumber = param.getValue0();
//            String startDate = param.getValue1();
//            String endDate = param.getValue2();
//            int expectedResultSetSize = param.getValue3();
//            
//            List<NameTotalAndCountBean> report = booksJpaController.findTopSellers(startDate, endDate);
//            
//            assertEquals("Test " + testNumber + " did not return the correct number of books", expectedResultSetSize, report.size());
//            
//        }
//        
//        /**
//         * Used to test if the first record is the top intended record
//         * @author Jeffrey Boisvert
//         */
//        @Test
//        public void testIfFirstRecordIsCorrect(){
//            
//            int testNumber = param.getValue0();
//            String startDate = param.getValue1();
//            String endDate = param.getValue2();
//            int expectedResultSetSize = param.getValue3();
//
//            String expectedName = param.getValue4();
//            BigDecimal expectedAmount = param.getValue5();
//            Long count = param.getValue6();
//            NameTotalAndCountBean expectedFirstRecord = new NameTotalAndCountBean(expectedName, expectedAmount, count);
//            
//            List<NameTotalAndCountBean> report = booksJpaController.findTopSellers(startDate, endDate);
//            
//            //Only test this for the records that are expecting records
//            if(expectedResultSetSize > 0){
//               assertEquals("Test " + testNumber + " did not return the correct first row", expectedFirstRecord, report.get(0));
//            }
//            
//        }
//        
//    }
    
    /**
     * Used to hold tests for the getActiveBooks method 
     * in the BooksJpaContoller
     * @author Jeffrey Boisvert
     */
//    public static class GetActiveBooksTest extends ArquillianTestBase{
//        
//        @Inject
//        BooksJpaController booksJpaController; 
//        
//        /**
//         * Used to test if all the books that are active are returned. 
//         * @author Jeffrey Boisvert
//         */
//        @Test
//        public void testIfAllActiveBooksReturned(){
//            
//            int expectedResultSetSize = 8; 
//            
//            List<Books> books = booksJpaController.getActiveBooks(); 
//            
//            assertEquals("Incorrect amount of books", expectedResultSetSize, books.size());
//            
//        }
//        
//        /**
//         * Used to test if all the books are returned
//         * when giving a negative number
//         * @author Jeffrey Boisvert
//         */
//        @Test 
//        public void testIfAllActiveBooksReturnedGivenANegativeParameter(){
//            
//            int expectedResultSetSize = 8; 
//            
//            List<Books> books = booksJpaController.getActiveBooks(-1); 
//            
//            assertEquals("Incorrect amount of books", expectedResultSetSize, books.size());
//
//        }
//        
//        /**
//         * Used to test if the max results 
//         * are returned correcty
//         * @author Jeffrey Boisvert
//         */
//        @Test 
//        public void testIfMaxResultReturned(){
//            
//            int expectedResultSetSize = 3; 
//            
//            List<Books> books = booksJpaController.getActiveBooks(3); 
//            
//            assertEquals("Incorrect amount of books", expectedResultSetSize, books.size());
//
//        }
//        
//        /**
//         * Used to test if the max results 
//         * are returned correctly when asking for a maximum 
//         * higher than the actual amount of active books
//         * @author Jeffrey Boisvert
//         */
//        @Test 
//        public void testAllBooksReturnedWithHighMaxResult(){
//            
//            int expectedResultSetSize = 8; 
//            
//            List<Books> books = booksJpaController.getActiveBooks(123456); 
//            
//            assertEquals("Incorrect amount of books", expectedResultSetSize, books.size());
//
//        }
//        
//        /**
//         * Used to test if the correct amount of books is returned
//         * even after disabling a book. 
//         * @author Jeffrey Boisvert
//         * @throws Exception 
//         */
//        @Test
//        public void testReturnOnlyActiveBooksAfterDisablingABook() throws Exception {
//            
//            int expectedResultSetSize = 7; 
//            
//            Books book = booksJpaController.findBooks(9780000000000l);
//            book.setActive(false);
//            booksJpaController.edit(book);
//            
//            List<Books> books = booksJpaController.getActiveBooks();
//            
//            assertEquals("Incorrect amount of books", expectedResultSetSize, books.size());
//            
//        }
//        
//        /**
//         * Used to test if there no active books
//         * @author Jeffrey Boisvert
//         * @throws java.lang.Exception
//         */
//        @Test
//        public void testNoActiveBooks() throws Exception {
//            
//            int expectedResultSetSize = 0; 
//            
//            List<Books> activeBooks = booksJpaController.getActiveBooks();
//            for(Books book : activeBooks){
//                book.setActive(false);
//                booksJpaController.edit(book);
//            }
//            
//            List<Books> books = booksJpaController.getActiveBooks();
//            
//            assertEquals("Incorrect amount of books", expectedResultSetSize, books.size());
//            
//        }
//        
//    }
    
    /**
     * Used to hold tests for the getAllBooksForGenres method 
     * in the BooksJpaContoller
     * @author Jeffrey Boisvert
     */
    public static class GetAllBooksForGenres extends ArquillianTestBase{
        
        @Inject
        BooksJpaController booksJpaController; 
        
        @Rule
        public ParameterRule rule = new ParameterRule("param",
                //test number, genreId, expected resultset size
                new Triplet<Integer, Long, Integer>(1, 1l, 5),
                new Triplet<Integer, Long, Integer>(2, 2l, 3),
                new Triplet<Integer, Long, Integer>(3, 100l, 0)
                );
        
        private Triplet<Integer, Long, Integer> param;
        
        /**
         * Used to test if the correct number of books are returned
         * in the result set. 
         * @author Jeffrey Boisvert
         */
        @Test
        public void testCorrectNumberOfBooksAreReturned(){
            
            int testNumber = param.getValue0();
            long genreId = param.getValue1();
            int expectedResultSetSize = param.getValue2();
            
            List<Books> books = booksJpaController.getAllBooksForGenre(genreId);
            
            assertEquals("Test " + testNumber + " did not return the correct number of books", expectedResultSetSize, books.size());
            
        }
        
        /**
         * Used to test and ensure all the 
         * results returned are active books. 
         * @author Jeffrey Boisvert
         */
        @Test
        public void testIfAllBooksReturnedAreActive(){
            
            int testNumber = param.getValue0();
            long genreId = param.getValue1();
            
            List<Books> books = booksJpaController.getAllBooksForGenre(genreId);
            
            assertTrue("Test " + testNumber + " contained an inactive book", isAllBooksActive(books));
            
        }
        
        /**
         * Used to test of the list of books are all active. 
         * @param books given 
         * @return true if books are active false otherwise
         * @author Jeffrey Boisvert
         */
        private boolean isAllBooksActive(List<Books> books){
            
            for(Books book : books){
                if (!book.getActive()){
                    return false;
                }
            }
            
            return true; 
            
        }
        
    }
    
}
