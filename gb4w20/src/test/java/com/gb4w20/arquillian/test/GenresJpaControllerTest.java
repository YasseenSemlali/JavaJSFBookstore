package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.GenresJpaController;
import java.util.List;
import javax.inject.Inject;
import org.javatuples.Quintet;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * Used to test various methods inside the GenresJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jeffrey Boisvert
 */
@RunWith(Enclosed.class)
public class GenresJpaControllerTest extends ArquillianTestBase{
    
    /**
     * Used to run valid tests for the getOtherBooksOfSameGenres 
     * method inside of the GenresJpaController class. 
     * @author Jeffrey Boisvert
     */
    public static class GetOtherBooksOfSameGenres extends ArquillianTestBase {

        @Rule
        public ParameterRule rule = new ParameterRule("param",
                //isbn, genreId, authorId, maxResults, expected resultset size
                new Object[] {1l, 1l, 1l, 0, 0} 
                );
        
        private Object[] param;
        
        @Inject
        GenresJpaController genresJpaController;
        
        /**
         * Used to test the result set is always the correct size
         * @author Jeffrey Boisvert
         */
        @Test
        public void testCorrectDataSetSize() {
            
            long isbn = (long)param[0];
            long genreId = (long)param[1];
            long authorId = (long)param[2]; 
            int maxResults = (int)param[3];
            int expectedResultSetSize = (int)param[4];
            
            List<Books> books = genresJpaController.getOtherBooksOfSameGenre(isbn, genreId, authorId, maxResults);
            
            assertEquals("Expected number of books returned " + books.size() + " did not match the expected result of " + expectedResultSetSize, books.size(), expectedResultSetSize);
            
        }
    }
    
}
