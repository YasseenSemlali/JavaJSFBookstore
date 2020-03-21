//package com.gb4w20.arquillian.test;
//
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
//        public ParameterRule<String> rule = new ParameterRule<String>("param",
//                "test1", 
//                "test2", 
//                "test3");
//        
//        private String param;
//        
//        @Test
//        public void test() {
//            System.out.println(param);
//        }
//    }
//
//}
