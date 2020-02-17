//
//package com.gb4w20.arquillian.test;
//
//import com.gb4w20.gb4w20.entities.Users;
//import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
//import com.gb4w20.gb4w20.jpa.UsersJpaController;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
//import java.io.StringReader;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Scanner;
//import javax.annotation.Resource;
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.sql.DataSource;
//import javax.transaction.UserTransaction;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Used to conduct Arquillian tests on the UserActionBean. 
// * Some of this code is based from the arquillian tests found in 
// * many of Ken Fogel's web projects. 
// * 
// * @author Jeffrey Boisvert
// */
//@RunWith(Arquillian.class)
//public class UserJpaControllerTests {
//    
//    private final static Logger LOG = LoggerFactory.getLogger(UserJpaControllerTests.class);
//
//    @Deployment
//    public static WebArchive deploy() {
//
//        // Use an alternative to the JUnit assert library called AssertJ
//        // Need to reference MySQL driver as it is not part of either
//        // embedded or remote
//        final File[] dependencies = Maven
//                .resolver()
//                .loadPomFromFile("pom.xml")
//                .resolve("mysql:mysql-connector-java",
//                        "org.assertj:assertj-core",
//                        "org.slf4j:slf4j-api",
//                        "org.apache.logging.log4j:log4j-slf4j-impl",
//                        "org.apache.logging.log4j:log4j-web"
//                ).withTransitivity()
//                .asFile();
//
//        // The SQL script to create the database is in src/test/resources
//        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
//                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
//                .addPackage(UsersJpaController.class.getPackage())
//                .addPackage(RollbackFailureException.class.getPackage())
//                .addPackage(Users.class.getPackage())
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/payara-resources.xml"), "payara-resources.xml")
//                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
//                .addAsResource(new File("src/main/resources/log4j2.xml"), "log4j2.xml")
//                .addAsResource("createbookstore.sql")
//                .addAsLibraries(dependencies);
//
//        return webArchive;
//    }
//
//    @Inject
//    private UsersJpaController usersJpaController; //No bean matches userController
//
//    @Resource(lookup = "java:app/jdbc/bookstore")
//    private DataSource dataSource;
//
//    @PersistenceContext(unitName = "BookPU")
//    private EntityManager enitityManager;
//
//    @Resource
//    private UserTransaction userTransaction;
//    
//    /**
//     * Used to test if a User can be successful created. 
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testCreate() throws RollbackFailureException {
//        
//        Users user = createTestUser();
//
//        userController.create(user);
//
//        Users returnedUser = userController.findUsers(user.getUserId());
//        assertEquals("The made user and the created user in the database do not match.", returnedUser, user);
//
//    }
//    
//    /**
//     * Used to test if a User can be edited correctly.  
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testEditWithName() throws RollbackFailureException, Exception {
//        
//        String testName = "ThisIsATestName"; 
//        
//        Users user = createTestUser();
//        userController.create(user);
//        
//        String originalName = user.getFirstName();
//        user.setFirstName(testName);
//        
//        userController.edit(user);
//
//        Users returnedUser = userController.findUsers(user.getUserId());
//        assertNotEquals("The user's name was not changed correctly", returnedUser.getFirstName(), originalName);
//
//    }
//    
//    /**
//     * Used to test if a User can be edited correctly.  
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testEditWithAddress() throws RollbackFailureException, Exception {
//        
//        String testAddress = "ThisIsATestAddress"; 
//        
//        Users user = createTestUser();
//        userController.create(user);
//        
//        String originalAddress = user.getFirstName();
//        user.setAddress1(testAddress);
//        
//        userController.edit(user);
//
//        Users returnedUser = userController.findUsers(user.getUserId());
//        assertNotEquals("The user's address was not changed correctly", returnedUser.getAddress1(), originalAddress);
//
//    }
//    
//    /**
//     * Used to test if a User just added can be found with id
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testFindUserById() throws RollbackFailureException, Exception {
//        
//        Users user = createTestUser();
//        
//        userController.create(user);
//
//        Users returnedUser = userController.findUsers(1l);
//        assertEquals("Did not find the user with id 1 in the database.", user, returnedUser);
//
//    }
//    
//    /**
//     * Used to test if a User just added can be found with id that is not just the first
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testFindUserByIdSecondCreated() throws RollbackFailureException, Exception {
//        
//        Users user = createTestUser();
//        userController.create(user);
//        
//        Users secondUser = createTestUser();
//        userController.create(secondUser);
//
//        Users returnedUser = userController.findUsers(2l);
//        assertEquals("Did not find the user with id 1 in the database.", secondUser, returnedUser);
//
//    }
//    
//    /**
//     * Used to test if able to find users with a firstname given. 
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testFindUsersByFirstName() throws RollbackFailureException, Exception {
//        
//        int expectedSize = 2; 
//        
//        Users user = createTestUser();
//        userController.create(user);
//        
//        Users secondUser = createTestUser();
//        userController.create(secondUser);
//
//        List<Users> returnedUser = userController.findUsersByFirstName("Test");
//        assertEquals("Did not find all the users with the first name similar to Test", returnedUser.size(), expectedSize);
//
//    }
//    
//    /**
//     * Used to test if correct behaviour if not users have a similar first name
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testFindUsersByFirstNameNoResult() throws RollbackFailureException, Exception {
//        
//        int expectedSize = 0; 
//        
//        Users user = createTestUser();
//        userController.create(user);
//        
//        Users secondUser = createTestUser();
//        userController.create(secondUser);
//
//        List<Users> returnedUser = userController.findUsersByFirstName("I am not a real name");
//        assertEquals("Found results even though there should be none", returnedUser.size(), expectedSize);
//
//    }
//    
//    /**
//     * Used to test if able to find users with a last name given. 
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testFindUsersByLastName() throws RollbackFailureException, Exception {
//        
//        int expectedSize = 2; 
//        
//        Users user = createTestUser();
//        userController.create(user);
//        
//        Users secondUser = createTestUser();
//        userController.create(secondUser);
//
//        List<Users> returnedUser = userController.findUsersByLastName("Test");
//        assertEquals("Did not find all the users with the last name similar to Test", returnedUser.size(), expectedSize);
//
//    }
//    
//    /**
//     * Used to test if correct behaviour if not users have a similar last name
//     * @throws RollbackFailureException
//     * @author Jeffrey Boisvert
//     */
//    @Test
//    public void testFindUsersByLastNameNoResult() throws RollbackFailureException, Exception {
//        
//        int expectedSize = 0; 
//        
//        Users user = createTestUser();
//        userController.create(user);
//        
//        Users secondUser = createTestUser();
//        userController.create(secondUser);
//
//        List<Users> returnedUser = userController.findUsersByLastName("I am not a real name");
//        assertEquals("Found results even though there should be none", returnedUser.size(), expectedSize);
//
//    }
//
////    /**
////     * Restore the database to a known state before testing. This is important
////     * if the test is destructive. This routine is courtesy of Bartosz Majsak
////     * who also solved my Arquillian remote server problem
////     * @author Bartosz Majsak, Ken Fogel
////     */
////    @Before
////    public void seedDatabase() {
////        final String seedDataScript = loadAsString("createbookstore.sql");
////        
////        if (dataSource == null){
////            System.out.println("Datasource is null");
////        }
////        
////        try (Connection connection = dataSource.getConnection()) {
////            for (String statement : splitStatements(new StringReader(
////                    seedDataScript), ";")) {
////                connection.prepareStatement(statement).execute();
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException("Failed seeding database", e);
////        }
////    }
////    
////    /**
////     * Methods supporting the seedDatabse method
////     * @author Bartosz Majsak, Ken Fogel
////     */
////    private String loadAsString(final String path) {
////        try (InputStream inputStream = Thread.currentThread()
////                .getContextClassLoader().getResourceAsStream(path)) {
////            return new Scanner(inputStream).useDelimiter("\\A").next();
////        } catch (IOException e) {
////            throw new RuntimeException("Unable to close input stream.", e);
////        }
////    }
////    
////    /**
////     * Methods supporting the seedDatabse method
////     * @author Bartosz Majsak, Ken Fogel
////     */
////    private List<String> splitStatements(Reader reader,
////            String statementDelimiter) {
////        final BufferedReader bufferedReader = new BufferedReader(reader);
////        final StringBuilder sqlStatement = new StringBuilder();
////        final List<String> statements = new LinkedList<>();
////        try {
////            String line;
////            while ((line = bufferedReader.readLine()) != null) {
////                line = line.trim();
////                if (line.isEmpty() || isComment(line)) {
////                    continue;
////                }
////                sqlStatement.append(line);
////                if (line.endsWith(statementDelimiter)) {
////                    statements.add(sqlStatement.toString());
////                    sqlStatement.setLength(0);
////                }
////            }
////            return statements;
////        } catch (IOException e) {
////            throw new RuntimeException("Failed parsing sql", e);
////        }
////    }
////    
////    /**
////     * Methods supporting the seedDatabse method
////     * @author Bartosz Majsak, Ken Fogel
////     */
////    private boolean isComment(final String line) {
////        return line.startsWith("--") || line.startsWith("//")
////                || line.startsWith("/*");
////    }
//    
//    /**
//     * Used as a helper method to create a user with predefined 
//     * values to be used for testing purposes. 
//     * @return a user with predefined values
//     * @author Jeffrey Boisvert
//     */
//    private Users createTestUser(){
//        Users user = new Users();
//        
//        user.setAddress1("123 TestStreet");
//        user.setAddress2("A");
//        user.setCellPhone("1234567891");
//        user.setCity("Testville");
//        user.setCompanyName("Dawson College");
//        user.setCountry("Canada");
//        user.setEmail("test@test.com");
//        user.setFirstName("Tester");
//        user.setLastName("Testerson");
//        user.setHomePhone("1234567891");
//        user.setIsManager(false);
//        user.setPassword("password");
//        user.setPostalCode("J5L2C8");
//        user.setProvince("QC");
//        user.setTitle("Mr.");
//        
//        return user;
//    }
//    
//}
