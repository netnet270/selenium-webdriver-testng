package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Default_Dropdown {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {	
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Rode() {
		driver.get("https://www.rode.com/wheretobuy");
		Select select = new Select(driver.findElement(By.id("where_country")));
		
		Assert.assertFalse(select.isMultiple());
		select.selectByVisibleText("Vietnam");
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");
		driver.findElement(By.id("search_loc_submit")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='result_count']/span[text()='31']")).isDisplayed());
		
		List<WebElement> listStoreName = driver.findElements(By.xpath("//div[@id='search_results']//div[@class='store_name']"));

		for(WebElement store: listStoreName) {
			System.out.println(store);
		}
	}

	@Test
	public void TC_02_NopCommerce() {
		driver.get("https://demo.nopcommerce.com/");
		driver.findElement(By.cssSelector("a.ico-register")).click();
		
		By firstNameBy = By.id("FirstName");
		By lastNameBy = By.id("LastName");
		By dayDropdownBy = By.name("DateOfBirthDay");
		By monthDropdownBy = By.name("DateOfBirthMonth");
		By yearDropdownBy = By.name("DateOfBirthYear");
		By emailBy = By.id("Email");
		By companyBy = By.id("Company");
		By passwordBy = By.id("Password");
		By confirmPasswordBy = By.id("ConfirmPassword");
		
		// data test
		String firstName = "Automation";
		String lastName = "FC";
		String dayDropdown = "1";
		String monthDropdown = "May";
		String yearDropdown = "1980";
		String email = "automation" + getRandomNumber() + "@gmail.com" ;
		String company = "Automation VN";
		String password = "123456";
		
		Select select;
		
		driver.findElement(By.id("gender-male")).click();
		driver.findElement(firstNameBy).sendKeys(firstName);
		driver.findElement(lastNameBy).sendKeys(lastName);
		
		select = new Select(driver.findElement(dayDropdownBy));
		select.selectByVisibleText(dayDropdown);
		Assert.assertEquals(select.getOptions().size(), 32);
	
		select = new Select(driver.findElement(monthDropdownBy));
		select.selectByVisibleText(monthDropdown);
		Assert.assertEquals(select.getOptions().size(), 13);
	
		select = new Select(driver.findElement(yearDropdownBy));
		select.selectByVisibleText(yearDropdown);
		Assert.assertEquals(select.getOptions().size(), 112);
		
		driver.findElement(emailBy).sendKeys(email);
		driver.findElement(companyBy).sendKeys(company);
		driver.findElement(passwordBy).sendKeys(password);
		driver.findElement(confirmPasswordBy).sendKeys(password);
		
		driver.findElement(By.id("register-button")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='result' and text()='Your registration completed']")).isDisplayed());
		
		driver.findElement(By.cssSelector("a.ico-account")).click();
		
		Assert.assertEquals(driver.findElement(firstNameBy).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(lastNameBy).getAttribute("value"), lastName);
		
		select = new Select(driver.findElement(dayDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), dayDropdown);
		
		select = new Select(driver.findElement(monthDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), monthDropdown);
		
		select = new Select(driver.findElement(yearDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), yearDropdown);
		
		Assert.assertEquals(driver.findElement(emailBy).getAttribute("value"), email);
		Assert.assertEquals(driver.findElement(companyBy).getAttribute("value"), company);
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

	public int getRandomNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
}
