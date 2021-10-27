package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_Command_II {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {	
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.techpanda.org/");
	}
	
	@Test
	public void TC_01_Verify_URL() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		
		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://live.techpanda.org/index.php/customer/account/login/");
		driver.findElement(By.xpath("//span[contains(text(),'Create an Account')]")).click();
		
		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.techpanda.org/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_Verify_Title() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		
		String loginPageTitle = driver.getTitle();
		Assert.assertEquals(loginPageTitle, "Customer Login");
		driver.findElement(By.xpath("//span[contains(text(),'Create an Account')]")).click();
		
		String registerPageTitle = driver.getTitle();
		Assert.assertEquals(registerPageTitle, "Create New Customer Account");
	}
	
	@Test
	public void TC_03_Navigation_Function() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Create an Account')]")).click();
		
		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.techpanda.org/index.php/customer/account/create/");
		
		driver.navigate().back();
		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://live.techpanda.org/index.php/customer/account/login/");
		
		driver.navigate().forward();
		String registerPageTitle = driver.getTitle();
		Assert.assertEquals(registerPageTitle, "Create New Customer Account");
	}

	@Test
	public void TC_04_Page_Source() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		
		String loginPageSource = driver.getPageSource();
		Assert.assertTrue(loginPageSource.contains("Login or Create an Account"));
		
		driver.findElement(By.xpath("//span[contains(text(),'Create an Account')]")).click();
		String registerPageSource = driver.getPageSource();
		Assert.assertTrue(registerPageSource.contains("Create an Account"));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
