package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Locator {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
	}
	
	@Test
	public void TC_01_Id() {
		driver.findElement(By.id("email")).sendKeys("abc@gmail.com");
		slepInSecond(3);
	}

	@Test
	public void TC_02_Class() {
		driver.findElement(By.className("fb_content")).isDisplayed();
		slepInSecond(3);
	}

	@Test
	public void TC_03_Name() {
		driver.findElement(By.name("pass")).sendKeys("Aa@123456");
		slepInSecond(3);
	}
	
	@Test
	public void TC_04_linkText() {
		driver.findElement(By.linkText("Forgotten password?")).click();
		slepInSecond(3);
	}
	
	@Test
	public void TC_05_partialLinkText() {
		driver.findElement(By.partialLinkText("Sign")).click();
		slepInSecond(3);
	}
	
	@Test
	public void TC_06_cssSelector() {
		driver.findElement(By.cssSelector("input[id='password_step_input']")).sendKeys("Aa@123456");
		slepInSecond(3);
	}
	
	@Test
	public void TC_07_xpath() {
		driver.findElement(By.xpath("//button[@name='websubmit']")).click();
		slepInSecond(3);
	}
	
	@Test
	public void TC_08_Name() {
		boolean inputSize = driver.findElement(By.tagName("input")).equals("ahihi");
		System.out.print(inputSize);
		slepInSecond(3);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void slepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
