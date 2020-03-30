
package com.gb4w20.arquillian.test;

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
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 *
 * @author Yasseen
 */
public class FrontPageIT extends TestBase{
    
    private WebDriver driver;

    @Override
    protected DataSource getDatasource() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        System.out.println("asdf1");
    }

    
    @Test
    @Ignore
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
        //driver.quit();
    }
    
}
