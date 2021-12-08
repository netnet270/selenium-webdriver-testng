package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_II_FindElement_ImplicitWait {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {	
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 20);

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_FindElement() {
		driver.get("http://live.techpanda.org/");
		
		WebElement element = null;
		
		// 1. Found 0 matches => throws exception - Fail TestCase
		element = driver.findElement(By.name("slenium"));
		
		// 2. Found 1 matches
		element = driver.findElement(By.id("#search"));
		
		// 3. Found >= 2 matches => Thao tác với element tìm thấy đầu tiên
		element = driver.findElement(By.xpath("//a[@title='My Account']"));
	}
	
	@Test
	public void TC_01_FindElements() {
		driver.get("http://live.techpanda.org/");
		
		List<WebElement> list = null;

		// 1. Found 0 matches => Không đánh fail testcase, trả về empty list
		list = driver.findElements(By.name("slenium"));
		
		// 2. Found 1 matches => Trả về list có 1 element
		list = driver.findElements(By.id("#search"));
		
		// 3. Found >= 2 matches => Trả về list có all element
		list = driver.findElements(By.xpath("//a[@title='My Account']"));
	}

	@Test
	public void TC_02_ImplicitWait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.cssSelector("#start>button")).click();		
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));
		Assert.assertEquals(driver.findElement(By.cssSelector("#finish h4")).getText(), "Hello World!");
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
}
