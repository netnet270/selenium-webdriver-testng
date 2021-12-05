package webdriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Frame_IFrame {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		
		// blocked notification
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Iframe() {
		driver.get("https://kyna.vn/");

		Assert.assertTrue(driver.findElement(By.cssSelector(".fanpage iframe")).isDisplayed());
		driver.switchTo().frame(driver.findElement(By.cssSelector(".fanpage iframe")));
		Assert.assertEquals(
				driver.findElement(By.xpath("//a[@title='Kyna.vn']//parent::div//following-sibling::div")).getText(),
				"167K likes");

		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.cssSelector("#cs_chat_iframe")));
		driver.findElement(By.cssSelector(".meshim_widget_components_chatButton_Button")).click();
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("John Wick");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0985125666");
		Select select = new Select(driver.findElement(By.id("serviceSelect")));
		select.selectByVisibleText("TƯ VẤN TUYỂN SINH");

		driver.findElement(By.cssSelector("textarea.meshim_widget_widgets_TextArea")).sendKeys("Java Bootcamp");
		driver.findElement(By.cssSelector("input.submit")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("div.profile_field label.logged_in_name")).getText(),
				"John Wick");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.profile_field label.logged_in_phone")).getText(),
				"0985125666");
		Assert.assertEquals(driver.findElement(By.cssSelector("textarea.meshim_widget_widgets_TextArea")).getText(),
				"Java Bootcamp");

		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		driver.findElement(By.cssSelector("button.search-button")).click();

		List<WebElement> postTitles = driver.findElements(By.cssSelector("li.k-box-card h4"));
		for (WebElement postTitle : postTitles) {
			Assert.assertTrue(postTitle.getText().contains("Excel"));
		}
	}

//	@Test
	public void TC_02_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");

		driver.switchTo().frame(driver.findElement(By.cssSelector("frame[name='login_page']")));
		driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("123456");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector("input#fldPasswordDispId")).isDisplayed());
		driver.findElement(By.xpath("//div[@class='footer-btm']//a[text()='Terms and Conditions']")).click();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
