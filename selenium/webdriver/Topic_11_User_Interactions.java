package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_User_Interactions {
	WebDriver driver;
	Actions actions;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {	
		// FirefoxDriver
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		actions = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Hover_Tooltip() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		
		actions.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ui-tooltip-content' and text()='We ask for your age only for statistical purposes.']")).isDisplayed());
	}

	@Test
	public void TC_02_Hover_Menu() {
		driver.get("https://www.myntra.com/");
		
		actions.moveToElement(driver.findElement(By.xpath("//nav[@class='desktop-navbar']//a[text()='Kids']"))).perform();
		actions.click(driver.findElement(By.xpath("//nav[@class='desktop-navbar']//a[text()='Home & Bath']"))).perform();
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb' and text()='Kids Home Bath']")).isDisplayed());
	}
	
	@Test
	public void TC_03_Menu_Fahasa() {
		driver.get("https://www.fahasa.com/");
		
		actions.moveToElement(driver.findElement(By.cssSelector(".fhs-header-top-bar a[title='Sách Trong Nước']"))).perform();
		actions.click(driver.findElement(By.xpath("//div[@class='fhs-header-top-bar holded']//a[text()='Kỹ Năng Sống']"))).perform();
	}

	@Test
	public void TC_04_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> allItems = driver.findElements(By.cssSelector("ol#selectable>li.ui-selectee"));
		actions.clickAndHold(allItems.get(0)).moveToElement(allItems.get(3)).release().perform();
		Assert.assertEquals(driver.findElements(By.cssSelector("ol#selectable>li.ui-selectee.ui-selected")).size(), 4);
	}
	
	@Test
	public void TC_05_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> allItems = driver.findElements(By.cssSelector("ol#selectable>li.ui-selectee"));
		
		actions.keyDown(Keys.CONTROL).perform();
		actions.click(allItems.get(0)).click(allItems.get(2)).click(allItems.get(5)).click(allItems.get(7)).perform();
		actions.keyUp(Keys.CONTROL).perform();
	}
	
	@Test
	public void TC_06_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
		actions.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
	}
	
	@Test
	public void TC_07_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		actions.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		actions.moveToElement(driver.findElement(By.cssSelector(".context-menu-icon-quit"))).perform();
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible.context-menu-hover")).isDisplayed());
		driver.findElement(By.cssSelector(".context-menu-icon-quit")).click();
		driver.switchTo().alert().accept();
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
	}
	
	@Test
	public void TC_08_Drag_And_Drop() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		
		WebElement smallCircle = driver.findElement(By.cssSelector("#draggable"));
		WebElement bigCircle = driver.findElement(By.cssSelector("#droptarget"));
		
		Assert.assertEquals(bigCircle.getText(), "Drag the small circle here.");
		actions.dragAndDrop(smallCircle, bigCircle).perform();
		Assert.assertEquals(bigCircle.getText(), "You did great!");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
