package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.gb4w20.jpa.OrdersJpaController;
import com.gb4w20.gb4w20.querybeans.NameTotalAndCountBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.javatuples.Quartet;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Orders JPA Controller Unit Test</h1>
 * <p>
 * Used to test various methods inside the OrdersJpaController class. This does
 * not include the methods that were auto generated by NetBeans (create, edit,
 * delete).
 * </p>
 *
 * @author Jean Robatto Inspired by the other test classes made by my teammates
 */
@RunWith(Enclosed.class)
public class OrdersJpaControllerTest {

    private final static Logger LOG = LoggerFactory.getLogger(OrdersJpaControllerTest.class);

    /**
     * Used to run valid tests for the getTotalSales method inside of the
     * OrdersJpaController class.
     *
     * @author Jean Robatto
     */
    public static class GetTotalSales extends ArquillianTestBase {

        @Inject
        private OrdersJpaController ordersJpaController;

        private Quartet<Integer, String, String, Double> param;

        //holds the result of the method being tested
        private double result;

        @Rule
        public ParameterRule<Quartet<Integer, String, String, Double>> rule = new ParameterRule("param", "result",
                () -> ordersJpaController.getTotalSales(param.getValue1(), param.getValue2()),
                Quartet.with(1, "2000-01-01", "3000-01-01", 212.0), //All total sales
                Quartet.with(2, "2020-01-01", "2020-01-30", 0.0), //Jan
                Quartet.with(3, "2000-02-01", "2020-02-29", 212.0), //Feb
                Quartet.with(4, "2000-02-01", "2020-02-14", 48.0), 
                Quartet.with(5, "2000-02-10", "2020-02-29", 212.0)); 

        /**
         * Used to test if the total sales given is equals to the expected total
         * sales from a chosen order
         *
         * @author Jean Robatto
         */
        @Test
        public void testCorrectTotal() {
            double expectedTotalSales = param.getValue3();
            assertEquals("Test " + param.getValue0() + " did not return the correct total sales", expectedTotalSales, this.result, 0.01f);
        }
    }

    /**
     * Used to run valid tests for the getPurchasedBooks method inside of the
     * OrdersJpaController class.
     *
     * @author Jean Robatto
     */
    public static class GetPurchasedBooks extends ArquillianTestBase {

        @Inject
        private OrdersJpaController ordersJpaController;

        private Quartet<Integer, String, String, List<NameTotalAndCountBean>> param;

        //holds the result of the method being tested
        private List<NameTotalAndCountBean> result;

        @Rule
        public ParameterRule<Quartet<Integer, String, String, List<NameTotalAndCountBean>>> rule = new ParameterRule("param", "result",
                () -> ordersJpaController.getPurchasedBooks(param.getValue1(), param.getValue2()),
                Quartet.with(1, "2000-01-01", "3000-01-01",
                        new ArrayList<NameTotalAndCountBean>() {
                    {
                        add(new NameTotalAndCountBean("And Another Thing...", new BigDecimal(10), 1L));
                        add(new NameTotalAndCountBean("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal(34), 2L));
                        add(new NameTotalAndCountBean("Harry Potter and the Chamber of Secrets", new BigDecimal(26), 2L));
                        add(new NameTotalAndCountBean("Harry Potter and the Deathly Hallows", new BigDecimal(30), 2L));
                        add(new NameTotalAndCountBean("Red Seas Under Red Skies", new BigDecimal(24), 2L));
                        add(new NameTotalAndCountBean("The Burning White", new BigDecimal(32), 2L));
                        add(new NameTotalAndCountBean("The Hitchhiker's Guide to the Galaxy", new BigDecimal(20), 1L));
                        add(new NameTotalAndCountBean("The Three-Body Problem", new BigDecimal(36), 2L));
                    }
                }
                ), //All total sales
                Quartet.with(2, "2020-01-01", "2020-01-31",
                        new ArrayList<NameTotalAndCountBean>() {
                    {
                        add(new NameTotalAndCountBean("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal(17), 1L));
                    }
                }
                ), //Jan
                Quartet.with(3, "2020-02-04", "2020-02-10",
                        new ArrayList<NameTotalAndCountBean>() {
                    {
                       add(new NameTotalAndCountBean("Harry Potter and the Deathly Hallows", new BigDecimal(15), 1L));
                       add(new NameTotalAndCountBean("The Burning White", new BigDecimal(16), 1L));
                    }
                }
                ), 
                Quartet.with(4, "2020-01-01", "2020-01-12",
                        new ArrayList<NameTotalAndCountBean>()
                ), 
                Quartet.with(5, "2020-02-01", "2020-02-29",
                        new ArrayList<NameTotalAndCountBean>() {
                    {
                        add(new NameTotalAndCountBean("And Another Thing...", new BigDecimal(10), 1L));
                        add(new NameTotalAndCountBean("Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch", new BigDecimal(17), 1L));
                        add(new NameTotalAndCountBean("Harry Potter and the Chamber of Secrets", new BigDecimal(26), 2L));
                        add(new NameTotalAndCountBean("Harry Potter and the Deathly Hallows", new BigDecimal(30), 2L));
                        add(new NameTotalAndCountBean("Red Seas Under Red Skies", new BigDecimal(24), 2L));
                        add(new NameTotalAndCountBean("The Burning White", new BigDecimal(32), 2L));
                        add(new NameTotalAndCountBean("The Hitchhiker's Guide to the Galaxy", new BigDecimal(20), 1L));
                        add(new NameTotalAndCountBean("The Three-Body Problem", new BigDecimal(36), 2L));
                    }
                }
                ) //Feb
        );

        /**
         * Used to test if the book information given is equals to the expected
         * info from a specific order
         *
         * @author Jean Robatto
         */
        @Test
        public void testCorrectResult() {
            List<NameTotalAndCountBean> expectedResult = param.getValue3();
            assertEquals("Test " + param.getValue0() + " did not return the correct result", expectedResult, this.result);
        }
    }

}
