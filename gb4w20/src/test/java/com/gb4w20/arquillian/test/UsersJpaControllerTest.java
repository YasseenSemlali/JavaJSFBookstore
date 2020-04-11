
package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.entities.Users;
import com.gb4w20.jpa.UsersJpaController;
import com.gb4w20.querybeans.NameAndNumberBean;
import com.gb4w20.querybeans.NameTotalAndCountBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Pair;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

/**
 * Used to test various methods inside the UsersJpaController class. This does
 * not include the methods that were auto generated by NetBeans (create, edit,
 * delete, find).
 *
 * @author Yasseen Semlali
 */
@RunWith(Enclosed.class)
public class UsersJpaControllerTest {
    public static class TestFindUsers extends ArquillianTestBase {

        @Inject
        UsersJpaController controller;

        @Rule
        public ParameterRule<Triplet<String, String, Long>> rule = new ParameterRule<Triplet<String, String, Long>>("param",
                Triplet.with("cst.send@gmail.com", "dawsoncollege", 1l),
                Triplet.with("cst.receive@gmail.com", "collegedawson", 2l)
        );

        private Triplet<String, String, Long> param;

        @Test
        public void testFindByEmail() {
            Users result = controller.findUsers(param.getValue0());

            assertEquals(param.getValue2(), result.getUserId());
        }

        @Test
        public void testFindByEmailAndPassword() {
            Users result = controller.findUserByEmailAndPassword(param.getValue0(), param.getValue1());

            assertEquals(param.getValue2(), result.getUserId());
        }

        @Test(expected = NoResultException.class)
        public void testFindByEmailEmpty() {
            Users result = controller.findUsers("unknownemail");
        }

        @Test(expected = NoResultException.class)
        public void testFindByEmailAndPasswordEmpty() {
            Users result = controller.findUserByEmailAndPassword(param.getValue0(), "wrongpassword");
        }
    }

    public static class TestUserSalesBetweenDates extends ArquillianTestBase {

        @Inject
        private UsersJpaController controller;

        private Quartet<Long, String, String, Double> param;
        private Double result;

        @Rule
        public ParameterRule<Quartet<Long, String, String, Double>> rule = new ParameterRule("param", "result",
                () -> controller.getUsersTotalSales(param.getValue0(), param.getValue1(), param.getValue2()),
                Quartet.with(1l, "2020-01-31", "2020-02-10", 48d),
                Quartet.with(1l, "2020-02-01", "2020-03-30", 74d),
                Quartet.with(2l, "2020-01-31", "2020-02-10", 0d),
                Quartet.with(2l, "2020-02-01", "2020-03-30", 121d));

        @Test
        public void testUserSalesBetweenDates() {
            assertEquals(param.getValue3(), result, 0.01);
        }
    }

    public static class TestUserSalesTotal extends ArquillianTestBase {

        @Inject
        private UsersJpaController controller;

        private Pair<Long, Double> param;
        public Double result;
        
        @Rule
        public ParameterRule<Pair<Long, Double>> rule = new ParameterRule("param", "result",
                () -> controller.getUsersTotalSales(param.getValue0()),
                Pair.with(1l, 91d),
                Pair.with(2l, 121d));
        

        @Test
        public void testUserSalesTotal() {
            assertEquals(param.getValue1(), result, 0.01);
        }
    }

    public static class TestUserPurchasedBooks extends ArquillianTestBase {

        @Inject
        private UsersJpaController controller;

        private Quartet<Long, String, String, Double> param;
        private List<NameAndNumberBean> result;

        @Rule
        public ParameterRule<Quartet<Long, String, String, List<NameAndNumberBean>>> rule = new ParameterRule("param", "result",
                () -> controller.getUserPurchasedBooks(param.getValue0(), param.getValue1(), param.getValue2()),
                Quartet.with(1l, "2020-01-31", "2020-02-10", Arrays.asList(
                        new NameAndNumberBean("9780545010221 - Harry Potter and the Deathly Hallows", new BigDecimal(15.00)),
                        new NameAndNumberBean("9780316251303 - The Burning White", new BigDecimal(16.00)),
                        new NameAndNumberBean("9780000000000 - Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal(17.00))
                )),
                Quartet.with(2l, "2020-01-31", "2020-02-10", new ArrayList<NameAndNumberBean>()));

        @Test
        public void testUserPurchasedBooks() {
            assertEquals(param.getValue3(), result);
        }
    }

    public static class TestTopUsersBySales extends ArquillianTestBase {

        @Inject
        private UsersJpaController controller;

        private Triplet<String, String, List<NameTotalAndCountBean>> param;
        private List<NameTotalAndCountBean> result;

        @Rule
        public ParameterRule<Triplet<String, String, List<NameTotalAndCountBean>>> rule = new ParameterRule("param", "result",
                () -> controller.findTopUsersBySales(param.getValue0(), param.getValue1()),
                Triplet.with("2020-01-31", "2020-02-10", Arrays.asList(new NameTotalAndCountBean("John Doe", new BigDecimal(48), 3l))),
                Triplet.with("2020-00-00", "2020-03-31", Arrays.asList(new NameTotalAndCountBean("Jane Doe", new BigDecimal(121), 8l), new NameTotalAndCountBean("John Doe", new BigDecimal(91), 6l))));

        @Test
        public void testUserPurchasedBooks() {
            assertEquals(param.getValue2(), result);
        }
    }

}
