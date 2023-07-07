package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Textbox_TextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	
	// tạo biến random cho email
	Random rand = new Random();
	String emailAddress = "anhttp" + rand.nextInt(99999) + "@gmail.com";
	
	// khai báo các trường khác
	String firstName;
	String lastName;
	String fullName;
	String password;
	
	String employeeID, userName;
	String passportID, issuedDate, expireDate, comment;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else { // Windows
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);
		
		// apply cho trạng thái của element: hiển thị/ k hiển thị / presence/ staleness
		// expliciwait = new WebDriverWait(driver, 30);
		
		// apply cho việc tìm element (findElement)
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		// gán giá trị
		emailAddress = "anhttp" + rand.nextInt(99999) + "@gmail.com";
		firstName = "Phuong Anh";
		lastName = "Tran";
		fullName = firstName + " " + lastName;
		password = "12345Pass";
		userName = "phuonganh" + rand.nextInt(999);
		
		// link fake: https://www.elfqrin.com/usssndriverlicenseidgen.php
		passportID = "998465861";
		issuedDate = "2018-06-30";
		expireDate = "2028-06-29";
		comment = "Employer Identification Number (EIN)\n141012901";
		
	}

	@Test
	public void TC_01_TechPanda_Register() {
		driver.get("http://live.techpanda.org/");
		
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
		sleepInSecond(1);
		
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		sleepInSecond(1);
		
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		
		driver.findElement(By.cssSelector("button[title='Register'")).click();
		sleepInSecond(1);
		
		// verify đky thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "Thank you for registering with Main Website Store.");
		// lấy text thông tin tk để ktra
		String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		//verify tk có giống với đã nhập
		Assert.assertTrue(contactInfo.contains(fullName));
		Assert.assertTrue(contactInfo.contains(emailAddress));
		
		// di chuyển đến trang thông tin
		driver.findElement(By.xpath("//a[text()='Account Information']")).click();
		
		//verify 
		Assert.assertEquals(driver.findElement(By.id("firstname")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("lastname")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), emailAddress);
		
		//logout
		driver.findElement(By.cssSelector("a.skip-account>span.label")).click();
		driver.findElement(By.cssSelector("a[title='Log Out']")).click();
		sleepInSecond(3);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("div.page-title img")).isDisplayed());
		
		
	}

	@Test
	public void TC_02_OrangeHRM() {
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		
		// Login
		driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
		driver.findElement(By.name("password")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[contains(@class,'orangehrm-login-button')]")).click();
		
		sleepInSecond(3);
		
		// Click PIM
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		sleepInSecond(3);
		
		// Nhập dữ liệu
		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(lastName);
		
		// lấy employee id
		// ktra trong console để xem value
		// $x("//label[text()='Employee Id']/parent::div/following-sibling::div/input");
		
		employeeID = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value");
		
		// click detail
		driver.findElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span")).click();
		sleepInSecond(2);
		
		// nhập thông tin
		driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys(userName);
		driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys(password);
		driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys(password);
		
		// Click button 
		driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click();
		sleepInSecond(10);
		
		// verify dữ liệu
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), employeeID);
		
		// click tab immigration
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(2);
		
		// Add mới
		driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();
		
		// nhập dữ liệu
		driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys(passportID);
		driver.findElement(By.xpath("//label[text()='Issued Date']/parent::div/following-sibling::div//input")).sendKeys(issuedDate);
		driver.findElement(By.xpath("//label[text()='Expiry Date']/parent::div/following-sibling::div//input")).sendKeys(expireDate);
		driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).sendKeys(comment);
		
		// Click button 
		driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click();
		sleepInSecond(6);
		
		// verify dữ liệu trong bảng
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + passportID + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + issuedDate + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + expireDate + "']")).isDisplayed());
		
		// click button edit
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		sleepInSecond(3);
		
		// verify data 
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"), passportID);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Issued Date']/parent::div/following-sibling::div//input")).getAttribute("value"), issuedDate);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Expiry Date']/parent::div/following-sibling::div//input")).getAttribute("value"), expireDate);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"), comment);
		
		// Log out
		driver.findElement(By.cssSelector("p.oxd-userdropdown-name")).click();
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		sleepInSecond(2);
		
		// Log in as user
		driver.findElement(By.cssSelector("input[name='username']")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[contains(@class,'orangehrm-login-button')]")).click();
		sleepInSecond(3);

		// Click my info
		driver.findElement(By.xpath("//span[text()='My Info']")).click();
		sleepInSecond(2);
		
		// verify
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), employeeID);
		
		// click tab immigration
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(2);
		
		// verify dữ liệu trong bảng
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + passportID + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + issuedDate + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + expireDate + "']")).isDisplayed());
		
		// click button edit
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		sleepInSecond(3);
		
		// verify data 
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"), passportID);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Issued Date']/parent::div/following-sibling::div//input")).getAttribute("value"), issuedDate);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Expiry Date']/parent::div/following-sibling::div//input")).getAttribute("value"), expireDate);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"), comment);
		
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}