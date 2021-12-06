package webdriver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Upload_FIle {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String dalatName = "Dalat.jpg";
	String hanoiName = "HaNoi.jpg";
	String hueName = "Hue.jpg";
	String dalatPath = projectPath + "\\upload-file\\" + dalatName;
	String hanoiPath = projectPath + "\\upload-file\\" + hanoiName;
	String huePath = projectPath + "\\upload-file\\" + hueName;
	String chromePath = projectPath + "\\upload-file\\" + "chromeUploadMultiple.exe";
	String firefoxPath = projectPath + "\\upload-file\\" + "firefoxUploadMultiple.exe";
	
	@BeforeClass
	public void beforeClass() {	
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 20);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_SendKey_Multiple_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		driver.findElement(By.cssSelector("input[name='files[]']")).sendKeys(dalatPath + "\n" + hanoiPath + "\n" + huePath);
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ hanoiName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ hueName +"']")).isDisplayed());
		
		List<WebElement> uploadFileButtons = driver.findElements(By.cssSelector("tr.template-upload button.start"));
		
		for (WebElement uploadFileButton : uploadFileButtons) {
			uploadFileButton.click();
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[text()='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[text()='"+ hanoiName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[text()='"+ hueName +"']")).isDisplayed());
	}
	
	@Test
	public void TC_02_AutoIT() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		driver.findElement(By.cssSelector("span.fileinput-button")).click();
		
		if(driver.toString().contains("firefox")) {			
			Runtime.getRuntime().exec(new String[]{firefoxPath, dalatPath, hanoiPath});
		} else if(driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			Runtime.getRuntime().exec(new String[]{chromePath, dalatPath, hanoiPath});
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ hanoiName +"']")).isDisplayed());
		
		List<WebElement> uploadFileButtons = driver.findElements(By.cssSelector("tr.template-upload button.start"));
		
		for (WebElement uploadFileButton : uploadFileButtons) {
			uploadFileButton.click();
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[text()='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[text()='"+ hanoiName +"']")).isDisplayed());
	}

	@Test
	public void TC_04_Gofile() {
		driver.get("https://gofile.io/uploadFiles");
		
		driver.findElement(By.xpath("//input[@id='uploadFile-Input']")).sendKeys(dalatPath + "\n" + hanoiPath + "\n" + huePath);
		
		// Wait để chờ tất cả các icon progess load upload hết ảnh biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("progress"))));
		// Wait chờ icon loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#mainContent i.fa-spinner")));
		// Wait chờ được message hiển thị
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));
		
		Assert.assertTrue(driver.findElement(By.xpath("//h5[text()='Your files have been successfully uploaded']")).isDisplayed());
		driver.get(driver.findElement(By.xpath("//a[@id='rowUploadSuccess-downloadPage']")).getAttribute("href"));
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName'and text()='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName'and text()='"+ hanoiName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName'and text()='"+ hueName +"']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
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
