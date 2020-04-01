package com.gb4w20.arquillian.test;

import com.mysql.cj.jdbc.MysqlDataSource;
import io.github.bonigarcia.wdm.WebDriverManager;
import javax.sql.DataSource;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;

/**
 * <h1>Selenium Test for the Front Page</h1>
 * <p>
 * To selenium test the front page functionalities 
 * which are search, recently bought books, survey, logged in,
 * clicking book, view cart and clicking ads.
 * </p>
 * @author Yasseen, Jasmar Badion, Jeffrey Boisvert, Jean
 */
public class FrontPageIT extends TestBase{
    
    private final static Logger LOG = LoggerFactory.getLogger(FrontPageIT.class);
    
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

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @Test
    @Ignore
    public void testTitle() throws Exception {

        // And now use this to visit a web site
        driver.get("http://localhost:8080/gb4w20");

        // Wait for the page to load, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // Wait for the page to load, timeout after 10 seconds
        wait.until(ExpectedConditions.titleIs("Front page"));
    }

    @Test
    @Ignore
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
    
    /**
     * Used to test that the user is brought to the correct page upon 
     * clicking on search giving a valid input
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void testNavigateToSearchPage() throws Exception {

        loadFrontPage();
        
        WebElement inputElement = driver.findElement(By.id("search-input"));
        inputElement.clear();
        inputElement.sendKeys("harry");
        
        driver.findElement(By.id("search-submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Search"));
        
    }
    
    /**
     * Used to test that the user is brought to the advanced search
     * page when clicking on the link 
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void testNavigateToAdvanceSearchPage() throws Exception {

        loadFrontPage();
        
        driver.findElement(By.id("advanced-search-link")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Advanced search"));
        
    }

    /**
     * Used to test that the user can switch 
     * the french language on the front page 
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void testSwitchToFrench() throws Exception {

        loadFrontPage();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("j_idt10:french-button")).click();
        
        wait.until(ExpectedConditions.titleIs("Page de garde"));
        
    }
    
    /**
     * Used to test that the user can switch 
     * the English language on the front page 
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void testSwitchToEnglish() throws Exception {

        loadFrontPage();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("j_idt10:french-button")).click();
        driver.findElement(By.id("j_idt10:english-button")).click();
        
        wait.until(ExpectedConditions.titleIs("Front page"));
        
    }
    
    
    /**
     * Helper method used to load the front page
     * @author Jeffrey Boisvert
     */
    private void loadFrontPage(){
        
        driver.get("http://localhost:8080/gb4w20");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Front page"));
    }

    /**
     * Selenium test for viewing the cart page
     * @throws Exception 
     * @author Jasmar Badion
     */
    @Test
    public void testViewCart() throws Exception{
        loadFrontPage();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        driver.findElement(By.id("j_idt10:bookcartlink")).click();
        wait.until(ExpectedConditions.titleIs("Cart"));
    }
    
    /**
     * Used to close the browser 
     * after a test was conducted
     */
    @After
    public void shutdownTest() {
        driver.quit();
    }

}
