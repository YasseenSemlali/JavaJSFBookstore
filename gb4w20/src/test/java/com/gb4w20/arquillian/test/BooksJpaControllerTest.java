//package com.gb4w20.arquillian.test;
//
//import org.apache.commons.lang3.tuple.Pair;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.experimental.runners.Enclosed;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Test for Books queries
// *
// * @author Yasseen
// */
//@RunWith(Enclosed.class)
//public class BooksJpaControllerTest {
//
//    private final static Logger LOG = LoggerFactory.getLogger(BooksJpaControllerTest.class);
//
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
//
//}
