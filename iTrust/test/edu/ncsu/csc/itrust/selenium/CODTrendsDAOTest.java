package edu.ncsu.csc.itrust.selenium;

import edu.ncsu.csc.itrust.enums.TransactionType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The GUI test (Selenium) to test the GUI interface of Cause of Death trends.
 */
public class CODTrendsDAOTest extends iTrustSeleniumTest {

    /**
     * The "name" attribute of the text input box to let user input start year in the web page.
     */
    private static final String START = "StartingYear";
    /**
     * The "name" attribute of the text input box to let user input end year in the web page.
     */
    private static final String END = "EndingYear";
    /**
     * The "name" attribute of the submit button to let user submit their start year and end year.
     */
    private static final String SUBMIT = "submit";

    /**
     * WebDriver instance to simulate GUI web control
     */
    private WebDriver webDriver;

    /**
     * Necessary database rebase before the test started.
     * @throws Exception Any exception that may be thrown
     */
    protected void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
    }

    /**
     * The selenium test to test all the possible false input that a user may input
     * @throws Exception Any exception that may be thrown
     */
    public void testFalseInput() throws Exception {
        webDriver = login("9000000000", "pw");
        assertEquals("iTrust - HCP Home", webDriver.getTitle());
        assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

        webDriver.findElement(By.linkText("View Cause of Death Trends")).click();
        assertEquals("iTrust - View Cause of Death Trends", webDriver.getTitle());

        webDriver.findElement(By.name(SUBMIT)).click();
        assertTrue(webDriver.getPageSource().contains("Your input is not a valid number!"));

        webDriver.findElement(By.name(START)).sendKeys("1970");
        webDriver.findElement(By.name(SUBMIT)).click();
        assertTrue(webDriver.getPageSource().contains("Your input is not a valid number!"));

        webDriver.findElement(By.name(START)).sendKeys("2010");
        webDriver.findElement(By.name(END)).sendKeys("1980");
        webDriver.findElement(By.name(SUBMIT)).click();
        assertTrue(webDriver.getPageSource().contains("Your start year is bigger than end year!"));

        webDriver.findElement(By.name(START)).sendKeys("1960");
        webDriver.findElement(By.name(END)).sendKeys("1980");
        webDriver.findElement(By.name(SUBMIT)).click();
        assertTrue(webDriver.getPageSource().contains("Start year should not be lower than 1970!"));

        webDriver.findElement(By.name(START)).sendKeys("1970");
        webDriver.findElement(By.name(END)).sendKeys("1960");
        webDriver.findElement(By.name(SUBMIT)).click();
        assertTrue(webDriver.getPageSource().contains("End year should not be lower than 1970!"));

        webDriver.quit();
    }

    /**
     * The selenium test to test true input
     * @throws Exception Any exception that may be thrown
     */
    public void testTrueInput() throws Exception {
        webDriver = login("9000000000", "pw");
        assertEquals("iTrust - HCP Home", webDriver.getTitle());
        assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

        webDriver.findElement(By.linkText("View Cause of Death Trends")).click();
        assertEquals("iTrust - View Cause of Death Trends", webDriver.getTitle());

        webDriver.findElement(By.name(START)).sendKeys("1970");
        webDriver.findElement(By.name(END)).sendKeys("2019");
        webDriver.findElement(By.name(SUBMIT)).click();
        assertTrue(webDriver.getPageSource().contains("1. Name: Diabetes with ketoacidosis, Code: 250.10, Number of Deaths: 1"));

        webDriver.quit();
    }
}
