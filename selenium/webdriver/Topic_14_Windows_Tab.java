package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Windows_Tab {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// FirefoxDriver
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Tabs_AutomationFC() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		String parentPageID = driver.getWindowHandle();

		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		switchToWindowByTitle("Google");

		Assert.assertEquals(driver.getTitle(), "Google");
		driver.switchTo().window(parentPageID);

		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		switchToWindowByTitle("Facebook – log in or sign up");

		Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
		driver.switchTo().window(parentPageID);

		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

		Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

		Set<String> allPageID = driver.getWindowHandles();

		for (String pageID : allPageID) {
			System.out.println(pageID);
			if (!pageID.equals(parentPageID)) {
				driver.switchTo().window(pageID);
				driver.close();
			}
		}
		driver.switchTo().window(parentPageID);
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
	}

	@Test
	public void TC_02_Tabs_Kyna() {
		driver.get("https://kyna.vn/");

		String parentPageID = driver.getWindowHandle();

		jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("//div[@id='k-footer']//a[contains(@href, 'facebook')]")));
		driver.findElement(By.xpath("//div[@id='k-footer']//a[contains(@href, 'facebook')]")).click();
		switchToWindowByTitle("Kyna.vn - Home | Facebook");
		sleepInSecond(3);

		Assert.assertEquals(driver.getTitle(), "Kyna.vn - Home | Facebook");
		driver.switchTo().window(parentPageID);
		sleepInSecond(5);

		driver.findElement(By.xpath("//div[@id='k-footer']//a[contains(@href, 'youtube')]")).click();
		switchToWindowByTitle("Kyna.vn - YouTube");

		Assert.assertEquals(driver.getTitle(), "Kyna.vn - YouTube");
		driver.switchTo().window(parentPageID);

		Set<String> allPageID = driver.getWindowHandles();

		for (String pageID : allPageID) {
			if (!pageID.equals(parentPageID)) {
				driver.switchTo().window(pageID);
				driver.close();
			}
		}
	}
	
	@Test
	public void TC_03_Windows_Cambride() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		String parentPageID = driver.getWindowHandle();
		
		driver.findElement(By.xpath("//a[text()='Sony Xperia']//parent::h2[@class='product-name']//following-sibling::div[@class='actions']"
				+ "//a[@class='link-compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']//parent::h2[@class='product-name']//following-sibling::div[@class='actions']"
				+ "//a[@class='link-compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The product Samsung Galaxy has been added to comparison list.");

		driver.findElement(By.xpath("//button[@title='Compare']//span[text()='Compare']")).click();
		switchToWindowById(parentPageID);
		sleepInSecond(3);
		
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		driver.close();
		driver.switchTo().window(parentPageID);
		
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		driver.switchTo().alert().accept();
		sleepInSecond(3);

		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The comparison list was cleared.");
	}

	@Test
	public void TC_04_Windows_Cambride() {
		driver.get("https://dictionary.cambridge.org/vi/");

		String parentPageID = driver.getWindowHandle();

		driver.findElement(By.xpath("//span[text()='Đăng nhập']")).click();
		switchToWindowByTitle("Login");

		driver.findElement(By.cssSelector("#gigya-login-form input.gigya-input-submit")).click();
		Assert.assertEquals(driver.findElement(By.xpath(
				"//form[@id='gigya-login-form']//input[@name='username']//following-sibling::span[contains(@class, 'gigya-error-msg')]"))
				.getText(), "This field is required");
		Assert.assertEquals(driver.findElement(By.xpath(
				"//form[@id='gigya-login-form']//input[@name='password']//following-sibling::span[contains(@class, 'gigya-error-msg')]"))
				.getText(), "This field is required");

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void switchToWindowById(String parentPageID) {
		Set<String> allPageID = driver.getWindowHandles();

		for (String pageID : allPageID) {
			if (!pageID.equals(parentPageID)) {
				driver.switchTo().window(pageID);
			}
		}
	}

	public void switchToWindowByTitle(String expectedTitle) {
		Set<String> allPageID = driver.getWindowHandles();

		for (String pageID : allPageID) {
			driver.switchTo().window(pageID);
			sleepInSecond(3);

			if (driver.getTitle().contains(expectedTitle)) {
				break;
			}
		}
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
