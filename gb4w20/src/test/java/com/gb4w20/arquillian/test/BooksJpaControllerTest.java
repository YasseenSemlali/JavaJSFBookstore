package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Quartet;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for Books queries
 *
 * @author Yasseen
 */
@RunWith(Enclosed.class)
public class BooksJpaControllerTest {

    private final static Logger LOG = LoggerFactory.getLogger(BooksJpaControllerTest.class);
    
    public static class NonParametrized extends ArquillianTestBase {

        @Test
        public void test() {
            System.out.println("test");
        }
    }

    public static class SampleParametrized extends ArquillianTestBase {

        @Rule
        public ParameterRule<Pair<String, String>> rule = new ParameterRule<Pair<String, String>>("param",
                Pair.of("test1", "test2"),
                Pair.of("test3", "test4"),
                Pair.of("test5", "test6"));
        
        private Pair<String, String> param;
        
        @Test
        public void test() {
            System.out.println(param);
        }
    }
    
    /**
     * Used to hold tests for the findBooksThatWereNeverSold method 
     * in the BooksJpaContoller
     * @author Jeffrey Boisvert
     */
    public static class FindBooksThatWereNeverSold extends ArquillianTestBase{
        
        @Inject
        BooksJpaController booksJpaController; 
        
        @Rule
        public ParameterRule rule = new ParameterRule("param",
                //test number, startDate, endDate, expected resultset size
                new Quartet<Integer, String, String, Integer>(1, "2020-01-01", "2020-03-03", 6),
                new Quartet<Integer, String, String, Integer>(2, "2019-01-01", "2019-03-03", 8),
                new Quartet<Integer, String, String, Integer>(3, "2020-01-23", "2020-01-23", 7),
                new Quartet<Integer, String, String, Integer>(4, "2020-02-22", "2020-02-22", 7),
                new Quartet<Integer, String, String, Integer>(5, "1999-02-22", "2020-01-23", 7)
                );
        
        private Quartet<Integer, String, String, Integer> param;
        
        @Test
        public void TestCorrectNumberOfBooksAreReturned(){
            
            int testNumber = param.getValue0();
            String startDate = param.getValue1();
            String endDate = param.getValue2();
            int expectedResultSetSize = param.getValue3();
            
            List<Books> books = booksJpaController.findBooksThatWereNeverSold(startDate, endDate);
            
            assertEquals("Test " + testNumber + " did not return the correct number of books", expectedResultSetSize, books.size());
            
        }
        
    }

}
