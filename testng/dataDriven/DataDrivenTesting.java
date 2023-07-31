package dataDriven;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class DataDrivenTesting {
//	@Test(dataProvider = "user")
//	public void TC_01(String firstParam, String secondParam) {
//		System.out.println(firstParam);
//		System.out.println(secondParam);
//	}
//
//	@Test(dataProvider = "admin")
//	public void TC_02(String firstParam, String secondParam) {
//		System.out.println(firstParam);
//		System.out.println(secondParam);
//	}
//
//	@DataProvider(name = "user")
//	public Object[][] getUsserData() {
//		return new Object[][] { 
//			new Object[] { "name", "AutoTest" }, 
//			new Object[] { "address", "106 Hoang Quoc Viet" },
//			new Object[] { "city", "Da Nang" }, 
//			new Object[] { "username", "Anh" },
//			new Object[] { "password", "123456" }, };
//	}
//
//	@DataProvider(name = "admin")
//	public Object[][] getAdminData() {
//		return new Object[][] { 
//			new Object[] { "name", "AutoTest 1" },
//			new Object[] { "address", "198 Hoang Quoc Viet" }, 
//			new Object[] { "city", "Da Nang" },
//			new Object[] { "username", "Anh1" }, 
//			new Object[] { "password", "123456" }, };
//	}
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	By emailTextbox = By.xpath("//*[@id='email']");
	By passwordTextbox = By.xpath("//*[@id='pass']");
	By loginButton = By.xpath("//*[@id='send2']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "loginData")
	public void TC_01_LoginToSystem(String username, String password)  {
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");

		driver.findElement(emailTextbox).sendKeys(username);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(loginButton).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains(username));
		
		// ....
		
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
	}

	@DataProvider(name = "loginData")
	public Object[][] UserAndPasswordData() {
		return new Object[][]{
			{"selenium_11_01@gmail.com", "111111"}, 
			{"selenium_11_02@gmail.com", "111111"}, 
			{"selenium_11_03@gmail.com", "111111"}};
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
