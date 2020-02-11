package edu.ncsu.csc.itrust.selenium;

import com.meterware.httpunit.HttpUnitOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;
import edu.ncsu.csc.itrust.enums.TransactionType;



public class PatientPharmacyTest extends iTrustSeleniumTest{
    public static final String ADDRESS = "http://localhost:8080/iTrust/";
    private WebDriver driver;

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	gen.clearAllTables();
	gen.standardData();
    }

    public void testInitialization() throws Exception {
	driver = login("9000000000", "pw");
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.findElement(By.linkText("Document Office Visit")).click();

	driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
        driver.findElement(By.xpath("//input[@value='2']")).submit();;
        driver.findElement(By.linkText("06/10/2007")).click();
        assertEquals("iTrust - Document Office Visit", driver.getTitle());
        
	assertTrue(driver.getPageSource().contains("Enter Pharmacy Here"));
	driver.quit();

        /*new Select(driver.findElement(By.id("medID"))).selectByVisibleText("009042407 - Tetracycline");
        driver.findElement(By.cssSelector("option[value=\"009042407\"]")).click();
        driver.findElement(By.id("dosage")).sendKeys("5");
        driver.findElement(By.id("startDate")).clear();
        driver.findElement(By.id("startDate")).sendKeys("10/01/2013");
        driver.findElement(By.id("endDate")).clear();
        driver.findElement(By.id("endDate")).sendKeys("10/12/2013");
        driver.findElement(By.id("instructions")).sendKeys("Take thrice daily");

	assertTrue(true);*/

    }

    public void testPharmacyUpdate() throws Exception {
	driver = login("9000000000", "pw");
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Document Office Visit")).click();

        driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
        driver.findElement(By.xpath("//input[@value='2']")).submit();;
        driver.findElement(By.linkText("06/10/2007")).click();

	assertTrue(driver.getPageSource().contains("Enter Pharmacy Here"));
 	
	new Select(driver.findElement(By.id("medID"))).selectByVisibleText("009042407 - Tetracycline");
        driver.findElement(By.cssSelector("option[value=\"009042407\"]")).click();
        driver.findElement(By.id("dosage")).sendKeys("5");
        driver.findElement(By.id("startDate")).clear();
        driver.findElement(By.id("startDate")).sendKeys("10/01/2013");
        driver.findElement(By.id("endDate")).clear();
        driver.findElement(By.id("endDate")).sendKeys("10/12/2013");
        driver.findElement(By.id("instructions")).sendKeys("Take thrice daily");
    	driver.findElement(By.name("pharmacy")).sendKeys("1302 W. Springfield Avenue, Urbana IL 61801");
	driver.findElement(By.id("addprescription")).submit();

	assertTrue(driver.getPageSource().contains("1302 W. Springfield Avenue, Urbana IL 61801"));
    }
}

