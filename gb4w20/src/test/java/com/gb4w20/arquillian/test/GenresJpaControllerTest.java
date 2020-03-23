package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Genres;
import com.gb4w20.gb4w20.jpa.GenresJpaController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

/**
 * Used to test various methods inside the GenresJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jeffrey Boisvert, Jasmar Badion
 */
@RunWith(Enclosed.class)
public class GenresJpaControllerTest {
    
    /**
     * Used to run valid tests for the getOtherBooksOfSameGenres 
     * method inside of the GenresJpaController class. 
     * @author Jeffrey Boisvert, Jasmar Badion
     */
    public static class GetOtherBooksOfSameGenres extends ArquillianTestBase {

        @Rule
        public ParameterRule rule = new ParameterRule("param",
                //test number, isbn, genreId, authorId, maxResults, expected resultset size
                new Sextet<Integer, Long, Collection<Genres>, Collection<Authors>, Integer, Integer>(1, 9780000000006l, new ArrayList<Genres>(Arrays.asList(new Genres(1l))), new ArrayList<Authors>(Arrays.asList(new Authors(6l))), 3, 3),
                new Sextet<Integer, Long, Collection<Genres>, Collection<Authors>, Integer, Integer>(2, 9780000000010l, new ArrayList<Genres>(Arrays.asList(new Genres(2l))), new ArrayList<Authors>(Arrays.asList(new Authors(8l))), 7, 2),
                new Sextet<Integer, Long, Collection<Genres>, Collection<Authors>, Integer, Integer>(3, 9780316251303l, new ArrayList<Genres>(Arrays.asList(new Genres(1l))), new ArrayList<Authors>(Arrays.asList(new Authors(5l))), 7, 4),
                new Sextet<Integer, Long, Collection<Genres>, Collection<Authors>, Integer, Integer>(4, 9780765377067l, new ArrayList<Genres>(Arrays.asList(new Genres(2l))), new ArrayList<Authors>(Arrays.asList(new Authors(7l))), 5, 2),
                new Sextet<Integer, Long, Collection<Genres>, Collection<Authors>, Integer, Integer>(5, 9781401323585l, new ArrayList<Genres>(Arrays.asList(new Genres(2l))), new ArrayList<Authors>(Arrays.asList(new Authors(9l))), 1, 1)
        );
        
        private Sextet<Integer, Long, Collection<Genres>, Collection<Authors>, Integer, Integer> param;
        
        @Inject
        private GenresJpaController genresJpaController;

        
        /**
         * Used to test the result set is always the correct size
         * @author Jeffrey Boisvert
         */
        @Test
        public void testCorrectDataSetSize() {
            
            int testNumber = param.getValue0();
            long isbn = param.getValue1();
            Collection<Genres> genres = param.getValue2();
            Collection<Authors> authors = param.getValue3(); 
            int maxResults = param.getValue4();
            int expectedResultSetSize = param.getValue5();
            
            List<Books> books = genresJpaController.getOtherBooksOfSameGenre(isbn, genres, authors, maxResults);
            
            assertEquals( "Test " + testNumber + ": Expected number of books found was incorrect", expectedResultSetSize, books.size());

        }
        
        /**
         * Used to test the result set never contains the same author
         * @author Jeffrey Boisvert
         */
        @Test
        public void testResultSetDoesNotContainSameAuthor() {
            
            int testNumber = param.getValue0();
            long isbn = param.getValue1();
            Collection<Genres> genres = param.getValue2();
            Collection<Authors> authors = param.getValue3(); 
            int maxResults = param.getValue4();
            
            List<Books> books = genresJpaController.getOtherBooksOfSameGenre(isbn, genres, authors, maxResults);
            
            assertTrue( "Test " + testNumber + ": One of the books were written by the same author", booksAreNotByGivenAuthor(books, authors));

        }
        
        /**
         * Used to know if any of the books are by the author with the given author id
         * @param books to check 
         * @param authorId in question 
         * @return true if none of the books were written by the author and false otherwise
         */
        private boolean booksAreNotByGivenAuthor(List<Books> books, Collection<Authors> authors){
            
            for(Books book : books){
                for(Authors author : book.getAuthorsCollection()){
                    if(authors.contains(author)){
                        return false; 
                    }
                }
            }
            
            return true; 
        }
        
        /**
         * Used to test the result set never contains the same book
         * @author Jeffrey Boisvert
         */
        @Test
        public void testResultSetDoesNotContainSameBook() {
            
            int testNumber = param.getValue0();
            long isbn = param.getValue1();
            Collection<Genres> genres = param.getValue2();
            Collection<Authors> authors = param.getValue3();
            int maxResults = param.getValue4();
            
            List<Books> books = genresJpaController.getOtherBooksOfSameGenre(isbn, genres, authors, maxResults);
            
            assertTrue( "Test " + testNumber + ": The same book is present in the result set", booksDoNotContainTheGivenIsbn(books, isbn));

        }
        
        /**
         * Used to know if any of the books in the list have the isbn given
         * @param books to check 
         * @param isbn in question 
         * @return true if none of the books have the same isbn and false otherwise
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
