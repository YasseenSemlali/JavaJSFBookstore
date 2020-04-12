
package com.gb4w20.arquillian.test.utils;

import com.gb4w20.entities.Books;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to hold helpful methods dealing with Books. 
 * This class is mainly used to conduct tests. 
 * @author Jeffrey Boisvert
 */
public class BooksUtilities {
   
        /**
         * Used to test of the list of books are all active. 
         * @param books given 
         * @return true if books are active false otherwise
         * @author Jeffrey Boisvert
         */
        public boolean areAllBooksActive(List<Books> books){
            
            for(Books book : books){
                if (!book.getActive()){
                    return false;
                }
            }
            
            return true; 
            
        }
        
        /**
         * Helper method just to get all the isbns from the given list of books
         * @param books given
         * @return a list of the isbns of the books. 
         * @author Jeffrey Boisvert
         */
        public List<Long> getIsbnsFromBooks(List<Books> books){
            
            List<Long> isbns = new ArrayList<>(); 
            
            for (Books book : books){
                isbns.add(book.getIsbn());
            }
            
            return isbns;
            
        }
        
    
}
