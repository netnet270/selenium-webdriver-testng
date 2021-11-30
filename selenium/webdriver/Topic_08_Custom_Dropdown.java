package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		// wait để apply cho các trạng thái của element (visible/ invisible/ presence/
		// clickable/ ...)
		explicitWait = new WebDriverWait(driver, 15);

		// Ép kiểu tường minh (Reference casting)
		jsExecutor = (JavascriptExecutor) driver;

		// Wait để tìm element (findElement/ findElements)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

		selectItemInDropdown(By.id("number-button"), By.cssSelector("#number-menu>li.ui-menu-item>div"), "10" );
		slepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("#number-button .ui-selectmenu-text")).getText(), "10");
		
		selectItemInDropdown(By.id("number-button"), By.cssSelector("#number-menu>li.ui-menu-item>div"), "5" );
		slepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("#number-button .ui-selectmenu-text")).getText(), "5");
	}

	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInDropdown(By.cssSelector("div[role='listbox']"), By.cssSelector("div[role='listbox'] .item span"), "Jenny Hess" );
		slepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div[aria-live='polite']")).getText(), "Jenny Hess");
		
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInDropdown(By.cssSelector("div[role='listbox']"), By.cssSelector("div[role='listbox'] .item span"), "Stevie Feliciano" );
		slepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div[aria-live='polite']")).getText(), "Stevie Feliciano");
	}

	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInDropdown(By.cssSelector(".btn-group li.dropdown-toggle"), By.cssSelector("ul.dropdown-menu li a"), "First Option" );
		slepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector(".btn-group li.dropdown-toggle")).getText(), "First Option");
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

	public void selectItemInDropdown(By parentBy, By childBy, String expectedSelectItem) {
		// 1. Click vào 1 thẻ bất kì để xổ ra hết tất cả các item trong dropdown ra
		driver.findElement(parentBy).click();

		// 2. Chờ cho tất cả item được có trong DOM
		explicitWait.until(
				ExpectedConditions.presenceOfAllElementsLocatedBy(childBy));

		// 3. Lấy hết tất cả các item này đưa vào 1 List Element
		List<WebElement> allItems = driver.findElements(childBy);

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedSelectItem)) {
				if (item.isDisplayed()) {
					item.click();
				} else {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
					item.click();
				}
				break;
			}
		}

	}
}
