package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_JavascriptExecutor {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {	
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_JavascriptExecutor() {
		navigateToUrlByJS("http://live.techpanda.org/");
		String domainPage = (String) executeForBrowser("return document.domain");
		Assert.assertEquals(domainPage, "live.techpanda.org");
		
		String urlPage = (String) executeForBrowser("return document.URL");
		Assert.assertEquals(urlPage, "http://live.techpanda.org/");

		clickToElementByJS("//a[text()='Mobile']");
		clickToElementByJS("//a[text()='Samsung Galaxy']//parent::h2[@class='product-name']//following-sibling::div[@class='actions']"
				+ "//a[@class='link-compare']");
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The product Samsung Galaxy has been added to comparison list.");
	}

	@Test
	public void TC_02_Verify_HTML5_Validate_Message() {
		driver.get("https://automationfc.github.io/html5/index.html");
		
		driver.findElement(By.cssSelector("input[name='submit-btn']")).click();
		Assert.assertEquals(getElementValidationMessage("//input[@id='fname']"), "Please fill out this field.");
		
		driver.findElement(By.id("fname")).sendKeys("AutomationFC");
		driver.findElement(By.cssSelector("input[name='submit-btn']")).click();
		Assert.assertEquals(getElementValidationMessage("//input[@id='pass']"), "Please fill out this field.");
		
		driver.findElement(By.id("pass")).sendKeys("AutomationFC");
		driver.findElement(By.cssSelector("input[name='submit-btn']")).click();
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please fill out this field.");
		
		driver.findElement(By.id("em")).sendKeys("123@123@123");
		driver.findElement(By.cssSelector("input[name='submit-btn']")).click();
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "A part following '@' should not contain the symbol '@'.");
		
		driver.findElement(By.id("em")).clear();
		driver.findElement(By.id("em")).sendKeys("123@123");
		driver.findElement(By.cssSelector("input[name='submit-btn']")).click();
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please match the requested format.");
		
		driver.findElement(By.id("em")).clear();
		driver.findElement(By.id("em")).sendKeys("automation@mail.vn");
		driver.findElement(By.cssSelector("input[name='submit-btn']")).click();
		Assert.assertEquals(getElementValidationMessage("//form//select"), "Please select an item in the list.");
	}
	
	@Test
	public void TC_03_Verify_HTML5_Validate_Message() {
		driver.get("https://login.ubuntu.com/");
		
		List<WebElement> popupCookie = driver.findElements(By.id("modal"));
		
		if(popupCookie.size() > 0) {
			driver.findElement(By.id("cookie-policy-button-accept")).click();
		}
		
		driver.findElement(By.xpath("//button[@data-qa-id='login_button']")).click();
		Assert.assertEquals(getElementValidationMessage("//input[@id='id_email']"), "Please fill out this field.");
		
		driver.findElement(By.id("id_email")).sendKeys("automation@gmail.com");
		driver.findElement(By.xpath("//button[@data-qa-id='login_button']")).click();
		Assert.assertEquals(getElementValidationMessage("//input[@id='id_password']"), "Please fill out this field.");
		
		driver.findElement(By.id("id_password")).sendKeys("AutomationFC");
		driver.findElement(By.xpath("//button[@data-qa-id='login_button']")).click();
	}
	
	@Test
	public void TC_04_Remove_Attribute() {
		driver.get("http://demo.guru99.com/v4/");
		String name, gender, dateOfBirthInput, dateOfBirthOutput, address, city, state, pin,
		phone, email, password;
		
		By nameTextbox = By.name("name");
		By genderRadio = By.xpath("//input[@name='rad1' and @value='f']");
		By dateOfBirthTextbox = By.name("dob");
		By addressTextarea = By.name("addr");
		By cityTextbox = By.name("city");
		By stateTextbox = By.name("state");
		By pinTextbox = By.name("pinno");
		By phoneTextbox = By.name("telephoneno");
		By emailTextbox = By.name("emailid");
		By passTextbox = By.name("password");
		
		name = "Selenium Online";
		gender = "female";
		dateOfBirthInput = "01/01/2000";
		dateOfBirthOutput = "2000-01-01";
		address = "123 Address";
		city = "Ho Chi Minh";
		state = "Thu Duc";
		pin = "123456";
		phone = "0123456798";
		email = "selenium" + getRandomNumber() + "@gmail.vn";
		password = "uvAbAbU";
		
		driver.findElement(By.cssSelector("input[name='uid']")).sendKeys("mngr360666");
		driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
		driver.findElement(By.cssSelector("input[name='btnLogin']")).click();
		
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		removeAttributeInDOM("//input[@name='dob']", "type");
		
		driver.findElement(nameTextbox).sendKeys(name);
		driver.findElement(genderRadio).click();
		driver.findElement(dateOfBirthTextbox).sendKeys(dateOfBirthInput);
		driver.findElement(addressTextarea).sendKeys(address);
		driver.findElement(cityTextbox).sendKeys(city);
		driver.findElement(stateTextbox).sendKeys(state);
		driver.findElement(pinTextbox).sendKeys(pin);
		driver.findElement(phoneTextbox).sendKeys(phone);
		driver.findElement(emailTextbox).sendKeys(email);
		driver.findElement(passTextbox).sendKeys(password);
		
		driver.findElement(By.name("sub")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirthOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);
	}
	
	@Test
	public void TC_05_Create_An_Account() {
		driver.get("http://live.techpanda.org/");
		
		clickToElementByJS("//div[@id='header-account']//a[text()='My Account']");
		sleepInSecond(3);
		
		clickToElementByJS("//a[@title='Create an Account']");
		sendkeyToElementByJS("//input[@id='firstname']", "Automation");
		sendkeyToElementByJS("//input[@id='lastname']", "FC");
		sendkeyToElementByJS("//input[@id='email_address']", "automationFC" + getRandomNumber() + "@mail.vn");
		sendkeyToElementByJS("//input[@id='password']", "Automation");
		sendkeyToElementByJS("//input[@id='confirmation']", "Automation");
		
		clickToElementByJS("//button[@title='Register']");
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "Thank you for registering with Main Website Store.");
		clickToElementByJS("//div[@id='header-account']//a[text()='Log Out']");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
		if (status) {
			return true;
		}
		return false;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
}
