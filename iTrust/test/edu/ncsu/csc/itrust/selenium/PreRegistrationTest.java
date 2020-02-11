package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PreRegistrationTest extends iTrustSeleniumTest {
	
    private WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new HtmlUnitDriver();
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testPreRegistration_Success() throws Exception {
		// start at homepage
		driver.get(iTrustSeleniumTest.ADDRESS);
		assertEquals(driver.getTitle(), "iTrust - Login");

		// go to pre-registration page
		driver.findElement(By.name("prereg")).click();
		assertEquals(driver.getTitle(), "iTrust - Pre-Register Patient");
		
		// enter required information
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("You have pre-registered successfully"));
		

	}

	public void testPreRegistration_MissingInfo() throws Exception {
		// start at homepage
		driver.get(iTrustSeleniumTest.ADDRESS);
		assertEquals(driver.getTitle(), "iTrust - Login");

		// go to pre-registration page
		driver.findElement(By.name("prereg")).click();
		assertEquals(driver.getTitle(), "iTrust - Pre-Register Patient");

		// missing name
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
		driver.findElement(By.name("tryAgain")).click();

		// missing email
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
		driver.findElement(By.name("tryAgain")).click();

		// missing password
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("confirmPassword")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
		driver.findElement(By.name("tryAgain")).click();

		// missing confirm password
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
	}

	public void testPreRegistration_PasswordMismatch() throws Exception {
		// start at homepage
		driver.get(iTrustSeleniumTest.ADDRESS);
		assertEquals(driver.getTitle(), "iTrust - Login");

		// go to pre-registration page
		driver.findElement(By.name("prereg")).click();
		assertEquals(driver.getTitle(), "iTrust - Pre-Register Patient");

		// password != confirmPassword
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).sendKeys("password2");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
		driver.findElement(By.name("tryAgain")).click();
	}

	public void testPreRegistration_InvalidPasswordFormat() throws Exception {
		// start at homepage
		driver.get(iTrustSeleniumTest.ADDRESS);
		assertEquals(driver.getTitle(), "iTrust - Login");

		// go to pre-registration page
		driver.findElement(By.name("prereg")).click();
		assertEquals(driver.getTitle(), "iTrust - Pre-Register Patient");

		// password too short
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("pass");
		driver.findElement(By.name("confirmPassword")).sendKeys("pass");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
		driver.findElement(By.name("tryAgain")).click();

		// password too long
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("passwordpasswordpassword");
		driver.findElement(By.name("confirmPassword")).sendKeys("passwordpasswordpassword");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
		driver.findElement(By.name("tryAgain")).click();

		// password contains invalid characters
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("password!@#$%");
		driver.findElement(By.name("confirmPassword")).sendKeys("password!@#$%");
		driver.findElement(By.name("confirmPassword")).submit();
		assertTrue(driver.getPageSource().contains("Information not valid"));
	}
}