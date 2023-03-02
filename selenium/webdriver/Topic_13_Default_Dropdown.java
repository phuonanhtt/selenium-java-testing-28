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

public class Topic_13_Default_Dropdown {
	WebDriver driver;
	Select select;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String firstname, lastname, email, password, companyName;
	String date, month, year;
	JavascriptExecutor jsExecutor;
	Actions action;
	Random rand = new Random();

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else { // Windows
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		// Khởi tạo driver
		driver = new FirefoxDriver();
		System.out.println(driver.toString());
		//Khi khởi tạo cần biến driver
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		firstname = "Tom";
		lastname = "Cruise";
		email = "tomcruise" + rand.nextInt(99999) + "@gmail.vn";
		password = "123123";
		companyName = "IPM";
		date =  "12";
		month = "January";
		year = "1999";
		
		
	}

	@Test
	public void TC_01_Facebook() {
		//driver.get("https://www.facebook.com/reg");
		
		
		/*
		select.selectByIndex(3);
		//hn
		//k dùng vì khi sửa/xóa sẽ bị thay đổi
		// khi cần reproduce lỗi lại (tái hiện lại bug)
		select.selectByValue("9806");
		//hcm
		//khi cần reproduce lỗi lại (tái hiện lại bug)
		// value ko phải là tham số bắt buộc
		
		select.deselectByVisibleText("thành phố Đà Nẵng");
		//dn
		// giống hành vi end user chọn
		
		//chọn item
		
		//kiểm tra xem 1 item là single/multiple
		select.isMultiple();
		//single
		Assert.assertFalse(select.isMultiple());
		//multiple
		Assert.assertTrue(select.isMultiple());
		
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "thành phố Đà Nẵng");
		
		//lấy ra all item
		List<WebElement> city = select.getOptions();
		
		//int -int
		Assert.assertEquals(city.size(), 66);
		
		for (WebElement text : city) {
			System.out.println(text.getText());
		}
	
		select = new Select(driver.findElement(By.cssSelector("select#day")));
		select.selectByVisibleText("11");
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "11");
		sleepInSecond(3);
		
		select = new Select(driver.findElement(By.cssSelector("select#month")));
		select.selectByVisibleText("Jun");
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Jun");
		sleepInSecond(3);
		
		*/
	}
	
	@Test
	public void TC_01_NopComerce() {
		driver.get("https://demo.nopcommerce.com/register");
		
		driver.findElement(By.id("FirstName")).sendKeys(firstname);
		driver.findElement(By.id("LastName")).sendKeys(lastname);
		
		//d
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']")));
		select.selectByVisibleText(date);
		//m
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		select.selectByVisibleText(month);
		
		//y
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		select.selectByVisibleText(year);
		
		driver.findElement(By.id("Email")).sendKeys(email);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		
		driver.findElement(By.id("register-button")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.registration-result-page div.result")).getText(), "Your registration completed");
		
		driver.findElement(By.cssSelector("a.ico-login")).click();
		sleepInSecond(3);
		
		driver.findElement(By.cssSelector("input#Email")).sendKeys(email);
		driver.findElement(By.cssSelector("input#Password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.login-button")).click();
		sleepInSecond(3);
		
		driver.findElement(By.cssSelector("a.ico-account")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("input#FirstName")).getAttribute("value"), firstname);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#LastName")).getAttribute("value"), lastname);
		
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']"))).getFirstSelectedOption().getText(), date);
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']"))).getFirstSelectedOption().getText(), month);
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']"))).getFirstSelectedOption().getText(), year);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("input#Email")).getAttribute("value"), email);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#Company")).getAttribute("value"), companyName);
		
		
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