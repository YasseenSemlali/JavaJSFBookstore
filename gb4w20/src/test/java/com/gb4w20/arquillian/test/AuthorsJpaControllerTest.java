package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
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
 * Used to test various methods inside the AuthorsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jasmar Badion
 */
@RunWith(Enclosed.class)
public class AuthorsJpaControllerTest {
    
    /**
     * Used to run valid tests for the getAuthorsTotalSales 
     * method inside of the AuthorsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetAuthorsTotalSales extends ArquillianTestBase {

        @Rule
        public ParameterRule rule = new ParameterRule("param",
                //test number, id, startDate, endDate, expected result
                new Quintet<Integer, Long, String, String, Double>(1, 1l, "2020-01-23 05:03:16", "2020-03-21 05:03:16", 17.0)
                );
        
        private Quintet<Integer, Long, String, String, Integer> param;
        
        @Inject
        private AuthorsJpaController authorsJpaController;
        
    }   
}
