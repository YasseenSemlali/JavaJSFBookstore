package com.gb4w20.arquillian.test;

import com.mysql.cj.jdbc.MysqlDataSource;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;

/**
 * <h1>Selenium Test for the Front Page</h1>
 * <p>
 * To selenium test the front page components 
 * </p>
 * @author Yasseen, Jeffrey Boisvert, Jasmar Badion, Jean Robatto
 */
public class FrontPageIT extends TestBase{
    
    private final static Logger LOG = LoggerFactory.getLogger(FrontPageIT.class);

    private final static String FRONT_PAGE_TITLE = "Front page";
    
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
        
        //Attempt to set the timezone to utc - Jean 
        try {
            dataSource.setServerTimezone("UTC");
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(FrontPageIT.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
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
        driver.findElement(By.id("nav-form:french-button")).click();
        
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
        driver.findElement(By.id("nav-form:french-button")).click();
        driver.findElement(By.id("nav-form:english-button")).click();
        
        wait.until(ExpectedConditions.titleIs(FRONT_PAGE_TITLE));
        
    }
    
    /**
     * Used to test that the recently bought books is empty 
     * when the user has not logged in. 
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void emptyRecentlyBoughtBooksWhenNotLoggedIn() throws Exception {

        loadFrontPage();
                
        List<WebElement> elements = driver.findElements(By.id("bought-books-not-logged-in-msg"));
        
        assertEquals("Message that the user is not logged in is not shown", 1, elements.size());
        
    }
    
    /**
     * Used to test that the news feed is present on the page
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void newsFeedIsPresentTest() throws Exception {

        loadFrontPage();
                
        List<WebElement> elements = driver.findElements(By.id("news-feed"));
        
        assertEquals("The newsfeed is not present on the page", 1, elements.size());
        
    }
    
    /**
     * Used to test that the user can click on a news feed item
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void newsFeedItemClickTest() throws Exception {

        loadFrontPage();
                
        List<WebElement> elements = driver.findElements(By.className("newsfeed-item"));
        
        //Grab first newsfeed item
        WebElement newsFeedItem = elements.get(0); 
        
        String url = newsFeedItem.getAttribute("href");
        newsFeedItem.click();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe(url));   
                
    }
       
    /**
     * Helper method used to load the front page
     * @author Jeffrey Boisvert
     */
    private void loadFrontPage(){
        
        driver.get("http://localhost:8080/gb4w20");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.titleIs(FRONT_PAGE_TITLE));
        
    }
    
    /**
     * Helper method to login the user.
     * 
     * @author Jeffrey Boisvert
     */
    private void loginUser() throws InterruptedException {
        
        driver.get("http://localhost:8080/gb4w20/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement inputEmailElement = driver.findElement(By.id("login-form:email"));
        inputEmailElement.clear();
        inputEmailElement.sendKeys("cst.send@gmail.com");
        
        WebElement inputPasswordElement = driver.findElement(By.id("login-form:password"));
        inputPasswordElement.clear();
        inputPasswordElement.sendKeys("dawsoncollege");
        
        driver.findElement(By.id("login-form:login-btn")).click();
        
        //Give chance to login
        wait.withTimeout(Duration.ofSeconds(10));
        
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
        driver.findElement(By.id("nav-form:bookcartlink")).click();
        
        wait.until(ExpectedConditions.titleIs("Cart"));
    }
    
    /**
     * Selenium test for clicking the book if it
     * goes to the right page
     * @throws Exception 
     * @author Jasmar Badion
     */
    @Test
    public void testClickBook() throws Exception{
        loadFrontPage();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("booklinkpage")).click();
        
        wait.until(ExpectedConditions.urlMatches(".*bookpage.*"));
    }
    
    /**
     * Selenium test for clicking genre if it
     * goes to the right page
     * @throws Exception 
     * @author Jasmar Badion
     */
    @Test
    public void testClickGenre() throws Exception{
        loadFrontPage();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("genrelinkpage")).click();
        
        wait.until(ExpectedConditions.titleIs("Genre"));
    }
    
    /**
     * Used to test that correct elements are shown
     * when logged in as a user and they have recently bought books
     * @throws Exception 
     * @author Jeffrey Boisvert
     */
    @Test
    public void recentlyBoughtBooksWhenLoggedInTest() throws Exception {

        loginUser();
        loadFrontPage();
                
        List<WebElement> elements = driver.findElements(By.id("recently-bought-books"));
        
        assertEquals("Recently bought books not shown", 1, elements.size());
        
    }
    
    /**
     * Selenium test for clicking the first ad (top of the page)
     * and verifying it goes to the correct link.
     * 
     * @throws Exception 
     * @author Jean Robatto
     */
    @Test
    public void testClickAd1() throws Exception{
        loadFrontPage();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("banner_ad_1")).click();
        
        wait.until(ExpectedConditions.titleIs("Summer 2019 – Credit Programs"));
    }
    
    /**
     * Selenium test for clicking the second ad (bottom of the page)
     * and verifying it goes to the correct link.
     * 
     * @throws Exception 
     * @author Jean Robatto
     */
    @Test
    public void testClickAd2() throws Exception{
        loadFrontPage();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("banner_ad_2")).click();
        
        wait.until(ExpectedConditions.titleIs("Summer 2019 – Credit Programs"));
    }
    
    /**
     * Selenium test ensuring the recommended books cookie is empty
     * at first.
     * 
     * @throws Exception 
     * @author Jean Robatto
     */
    @Test
    public void testRecommendedBookCookieBefore() throws Exception{
        loadFrontPage();
        assertEquals("Genre cookie should not exist on first launch.", null, driver.manage().getCookieNamed("recentGenre"));
    }
    
    /**
     * Selenium test ensuring the recommended books cookie is 
     * correctly set.
     * 
     * @throws Exception 
     * @author Jean Robatto
     */
    @Test
    public void testRecommendedBookCookieAfter() throws Exception{
        loadFrontPage();
        
        driver.findElement(By.id("booklinkpage")).click();
        
        assertEquals("Invalid genre cookie value.", "1", driver.manage().getCookieNamed("recentGenre").getValue());
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
