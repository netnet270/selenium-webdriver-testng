package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_Command_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {	
		// FirefoxDriver
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		// ChormeDriver
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
	}
	
	@Test
	public void TC_01_Command() {
		driver.findElement(By.name("login")).click();
		
		WebElement element = driver.findElement(By.id("email"));
		
		//xóa dữ liệu trong editable field (textbox/textarea/dropdown)
		element.clear();
		
		//Nhập dữ liệu vào editable filed
		element.sendKeys("abc");
		element.sendKeys(Keys.ENTER);
		
		// click vào button/link/radio/checkbox/image...
		element.click();
	
		// Trả về  dữ liệu nằm trong attr của element
		element.getAttribute("");
		
		// Lấy thuộc tính của element: size/color/background...
		element.getCssValue("color");
		// => trả về rgba => phải convert sang mã hexa
		
		// GUI
		element.getLocation();
		element.getRect();
		element.getSize();
		
		// Take Screenshot => Attach to HTML report
		 element.getScreenshotAs(OutputType.FILE);
		 
		 // Get tên thẻ HML
		 element.getTagName();
		 
		 // lấy text của header/link/label/error...
		 element.getText();
		 
		 // kiểm tra element có hiển thị hay không (hiển thị: người dùng nhìn thấy được và thao tác được )
		 element.isDisplayed();
		 
		 // Kiểm tra 1 element có thể thao tác được hay không(không bị disable/không phải là readonly field)
		 element.isEnabled();
		 
		 // Kiểm tra 1 e đã được chọn hay chưa(radio/checkbox/dropdown)
		 element.isSelected();
		 
		 // submit vào 1 form - rất ít khi sử dụng
		 element.submit();
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
