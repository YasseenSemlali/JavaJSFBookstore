
package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.UserActionBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to conduct Arquillian tests on the UserActionBean. 
 * Some of this code is based from the arquillian tests found in 
 * many of Ken Fogel's web projects. 
 * 
 * @author Jeffrey Boisvert
 */
public class UserActionBeanTests {
    
    private final static Logger LOG = LoggerFactory.getLogger(UserActionBeanTests.class);

    @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core",
                        "org.slf4j:slf4j-api",
                        "org.apache.logging.log4j:log4j-slf4j-impl",
                        "org.apache.logging.log4j:log4j-web"
                ).withTransitivity()
                .asFile();

        // The SQL script to create the database is in src/test/resources
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(UserActionBean.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addPackage(Users.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource(new File("src/main/resources/log4j2.xml"), "log4j2.xml")
                .addAsResource("createbookstore.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Inject
    private UserActionBean userActionBean;

    @Resource(lookup = "java:app/jdbc/bookstore")
    private DataSource dataSource;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager enitityManager;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Used to test if a User can be successful created. 
     * @throws RollbackFailureException
     * @throws IllegalStateException
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void testCreate() throws RollbackFailureException, IllegalStateException, Exception {
        Users user = createTestUser();

        userActionBean.create(user);

        Users returnedUser = userActionBean.findUser(user.getUserId());

        assertEquals("The made user and the created user in the database do not match.", returnedUser, user);

    }

    /**
     * Restore the database to a known state before testing. This is important
     * if the test is destructive. This routine is courtesy of Bartosz Majsak
     * who also solved my Arquillian remote server problem
     * @author Bartosz Majsak, Ken Fogel
     */
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("createbookstore.sql");

        try (Connection connection = dataSource.getConnection()) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }
    
    /**
     * Methods supporting the seedDatabse method
     * @author Bartosz Majsak, Ken Fogel
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }
    
    /**
     * Methods supporting the seedDatabse method
     * @author Bartosz Majsak, Ken Fogel
     */
    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }
    
    /**
     * Methods supporting the seedDatabse method
     * @author Bartosz Majsak, Ken Fogel
     */
    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
    
    /**
     * Used as a helper method to create a user with predefined 
     * values to be used for testing purposes. 
     * @return a user with predefined values
     * @author Jeffrey Boisvert
     */
    private Users createTestUser(){
        Users user = new Users();
        
        user.setAddress1("123 TestStreet");
        user.setAddress2("A");
        user.setCellPhone("1234567891");
        user.setCity("Testville");
        user.setCompanyName("Dawson College");
        user.setCountry("Canada");
        user.setEmail("test@test.com");
        user.setFirstName("Tester");
        user.setLastName("Testerson");
        user.setHomePhone("1234567891");
        user.setIsManager(false);
        user.setPassword("password");
        user.setPostalCode("J5L2C8");
        user.setProvince("QC");
        user.setTitle("Mr.");
        
        return user;
    }
    
}
