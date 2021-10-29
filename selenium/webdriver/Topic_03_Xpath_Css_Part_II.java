package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Xpath_Css_Part_II {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	String firstname, lastname, email, password, fullname;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		firstname = "John";
		lastname = "Smith";
		email = "john" + getRandomNumber() + "@gmail.com";
		password = "123456";
		fullname = firstname + " " + lastname;
	}
	
	@Test
	public void TC_01_Login_Empty_Data() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
			
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.cssSelector(".registered-users button[name='send']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
	}

	@Test
	public void TC_02_Login_Invalid_Email() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("12343432@1234.1234");
		driver.findElement(By.id("pass")).sendKeys("123456");
		driver.findElement(By.cssSelector(".registered-users button[name='send']")).click();
		
		String entryEmailText = driver.findElement(By.id("advice-validate-email-email")).getText();
		Assert.assertEquals(entryEmailText, "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Login_Password_Less_Than_6_Characters() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.cssSelector(".registered-users button[name='send']")).click();
		
		String entryPassText = driver.findElement(By.id("advice-validate-password-pass")).getText();

		Assert.assertEquals(entryPassText, "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	@Test
	public void TC_04_Login_Incorrect_Email_Or_Password() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123123123");
		driver.findElement(By.cssSelector(".registered-users button[name='send']")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
	}
	
	@Test
	public void TC_05_Create_New_Account() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Create an Account')]")).click();
		
		driver.findElement(By.id("firstname")).sendKeys(firstname);
		driver.findElement(By.id("lastname")).sendKeys(lastname);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		driver.findElement(By.xpath("//button[@title='Register']")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		
		// Cach 1
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/ancestor::div[@class='box']/"
				+ "div[@class='box-content']/p[contains(text(),'"+ fullname+"')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/ancestor::div[@class='box']/"
				+ "div[@class='box-content']/p[contains(., '"+ email +"')]")).isDisplayed());
		
		// Cach 2
		String contactInfomation = driver.findElement(By.xpath("//h3[text()='Contact Information']/ancestor::div[@class='box']/div[@class='box-content']/p")).getText();
		
		Assert.assertTrue(contactInfomation.contains(fullname));
		Assert.assertTrue(contactInfomation.contains(email));
		
		driver.findElement(By.xpath("//a[@class='skip-link skip-account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
		
		String loginPageTitle = driver.getTitle();
		Assert.assertEquals(loginPageTitle, "Magento Commerce");
	}
	
	@Test
	public void TC_06_Login_Valid_Data() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("pass")).sendKeys(password);
		driver.findElement(By.cssSelector(".registered-users button[name='send']")).click();
			
		String contactInfomation = driver.findElement(By.xpath("//h3[text()='Contact Information']/ancestor::div[@class='box']/div[@class='box-content']/p")).getText();
		
		Assert.assertTrue(contactInfomation.contains(fullname));
		Assert.assertTrue(contactInfomation.contains(email));
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
