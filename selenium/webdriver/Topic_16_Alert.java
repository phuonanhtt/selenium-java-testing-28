package webdriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Alert {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	// thêm đường dẫn đến autoIT
	String autoITFirefox = projectPath + "\\autoIT\\authen_firefox.exe";

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
		
		// khởi tạo biến wait để đợi alert
		explicitWait = new WebDriverWait(driver, 10);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// click button để hiển thị alert
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		
		// click button trong alert
		// switch qua: alert/ Iframe (frame)/ Windows
		// sẽ switch thẳng vào mà k chờ alert xuất hiện
		
//		Alert alert2 = driver.switchTo().alert();
		
		// sử dụng wait để đợi alert xuất hiện => thay thế switchTo
		Alert alert1 = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// verify title của alert
		Assert.assertEquals(alert1.getText(), "I am a JS Alert");
		sleepInSecond(2);
		
		// accept
		alert1.accept();
		sleepInSecond(2);
		
		// verify click thành công
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked an alert successfully");

		
//		// Accept alert
//		alert.accept();
//		
//		// cancel alert
//		alert.dismiss();
//		
//		// get text ra verify title của alert
//		alert.getText();
//		
//		// nhập liệu vào alert
//		alert.sendKeys("adjlfdk");
		
	}
	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// click button để hiển thị alert
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(2);
		
		// wait thấy rồi switch qua
		Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// verify title của alert
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		sleepInSecond(2);
		
		// cancel
		alert.dismiss();
		sleepInSecond(2);
		
		// verify click thành công
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Cancel");
	}
	@Test
	public void TC_06_Frame_Alert() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		// switch vào frame
		driver.switchTo().frame(0);
		// click button để hiển thị alert
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(2);
		
		// wait thấy rồi switch qua
		Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// verify title của alert
		Assert.assertEquals(alert.getText(), "Customer ID  cannot be left blank.");
		sleepInSecond(2);
		
		// cancel
		alert.accept();
		sleepInSecond(2);
		
	}
	@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// click button để hiển thị alert
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(2);
		
		// wait thấy rồi switch qua
		Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// verify title của alert
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		sleepInSecond(2);
		
		String keyword = "abcd123";
		// nhập dữ liệu
		alert.sendKeys(keyword);
		alert.accept();
		sleepInSecond(2);
		
		// verify nhập thành công
		Assert.assertEquals (driver.findElement(By.id("result")).getText(),"You entered: " + keyword);
	}
	@Test
	public void TC_04_Authentication_Alert() {
		// k dùng thư viện alert để xử lý được
		// Selenium trick
		
		// truyền trực tiếp username/pass vào url
		// http://username:password@domain
		
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']//p")).getText(), "Congratulations! You must have the proper credentials.");
	}
	//@Test
	public void TC_05_Authentication_Alert() {
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		
		// click link basic
		String basicAuthenUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		sleepInSecond(2);
		
		driver.get(getUrlByUserPass(basicAuthenUrl, "admin", "admin"));
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']//p")).getText(), "Congratulations! You must have the proper credentials.");
	}
	
	@Test
	public void TC_06_Authentication_Alert_AutoIT() throws IOException {
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		
		Runtime.getRuntime().exec(new String[] {autoITFirefox, "admin", "admin"});
		sleepInSecond(5);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']//p")).getText(), "Congratulations! You must have the proper credentials.");
		
	}
	public String getUrlByUserPass(String url, String username, String password) {
		String[] newurl = url.split("//");
		
		// thêm user/pass vào url
		url = newurl[0] + "//" + username + ":" + password + "@" +  newurl[1];
		
		return url;
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