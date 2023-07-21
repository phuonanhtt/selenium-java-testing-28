package webdriver;

import java.util.Date;
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

public class Topic_26_Implicit_Wait {
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

		
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Implicit_No_Set() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		// get text
		String helloText = driver.findElement(By.cssSelector("div#finish>h4")).getText();
		
		// verify
		Assert.assertEquals(helloText,"Hello World!");
	}
	@Test
	public void TC_02_Implicit_Not_Enough() {
		// set implicit wait
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		// get text
		String helloText = driver.findElement(By.cssSelector("div#finish>h4")).getText();
		
		// verify
		Assert.assertEquals(helloText,"Hello World!");
	}
	@Test
	public void TC_03_Implicit_Equal() {
		// set implicit wait
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		// get text
		String helloText = driver.findElement(By.cssSelector("div#finish>h4")).getText();
		
		// verify
		Assert.assertEquals(helloText,"Hello World!");
	}
	@Test
	public void TC_04_Implicit_Greater() {
		// set implicit wait
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		// get text
		String helloText = driver.findElement(By.cssSelector("div#finish>h4")).getText();
		
		// verify
		Assert.assertEquals(helloText,"Hello World!");
	}
	
	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
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