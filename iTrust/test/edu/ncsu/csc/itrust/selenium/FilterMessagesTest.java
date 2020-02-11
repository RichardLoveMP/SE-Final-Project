package edu.ncsu.csc.itrust.selenium;

import com.meterware.httpunit.HttpUnitOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FilterMessagesTest extends iTrustSeleniumTest{
    /*
     * The URL for iTrust, change as needed
     */
    /**ADDRESS*/
    public static final String ADDRESS = "http://localhost:8080/iTrust/";
    private WebDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
        HttpUnitOptions.setScriptingEnabled(false);
        // turn off htmlunit warnings
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
    }

    public void testInboxOnly() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Outbox")).click();
        assertFalse(driver.getPageSource().contains("Filter by sender"));
        assertFalse(driver.getPageSource().contains("or subject"));
        assertFalse(driver.getPageSource().contains("Include words"));
        assertFalse(driver.getPageSource().contains("Exclude words"));
        assertFalse(driver.getPageSource().contains("From the date"));
        assertFalse(driver.getPageSource().contains("Till the date"));

    }

    public void testTestFilter() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        driver.findElement(By.name("filterSender")).sendKeys("Random Person");
        driver.findElement(By.name("filterHasTheWords")).sendKeys("Lab");
        driver.findElement(By.name("filterHasNotTheWords")).sendKeys("Procedure");
        driver.findElement(By.name("Test Search")).submit();
        assertTrue(driver.getPageSource().contains("Lab Results"));
        assertFalse(driver.getPageSource().contains("RE: Lab Procedure"));
    }

    public void testCancelFilter() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        driver.findElement(By.name("filterSender")).sendKeys("DOESN'T EXIST");
        driver.findElement(By.name("Test Search")).submit();
        assertTrue(driver.getPageSource().contains("You have no messages"));

        driver.findElement(By.name("Cancel")).submit();
        assertFalse(driver.getPageSource().contains("You have no messages"));
    }

    public void testSaveFilter() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        driver.findElement(By.name("filterSender")).sendKeys("Andy Programmer");
        driver.findElement(By.name("Save")).submit();
        assertFalse(driver.getPageSource().contains("Random Person"));

        driver.findElement(By.linkText("Message Outbox")).click();
        driver.findElement(By.linkText("Message Inbox")).click();
        assertFalse(driver.getPageSource().contains("Random Person"));
    }
}