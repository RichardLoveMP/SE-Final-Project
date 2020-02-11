package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ActivatePreRegisteredPatientTest extends iTrustSeleniumTest {
	
    private WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new Driver();
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testActivation_Success() throws Exception {
		// login as HCP 1
		driver = login("9000000000", "pw");	

		// view the list of pre-registered patients
		driver.findElement(By.linkText("Pre-registered Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - View All Pre-registered Patients");

		// click on the pre-registered patient named "Pre Patient"
		driver.findElement(By.linkText("Pre Patient")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Personal Health Record");
		
		// activate the patient
		driver.findElement(By.name("activationbutton")).click();
		assertTrue(driver.getPageSource().contains("Patient has been activated."));
	}

	public void testDeactivation_Success() throws Exception {
		// login as HCP 1
		driver = login("9000000000", "pw");	

		// view the list of pre-registered patients
		driver.findElement(By.linkText("Pre-registered Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - View All Pre-registered Patients");
		
		// click on the pre-registered patient named "Pre Patient"
		driver.findElement(By.linkText("Pre Patient")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Personal Health Record");

		// deactivate the patient
		driver.findElement(By.name("deactivationbutton")).click();
		assertEquals(driver.getTitle(), "iTrust - View All Pre-registered Patients");
		assertFalse(driver.getPageSource().contains("Pre Patient")); // check if Pre Patient has been removed from the list
	}

	public void testEditPatient_Success() throws Exception {
		// login as HCP 1
		driver = login("9000000000", "pw");	

		// view the list of pre-registered patients
		driver.findElement(By.linkText("Pre-registered Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - View All Pre-registered Patients");
		
		// click on the pre-registered patient named "Pre Patient"
		driver.findElement(By.linkText("Pre Patient")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Personal Health Record");

		// edit the patient
		driver.findElement(By.name("editbutton")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");

		// modify the name
		driver.findElement(By.name("firstName")).sendKeys("New");
		driver.findElement(By.name("firstName")).submit();

		// check that the information has been updated
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		assertTrue(driver.getPageSource().contains("Information Successfully Updated")); 
	}
}