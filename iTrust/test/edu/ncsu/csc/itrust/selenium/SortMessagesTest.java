package edu.ncsu.csc.itrust.selenium;

import com.meterware.httpunit.HttpUnitOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SortMessagesTest extends iTrustSeleniumTest{

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

    public void testAscendingNameSort() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        Select sortBy = new Select(driver.findElement(By.name("sortBy")));
        sortBy.selectByVisibleText("Sender/Recipient");

        Select orderBy = new Select(driver.findElement(By.name("orderOf")));
        orderBy.selectByVisibleText("Ascending");

        driver.findElement(By.name("submit")).submit();
        WebElement firstEmailSender = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[1]"));
        assertEquals("Random Person", firstEmailSender.getText());
        WebElement firstEmailSubject = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[2]"));
        assertEquals("Old Medicine", firstEmailSubject.getText());
        WebElement firstEmailTime = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[3]"));
        assertEquals("2009-12-02 11:15", firstEmailTime.getText());
    }

    public void testDescendingNameSort() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        Select sortBy = new Select(driver.findElement(By.name("sortBy")));
        sortBy.selectByVisibleText("Sender/Recipient");

        Select orderBy = new Select(driver.findElement(By.name("orderOf")));
        orderBy.selectByVisibleText("Descending");

        driver.findElement(By.name("submit")).submit();
        WebElement firstEmailSender = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[1]"));
        assertEquals("Baby Programmer", firstEmailSender.getText());
        WebElement firstEmailSubject = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[2]"));
        assertEquals("Remote Monitoring Question", firstEmailSubject.getText());
        WebElement firstEmailTime = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[3]"));
        assertEquals("2010-01-07 09:15", firstEmailTime.getText());
    }

    public void testAscendingTimeSort() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        Select sortBy = new Select(driver.findElement(By.name("sortBy")));
        sortBy.selectByVisibleText("Timestamp");

        Select orderBy = new Select(driver.findElement(By.name("orderOf")));
        orderBy.selectByVisibleText("Ascending");

        driver.findElement(By.name("submit")).submit();
        WebElement firstEmailSender = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[1]"));
        assertEquals("Random Person", firstEmailSender.getText());
        WebElement firstEmailSubject = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[2]"));
        assertEquals("Old Medicine", firstEmailSubject.getText());
        WebElement firstEmailTime = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[3]"));
        assertEquals("2009-12-02 11:15", firstEmailTime.getText());
    }

    public void testDescendingTimeSort() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        Select sortBy = new Select(driver.findElement(By.name("sortBy")));
        sortBy.selectByVisibleText("Timestamp");

        Select orderBy = new Select(driver.findElement(By.name("orderOf")));
        orderBy.selectByVisibleText("Descending");

        driver.findElement(By.name("submit")).submit();
        WebElement firstEmailSender = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[1]"));
        assertEquals("Andy Programmer", firstEmailSender.getText());
        WebElement firstEmailSubject = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[2]"));
        assertEquals("Scratchy Throat", firstEmailSubject.getText());
        WebElement firstEmailTime = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[3]"));
        assertEquals("2010-02-02 13:03", firstEmailTime.getText());
    }

    public void testResetSort() throws Exception {
        driver = login("9000000000", "pw");
        driver.findElement(By.linkText("Message Inbox")).click();

        Select sortBy = new Select(driver.findElement(By.name("sortBy")));
        sortBy.selectByVisibleText("Timestamp");

        Select orderBy = new Select(driver.findElement(By.name("orderOf")));
        orderBy.selectByVisibleText("Ascending");
        driver.findElement(By.name("submit")).submit();

        WebElement sortedTime = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[3]"));
        assertEquals("2009-12-02 11:15", sortedTime.getText());
        driver.findElement(By.linkText("Message Inbox")).click();

        WebElement defaultTime = driver.findElement(By.xpath("//table[@id=\"mailbox\"]/tbody/tr[1]/td[3]"));
        assertEquals("2010-02-02 13:03", defaultTime.getText());
    }
}
