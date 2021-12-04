package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// FirefoxDriver
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Fixed_Popup() {
		driver.get("https://ngoaingu24h.vn/");

		WebElement loginPopup = driver.findElement(By.cssSelector("div#modal-login-v1"));

		Assert.assertFalse(loginPopup.isDisplayed());

		driver.findElement(By.cssSelector("button.login_")).click();
		Assert.assertTrue(loginPopup.isDisplayed());

		driver.findElement(By.cssSelector("input#account-input")).sendKeys("automationfc");
		driver.findElement(By.cssSelector("input#password-input")).sendKeys("automationfc");
		driver.findElement(By.xpath("//button[@data-text='Đăng nhập']")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("#modal-login-v1 div.error-login-panel")).getText(),
				"Tài khoản không tồn tại!");
	}

	@Test
	public void TC_02_Radom_Popup_In_DOM_Blog() {
		driver.get("https://blog.testproject.io/");

		WebElement blogPopup = driver.findElement(By.cssSelector("div#mailch-bg"));

		// Step 2: Check popup có hiển thị hay không, nếu có close popup,
		// nếu không => chuyển step 3
		if (blogPopup.isDisplayed()) {
			System.out.println("-----Popup hien thi-----");
			driver.findElement(By.cssSelector("div#close-mailch")).click();
			Assert.assertFalse(blogPopup.isDisplayed());
		}

		// Step 3
		isPageLoadedSuccess(driver);
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("Selenium");
		driver.findElement(By.cssSelector("section#search-2 span.glass")).click();

		List<WebElement> postTitles = driver.findElements(By.cssSelector("h3.post-title a"));

		for (WebElement postTitle : postTitles) {
			Assert.assertTrue(postTitle.getText().contains("Selenium"));
			System.out.println(postTitle.getText());
		}
	}

	@Test
	public void TC_03_Radom_Popup_In_DOM_VNK() {
		driver.get("https://vnk.edu.vn/");

		WebElement popup = driver.findElement(By.cssSelector("#tve-p-scroller"));
		// Step 2: Check popup có hiển thị hay không, nếu có close popup,
		// nếu không => chuyển step 3
		if (popup.isDisplayed()) {
			driver.findElement(By.cssSelector("div.tve_empty_dropzone>div.thrv_icon")).click();
			Assert.assertFalse(popup.isDisplayed());
		}

		driver.findElement(By.xpath("//a[text()='Đội ngũ giảng viên nhiệt tình và thấu hiểu']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("div.title-content h1")).getText(), "Đội Ngũ Giảng Viên");
	}
	
	@Test
	public void TC_04_Radom_Popup_Not_In_DOM() {
		driver.get("https://dehieu.vn/");
				
		List<WebElement> popup = driver.findElements(By.cssSelector("section#popup"));
		
		if (popup.size() > 0) {
			driver.findElement(By.cssSelector("button#close-popup")).click();
		}
		driver.findElement(By.xpath("//h4[text()='Khóa học thiết kế hệ thống M&E - Tòa nhà']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("#banner h2.title")).getText(), "Khóa học thiết kế hệ thống M&E - Tòa nhà");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public boolean isPageLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 15);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jsQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				String state = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState;");
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0)");
			}
		};
		return explicitWait.until(jsQueryLoad);
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
