package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.gb4w20.backingbeans.UserSessionBean;
import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Books_;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.GenresJpaController;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import com.gb4w20.gb4w20.querybeans.NameTotalAndCountBean;
import com.mysql.cj.jdbc.MysqlDataSource;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import junit.framework.Assert;
import org.apache.commons.lang3.tuple.Pair;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Yasseen
 */
public class FrontPageIT extends TestBase {

    private WebDriver driver;

    @Override
    protected DataSource getDatasource() {
        MysqlDataSource dataSource = new MysqlDataSource();

        // Set dataSource Properties
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(3306);
        dataSource.setDatabaseName("gb4w20test");
        dataSource.setUser("gb4w20");
        dataSource.setPassword("pencil3tuna");
        return dataSource;
    }
    
    @BeforeClass
    public static void setupClass() {
        // Normally an executable that matches the browser you are using must
        // be in the classpath. The webdrivermanager library by Boni Garcia
        // downloads the required driver and makes it available
        WebDriverManager.chromedriver().setup();
    }

    private void login(String username, String password) {
        driver.get("http://localhost:8080/gb4w20/login.xhtml");
        driver.findElement(By.id("login-form:email")).sendKeys(username);
        driver.findElement(By.id("login-form:password")).sendKeys(password);
        driver.findElement(By.id("login-form:login-btn")).click();
        
    }
    
    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @Test
    @Ignore
    public void testLoginRedirectNotManager() throws Exception {
        this.login("cst.send@gmail.com", "dawsoncollege");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Front page"));
    }
    
    @Test
    public void testLoginRedirectIsManager() throws Exception {
        this.login("cst.receive@gmail.com", "collegedawson");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Dashboard"));
    }
    
    @Test
    public void testRecentlyBought() throws Exception {
        this.login("cst.send@gmail.com", "dawsoncollege");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Front page"));
        
        List<WebElement> books = driver.findElement(By.id("recently-bought")).findElements(By.tagName("strong"));
        
        List<String> results = new ArrayList<String>();
        for(WebElement i: books) {
            results.add(i.getText());
        }
        
        assertEquals(Arrays.asList("The Three-Body Problem", "Red Seas Under Red Skies", "Harry Potter and the Chamber of Secrets"), results);
    }
    
    @Test
    public void testTitle() throws Exception {

        // And now use this to visit a web site
        driver.get("http://localhost:8080/gb4w20");

        // Wait for the page to load, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // Wait for the page to load, timeout after 10 seconds
        wait.until(ExpectedConditions.titleIs("Front page"));
    }

    @Test
    public void testSurvey() throws Exception {

        // And now use this to visit a web site
        driver.get("http://localhost:8080/gb4w20");

        // Wait for the page to load, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // Wait for the page to load, timeout after 10 seconds
        wait.until(ExpectedConditions.titleIs("Front page"));

        driver.findElement(By.cssSelector("label[for='surveyform\\:survey-answers\\:1']")).click();
        driver.findElement(By.id("surveyform:surveysubmit")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("survey-chart")));
        
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.cssSelector(".highcharts-series > rect:nth-child(2)"))).build().perform();        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("survey-chart")));        
        String greenCount = driver.findElement(By.cssSelector(".highcharts-label > text > tspan:nth-child(4)")).getText();
        
        assertEquals("501", greenCount);
    }

    @Test
    public void testLoginFormFill() throws Exception {

        // And now use this to visit a web site
        driver.get("http://localhost:8080/JSFSample30FilterSelenium");

        // Wait for the page to load, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // Check the title of the page
        wait.until(ExpectedConditions.titleIs("Login form"));

        // Find username input field
        WebElement inputElement = driver.findElement(By.id("login-form:username"));
        // Clear out anything currently in the field
        inputElement.clear();
        // Enter text into the input field
        inputElement.sendKeys("ken");

        // Find password input field
        inputElement = driver.findElement(By.id("login-form:password"));
        // Clear out anything currently in the field
        inputElement.clear();
        // Enter text into the input field
        inputElement.sendKeys("moose");

        // Click the submit button
        driver.findElement(By.id("login-form:button")).click();

        // If username and password are correct then a page with this title 
        // should be loaded
        wait.until(ExpectedConditions.titleIs("Secured Selenium Welcome page"));
    }

    @After
    public void shutdownTest() {
        //Close the browser
        driver.quit();
    }

}
