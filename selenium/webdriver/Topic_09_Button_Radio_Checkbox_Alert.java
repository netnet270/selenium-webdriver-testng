package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button_Radio_Checkbox_Alert {
	WebDriver driver;
	JavascriptExecutor jsExcutor;
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
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create?attempt=1");
		
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		By loginButtonBy = By.cssSelector("button.fhs-btn-login");
		
		Assert.assertFalse(driver.findElement(loginButtonBy).isEnabled());
		
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("automation@gmail.com");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456");
		
		Assert.assertTrue(driver.findElement(loginButtonBy).isEnabled());
		String rgbaColorButton = driver.findElement(loginButtonBy).getCssValue("background-color");
		String hexaColorButton = Color.fromString(rgbaColorButton).asHex().toUpperCase();
		
		System.out.print(hexaColorButton);
		Assert.assertEquals(hexaColorButton, "#C92127");
		
		driver.navigate().refresh();
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
	
		jsExcutor.executeScript("arguments[0].removeAttribute('disabled');", driver.findElement(loginButtonBy));
		driver.findElement(loginButtonBy).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Số điện thoại/Email']//following-sibling::div[@class='fhs-input-alert' "
				+ "and text()='Thông tin này không thể để trống']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Mật khẩu']//following-sibling::div[@class='fhs-input-alert' "
				+ "and text()='Thông tin này không thể để trống']")).isDisplayed());
	}

	@Test
	public void TC_02_Default_Checkbox_Radio() {
		// Checkbox
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		By inputCheckboxBy = By.xpath("//label[text()='Dual-zone air conditioning']//preceding-sibling::input");
		
		checkItem(inputCheckboxBy);
		Assert.assertTrue(driver.findElement(inputCheckboxBy).isSelected());
		
		unCheckItem(inputCheckboxBy);
		Assert.assertFalse(driver.findElement(inputCheckboxBy).isSelected());
		
		// Radio_Button
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		
		By inputRadioBy = By.xpath("//label[text()='2.0 Diesel, 103kW']//preceding-sibling::input");
		
		checkItem(inputRadioBy);
		Assert.assertTrue(driver.findElement(inputRadioBy).isSelected());
	}
	
	@Test
	public void TC_03_Custom_Checkbox_Radio() {
		// Custom Radio_Button
		driver.get("https://material.angular.io/components/radio/examples");
		
		String itemSelected1 = "Summer";
		String itemSelected2 = "Winter";
		
		By itemClickRadioBy1 = By.xpath("//span[contains(text(),'" + itemSelected1 + "')]//preceding-sibling::span[@class='mat-radio-container']");
		By itemVerifyRadioBy1 = By.xpath("//span[contains(text(), '" + itemSelected1 + "')]//preceding-sibling::span[@class='mat-radio-container']//input");
		By itemClickRadioBy2 = By.xpath("//span[contains(text(),'" + itemSelected2 + "')]//preceding-sibling::span[@class='mat-radio-container']");
		By itemVerifyRadioBy2 = By.xpath("//span[contains(text(), '" + itemSelected2 + "')]//preceding-sibling::span[@class='mat-radio-container']//input");
		
		checkItemCustom(itemClickRadioBy1, itemVerifyRadioBy1);
		Assert.assertTrue(driver.findElement(itemVerifyRadioBy1).isSelected());
		
		checkItemCustom(itemClickRadioBy2, itemVerifyRadioBy2);
		Assert.assertTrue(driver.findElement(itemVerifyRadioBy2).isSelected());
	
		// Custom Checkbox
		driver.get("https://material.angular.io/components/checkbox/examples");
		
		String checkbox1 = "Checked";
		String checkbox2 = "Indeterminate";
		
		By itemClickCheckboxBy1 = By.xpath("//span[contains(text(),'" + checkbox1 + "')]//preceding-sibling::span[@class='mat-checkbox-inner-container']");
		By itemVerifyCheckboxBy1 = By.xpath("//span[contains(text(), '" + checkbox1 + "')]//preceding-sibling::span[@class='mat-checkbox-inner-container']//input");
		By itemClickCheckboxBy2 = By.xpath("//span[contains(text(),'" + checkbox2 + "')]//preceding-sibling::span[@class='mat-checkbox-inner-container']");
		By itemVerifyCheckboxBy2 = By.xpath("//span[contains(text(), '" + checkbox2 + "')]//preceding-sibling::span[@class='mat-checkbox-inner-container']//input");
		
		checkItemCustom(itemClickCheckboxBy1, itemVerifyCheckboxBy1);
		Assert.assertTrue(driver.findElement(itemVerifyCheckboxBy1).isSelected());
		
		checkItemCustom(itemClickCheckboxBy2, itemVerifyCheckboxBy2);
		Assert.assertTrue(driver.findElement(itemVerifyCheckboxBy2).isSelected());
		
		unCheckItemCustom(itemClickCheckboxBy1, itemVerifyCheckboxBy1);
		Assert.assertFalse(driver.findElement(itemVerifyCheckboxBy1).isSelected());
		
		unCheckItemCustom(itemClickCheckboxBy2, itemVerifyCheckboxBy2);
		Assert.assertFalse(driver.findElement(itemVerifyCheckboxBy2).isSelected());
	}
	
	@Test
	public void TC_04_Custom_Checkbox_Radio_Docs() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='false']")).isDisplayed());
		
		driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='false']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='true']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void checkItem(By by) {
		WebElement item = driver.findElement(by);
		if(!item.isSelected()) item.click();
	}
	
	public void unCheckItem(By by) {
		WebElement item = driver.findElement(by);
		if(item.isSelected()) item.click();
	}

	public void checkItemCustom(By itemClickBy, By itemVerifyBy) {
		WebElement itemClick = driver.findElement(itemClickBy);
		WebElement itemVerify = driver.findElement(itemVerifyBy);

		if(!itemVerify.isSelected()) itemClick.click();
	}
	
	public void unCheckItemCustom(By itemClickBy, By itemVerifyBy) {
		WebElement itemClick = driver.findElement(itemClickBy);
		WebElement itemVerify = driver.findElement(itemVerifyBy);

		if(itemVerify.isSelected()) itemClick.click();
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
