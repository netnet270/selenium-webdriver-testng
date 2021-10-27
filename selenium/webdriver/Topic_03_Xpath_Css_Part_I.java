package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Xpath_Css_Part_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
	}
	
	@Test
	public void TC_01_Register_Empty_Data() {
		driver.findElement(By.id("txtFirstname")).sendKeys("");
		driver.findElement(By.id("txtEmail")).sendKeys("");
		driver.findElement(By.id("txtCEmail")).sendKeys("");
		driver.findElement(By.id("txtPassword")).sendKeys("");
		driver.findElement(By.id("txtCPassword")).sendKeys("");
		driver.findElement(By.id("txtPhone")).sendKeys("");
		driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
		
		//verify
		Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Vui lòng nhập lại địa chỉ email");
		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Vui lòng nhập mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
	}

	@Test
	public void TC_02_Register_Invalid_Email() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");

		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("123@123.123@");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@123.123@");
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456");
		driver.findElement(By.id("txtPhone")).sendKeys("0987654321");
		driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email hợp lệ");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
	}
	
	@Test
	public void TC_03_Register_Incorrect_Confirm_Email() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");

		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.net");
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456");
		driver.findElement(By.id("txtPhone")).sendKeys("0987654321");
		driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
	}

	@Test
	public void TC_04_Register_Password_Less_Than_6_Characters() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtPassword")).sendKeys("123");
		driver.findElement(By.id("txtCPassword")).sendKeys("123");
		driver.findElement(By.id("txtPhone")).sendKeys("0987654321");
		driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
	}
	
	@Test
	public void TC_05_Register_Incorrect_Confirm_Password() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123465");
		driver.findElement(By.id("txtPhone")).sendKeys("0987654321");
		driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Mật khẩu bạn nhập không khớp");
	}
	
	@Test
	public void TC_06_Register_Invalid_Phone_Number() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456");
		driver.findElement(By.id("txtPhone")).sendKeys("098765432");
		driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại phải từ 10-11 số.");
		
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456");
		driver.findElement(By.id("txtPhone")).sendKeys("498765432");
		driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
