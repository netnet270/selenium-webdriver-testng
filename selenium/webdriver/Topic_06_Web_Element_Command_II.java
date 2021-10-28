package webdriver;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_Command_II {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	By emailTextboxBy = By.id("mail");
	By under18RadioBy = By.id("under_18");
	By eduTextareaBy = By.id("edu");
	By jobRole1DropdownBy = By.id("job1");
	By jobRole2DropdownBy = By.id("job2");
	By devRadioBy = By.id("development");
	By slider1By = By.id("slider-1");
	
	By passBy = By.id("password");
	By radioDisableBy = By.id("radio-disabled");
	By bioTextareaBy = By.id("bio");
	By jobRole3DropdownBy = By.id("job3");
	By checkboxDisableBy = By.id("check-disbaled");
	By slider2By = By.id("slider-2");
	By javaCheckboxBy = By.id("java");

	@BeforeClass
	public void beforeClass() {	
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://automationfc.github.io/basic-form/index.html");
	}
	
	@Test
	public void TC_01_Is_Displayed() {
		if(isDisplayed(emailTextboxBy)){
			sendToElement(emailTextboxBy, "Automation Testing");
		}
	
		if(isDisplayed(under18RadioBy)){
			clickToElement(under18RadioBy);
		}
	
		if(isDisplayed(eduTextareaBy)){
			sendToElement(eduTextareaBy, "Automation Testing");
		}
	}
	
	@Test
	public void TC_02_Is_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		Assert.assertTrue(isEnabled(emailTextboxBy));
		Assert.assertTrue(isEnabled(under18RadioBy));
		Assert.assertTrue(isEnabled(eduTextareaBy));
		Assert.assertTrue(isEnabled(jobRole1DropdownBy));
		Assert.assertTrue(isEnabled(jobRole2DropdownBy));
		Assert.assertTrue(isEnabled(devRadioBy));
		Assert.assertTrue(isEnabled(slider1By));
		
		Assert.assertFalse(isEnabled(passBy));
		Assert.assertFalse(isEnabled(radioDisableBy));
		Assert.assertFalse(isEnabled(bioTextareaBy));
		Assert.assertFalse(isEnabled(jobRole3DropdownBy));
		Assert.assertFalse(isEnabled(checkboxDisableBy));
		Assert.assertFalse(isEnabled(slider2By));
	}

	@Test
	public void TC_03_Is_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		clickToElement(under18RadioBy);
		clickToElement(javaCheckboxBy);
		
		Assert.assertTrue(isSelected(under18RadioBy));
		Assert.assertTrue(isSelected(javaCheckboxBy));
		
		clickToElement(javaCheckboxBy);
		Assert.assertFalse(isSelected(javaCheckboxBy));
	}
	
	public boolean isDisplayed(By by) {
		WebElement element = driver.findElement(by);
		
		if (element.isDisplayed()) {
			System.out.println("Element " + by + " is displayed");
			return true;
		} else {
			System.out.println("Element " + by + " is not displayed");
			return false;
		}
	}

	public boolean isEnabled(By by) {
		WebElement element = driver.findElement(by);
		
		if (element.isEnabled()) {
			System.out.println("Element " + by + " is enabled");
			return true;
		} else {
			System.out.println("Element " + by + " is disabled");
			return false;
		}
	}
	
	public boolean isSelected(By by) {
		WebElement element = driver.findElement(by);
		
		if (element.isSelected()) {
			System.out.println("Element " + by + " is selected");
			return true;
		} else {
			System.out.println("Element " + by + " is de-selected");
			return false;
		}
	}
	
	public void sendToElement(By by, String string) {
		WebElement element = driver.findElement(by);
		element.sendKeys(string);
	}
	
	public void clickToElement(By by) {
		WebElement element = driver.findElement(by);
		element.click();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
