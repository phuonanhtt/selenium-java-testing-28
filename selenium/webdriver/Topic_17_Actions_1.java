package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Actions_1 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;

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

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_ToolTip() {
		// Action: 1 thư viện dùng để giả lập lại cá hành động của chuột/ bàn phím
		// user interaction
		// khi testscript đang chạy về action thì k được sử dụng chuột và bàn phím
		
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		
		// di chuột tới element trước r click
		// action.click(driver.findElement(By.cssSelector(""))).perform();
		// click bt, k di chuột
		// driver.findElement(By.cssSelector("")).click();
		
		// Hover vào tooltip
		action.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
		sleepInSecond(2);
		
		// firefox: chạy lệnh console: setTimeout(() => {debugger;},3000);
		// hover chuột và dợi tooltip hiện, chờ tới timeout để bắt element
		// verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
		
	}
	
	@Test
	public void TC_02_ToolTip() {
//		driver.get("http://www.myntra.com/");
//		
//		// hover vào kid
//		action.moveToElement(driver.findElement(By.xpath("//a[@data-group='kids']"))).perform();
//		sleepInSecond(2);
//		
//		// click home&bath
//		action.click(driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Home & Bath']"))).perform();
//		sleepInSecond(2);
//		
//		Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
		driver.get("https://www.healthkart.com/");
		
		// hover
	    action.moveToElement(driver.findElement(By.xpath("//div[text()='Customer Support']"))).perform();
	    sleepInSecond(2);
	    
	    // click
		action.click(driver.findElement(By.xpath("//div[text()='Terms & Conditions']"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.xpath("//h1[text()='Terms and Conditions']")).getText(), "Terms and Conditions");
	}
	
	@Test
	public void TC_03_Click_And_Hold() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		// 1. click chuột trái vào số 1
		// 2. vẫn giữ chuột trái
		action.clickAndHold(driver.findElement(By.xpath("//li[text()='1']")))
		
		// 3. kéo chuột tới số kết thúc
		.moveToElement(driver.findElement(By.xpath("//li[text()='4']")))
		
		// 4. nhả chuột trái ra
		.release()
		// thực thi các hành động trên
		.perform();
		
		sleepInSecond(4);
	}
	
	@Test
	public void TC_04_() {
		
	}
	@Test
	public void TC_05_() {
		
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