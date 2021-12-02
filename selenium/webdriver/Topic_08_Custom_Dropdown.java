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
//		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
//		driver = new ChromeDriver();
		
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

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
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("#number-button .ui-selectmenu-text")).getText(), "10");
		
		selectItemInDropdown(By.id("number-button"), By.cssSelector("#number-menu>li.ui-menu-item>div"), "5" );
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("#number-button .ui-selectmenu-text")).getText(), "5");
	}

	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInDropdown(By.cssSelector("div[role='listbox']"), By.cssSelector("div[role='listbox'] .item span"), "Jenny Hess" );
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div[aria-live='polite']")).getText(), "Jenny Hess");
		
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInDropdown(By.cssSelector("div[role='listbox']"), By.cssSelector("div[role='listbox'] .item span"), "Stevie Feliciano" );
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div[aria-live='polite']")).getText(), "Stevie Feliciano");
	}

	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInDropdown(By.cssSelector(".btn-group li.dropdown-toggle"), By.cssSelector("ul.dropdown-menu li a"), "First Option" );
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector(".btn-group li.dropdown-toggle")).getText(), "First Option");
	}
	
	@Test
	public void TC_04_Angular() {
		driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");

		selectItemInDropdown(By.cssSelector("span[aria-owns='games_options']"), By.cssSelector("ul#games_options>li"), "Basketball");
		sleepInSecond(1);
		
		// Verify on Chorme
//		Assert.assertEquals(driver.findElement(By.cssSelector("span[aria-owns='games_options']>input")).getAttribute("aria-label"), "Basketball");
	
		// Verify on Firefox
		Assert.assertEquals(getHiddenText("#games_hidden + input.e-input"), "Basketball");

		selectItemInDropdown(By.cssSelector("span[aria-owns='games_options']"), By.cssSelector("ul#games_options>li"), "Football");
		sleepInSecond(1);

		// Verify on Chorme
//		Assert.assertEquals(driver.findElement(By.cssSelector("span[aria-owns='games_options']>input")).getAttribute("aria-label"), "Football");

		// Verify on Firefox
		Assert.assertEquals(getHiddenText("#games_hidden + input.e-input"), "Football");
	}
	
	public void TC_05_JQuery_Editable() {
		driver.get("http://indrimuska.github.io/jquery-editable-select/");
		By inputBy = By.cssSelector("#default-place>input");

		selectItemInEditableDropdown(inputBy, By.cssSelector("#default-place>ul.es-list>li"), "Mercedes");
		sleepInSecond(1);
		Assert.assertEquals(getHiddenText("#default-place>input"), "Mercedes");
		
		selectItemInEditableDropdown(inputBy, By.cssSelector("#default-place>ul.es-list>li"), "Ford");
		sleepInSecond(1);
		Assert.assertEquals(getHiddenText("#default-place>input"), "Ford");
	}

	public void TC_06_React_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		By inputBy = By.cssSelector("input.search");
		
		selectItemInEditableDropdown(inputBy, By.cssSelector("div[role='option']>span"), "Albania");
		sleepInSecond(1);
		Assert.assertEquals(driver.findElement(By.xpath("//input[@class='search']//following-sibling::div[@role='alert']")).getText(), "Albania");
		
		selectItemInEditableDropdown(inputBy, By.cssSelector("div[role='option']>span"), "Australia");
		sleepInSecond(1);
		Assert.assertEquals(driver.findElement(By.xpath("//input[@class='search']//following-sibling::div[@role='alert']")).getText(), "Australia");
	}

	public void TC_07_Multiple_Select() {
		driver.get("https://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");

		By parentBy = By.xpath("(//button[@class='ms-choice'])[1]");
		By childBy = By.xpath("(//button[@class='ms-choice'])[1]//following-sibling::div//ul//li//span");
		String option1[] = {"March", "July", "November"};
		String option2[] = {"March", "July", "November", "September"};
		String option3[] = {"[Select all]"};
	
		selectMultiItemInDropdown(parentBy, childBy, option1);
		sleepInSecond(1);
		Assert.assertTrue(areItemSelected(option1));
		
		driver.navigate().refresh();
		selectMultiItemInDropdown(parentBy, childBy, option2);
		sleepInSecond(1);
		Assert.assertTrue(areItemSelected(option2));
		
		driver.navigate().refresh();
		selectMultiItemInDropdown(parentBy, childBy, option3);
		sleepInSecond(1);
		Assert.assertTrue(areItemSelected(option3));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String getHiddenText(String cssSelector) {
		return (String)jsExecutor.executeScript("return document.querySelector(\""+ cssSelector +"\").value;");
	}

	public void sleepInSecond(long second) {
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

	public void selectItemInEditableDropdown(By parentBy, By childBy, String expectedSelectItem) {
		// 0. clear data before senkeys text
		driver.findElement(parentBy).clear();
	
		// 1. set Text
		driver.findElement(parentBy).sendKeys(expectedSelectItem);
		
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

	public void selectMultiItemInDropdown(By parentBy, By childBy, String[] expectedSelectItem) {
		// 1. Click vào 1 thẻ bất kì để xổ ra hết tất cả các item trong dropdown ra
		driver.findElement(parentBy).click();
		
		// 2. Chờ cho tất cả item được có trong DOM
		explicitWait.until(
				ExpectedConditions.presenceOfAllElementsLocatedBy(childBy));
		
		// 3. Lấy hết tất cả các item này đưa vào 1 List Element
		List<WebElement> allItems = driver.findElements(childBy);
		
		for (WebElement item : allItems) {
			for (String itemSelected : expectedSelectItem) {
				if (item.getText().trim().equals(itemSelected)) {
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
	
	public boolean areItemSelected(String[] months) {
		List<WebElement> ItemSelected = driver.findElements(By.xpath("//li[@class='selected']"));
		int numberItemSelected = ItemSelected.size();
		
		String allItemSelectedText = driver.findElement(By.xpath("(//button[@class='ms-choice']//span)[1]")).getText();
		
		if(numberItemSelected <= 3 && numberItemSelected > 0) {
			for (String item : months) {
				if(!allItemSelectedText.contains(item)) {
					return false;
				}
			}
			return true;
		} else if(numberItemSelected > 3 && numberItemSelected < 12) {
			return driver.findElement(By.xpath("(//button[@class='ms-choice']//span[text()='" + numberItemSelected + " of 12 selected'])[1]")).isDisplayed();
		} else if(numberItemSelected == 12) {
			return driver.findElement(By.xpath("(//button[@class='ms-choice']//span[text()='All selected'])[1]")).isDisplayed();
		}
		return false;
	}
}
