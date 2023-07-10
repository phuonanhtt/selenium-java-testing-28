package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Default_Dropdown {
	WebDriver driver;
	Select select;             // không khởi tạo trong before class
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	
	// tạo biến random cho email
	Random rand = new Random();
	String emailAddress;
	String firstName, lastName, password, company;
	String day, month, year;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else { // Windows
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		// khởi tạo driver
		driver = new FirefoxDriver();
		
		// khi khởi tạo cần biến driver
		// tham số khi khởi tạo thư viện sẽ quyết định nó được khởi tạo ở vị trí nào
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);
		
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		// gán giá trị
		firstName = "Test";
		lastName = "Automation";
		password = "123456";
		emailAddress = firstName + rand.nextInt(999) + "@gmail.com";
		company = "Luvina";
		
		day = "1";
		month = "April";
		year = "1967";
		
	}
	
	@Test
	public void TC_01_Register_FB () {
		driver.get("https://www.facebook.com/reg");
		// HTML code
		// thẻ select: cah
		// thẻ option: item con
		// Default dropdown: Thư viện select của selenium
		
		select = new Select(driver.findElement(By.cssSelector("select#day")));
		
		// 1. Chọn item
		
		// k dùng index thì khi thay đổi /thêm /xóa thì index sẽ bị cập nhật lại
		// khi cần reproduce (tái hiện) lại bug thì k sd đc
		select.selectByIndex(10);
		
		// khi cần reproduce (tái hiện) lại bug thì k sd đc
		// value ko phải là tham số bắt buộc
		select.selectByValue("11");
		
		// giống hành vi end user chọn
		select.selectByVisibleText("11");
		
		// Kiểm tra 1 dropdown là single/multiple
		select.isMultiple();
		
		//Kiểm tra 1 dropdown là single
		Assert.assertFalse(select.isMultiple());
		
		// Kiểm tra 1 dropdown là multiple
		// Assert.assertTrue(select.isMultiple());
		
		// lấy ra item được chọn đầu tiên
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "11");
		
		// lấy ra all option
		List<WebElement> day = select.getOptions();
		
		// int - int
		Assert.assertEquals(day.size(), 31);
	}

	@Test
	public void TC_02_NopCommerce() {
		driver.get("https://demo.nopcommerce.com/");
		
		// click đăng ký
		driver.findElement(By.cssSelector("a.ico-register")).click();
		sleepInSecond(1);
		
		// nhập giá trị bắt buộc
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);
		
		// chọn dropdown
		// ngày
		new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']"))).selectByVisibleText(day);
		
		
		// tháng
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		select.selectByVisibleText(month);
		
		// năm
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		select.selectByVisibleText(year);
		
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Company")).sendKeys(company);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		
		// click dky
		driver.findElement(By.id("register-button")).click();
		sleepInSecond(2);
		
		// ktra kết quả
		Assert.assertEquals(driver.findElement(By.cssSelector("div.registration-result-page div.result")).getText(), "Your registration completed");
		
		// click login
		driver.findElement(By.cssSelector("a.ico-login")).click();
		sleepInSecond(2);
		
		// Login
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.login-button")).click();
		
		// Click my account
		driver.findElement(By.cssSelector("a.ico-account")).click();
		sleepInSecond(2);
		
		// verify thông tin account
		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"),firstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"),lastName);
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']"))).getFirstSelectedOption().getText(),day);
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']"))).getFirstSelectedOption().getText(),month);
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']"))).getFirstSelectedOption().getText(),year);
		Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"),emailAddress);
		Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"),company);
		
		// verify đủ 13 item của dropdown month
		List<WebElement> listMonth = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']"))).getOptions();
		Assert.assertEquals(listMonth.size(), 13);
		
		for (WebElement month : listMonth) {
			System.out.println(month.getText());
		}
		
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']"))).getOptions().size(), 32);
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']"))).getOptions().size(), 112);
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