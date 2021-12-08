package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_III_Explicit_Page_Ready {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {	
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 20);

		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Explicit() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceholder1_RadCalendar1")));
		
		Assert.assertEquals(driver.findElement(By.id("ctl00_ContentPlaceholder1_Label1")).getText(), "No Selected Dates to display.");
		
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='8']"))).click();
		
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id$='RadCalendar1']>.raDiv")));
		
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='8']")));
		
		Assert.assertEquals(driver.findElement(By.id("ctl00_ContentPlaceholder1_Label1")).getText(), "Wednesday, December 8, 2021");
	}
	
	@Test
	public void TC_02_Page_Ready() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		driver.findElement(By.cssSelector("input#txtUsername")).sendKeys("admin");
		driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("admin123");
		
		driver.findElement(By.cssSelector("input#btnLogin")).click();
		
		Assert.assertTrue(isJQueryLoadedSuccess(driver));
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='3 month(s)']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean isJQueryLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 20);
		jsExecutor = (JavascriptExecutor) driver;
		
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
		
		return explicitWait.until(jQueryLoad);
	}
	
	public Boolean isJQueryAndAjaxIconLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 20);
		jsExecutor = (JavascriptExecutor) driver;
		
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		
		ExpectedCondition<Boolean> ajaxIconLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return $('.raDiv').is(':visible')").toString().equals("false");
			}
		};
		
		return explicitWait.until(jQueryLoad) && explicitWait.until(ajaxIconLoad);
	}
	
	public Boolean isJQueryAndPageLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 20);
		jsExecutor = (JavascriptExecutor) driver;
		
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
}
