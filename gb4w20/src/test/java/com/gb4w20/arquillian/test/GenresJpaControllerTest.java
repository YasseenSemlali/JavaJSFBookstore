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
                new Quintet<Long, Long, Long, Integer, Integer>(1l, 1l, 1l, 2, 2),
                new Quintet<Long, Long, Long, Integer, Integer>(9780000000000l, 1l, 2l, 100, 4),
                new Quintet<Long, Long, Long, Integer, Integer>(9780765377067l, 2l, 7l, 100, 2),
                new Quintet<Long, Long, Long, Integer, Integer>(9780439064866l, 1l, 4l, 1, 1),
                new Quintet<Long, Long, Long, Integer, Integer>(9780545010221l, 1l, 1l, 100, 3)
                );
        
        private Quintet<Long, Long, Long, Integer, Integer> param;
        
        @Inject
        GenresJpaController genresJpaController;
        
        /**
         * Used to test the result set is always the correct size
         * @author Jeffrey Boisvert
         */
        @Test
        public void testCorrectDataSetSize() {
            
            long isbn = param.getValue0();
            long genreId = param.getValue1();
            long authorId = param.getValue2(); 
            int maxResults = param.getValue3();
            int expectedResultSetSize = param.getValue4();
            
            List<Books> books = genresJpaController.getOtherBooksOfSameGenre(isbn, genreId, authorId, maxResults);
            
            assertEquals("Expected number of books found was incorrect", books.size(), expectedResultSetSize);
            
        }
    }
    
}
