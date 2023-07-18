package webdriver;

import java.util.Set;
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

public class Topic_21_Window_Tab {
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

	@Test
	public void TC_01_Github() {
		// trang cha
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// ID của trang hiện tại mà driver đang đứng
		String githubID = driver.getWindowHandle();
		
		// click google link
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(3);
		
		// business tự động nhảy qua tab mới
		// driver vẫn ở trang github
		// lấy ra ID của tất cả các tab mà đang mở 
		// 2 tab - 2 ID
//		Set<String> allID = driver.getWindowHandles();
//		 for (String id : allID) {
//			 if(!id.equals(githubID)) {
//				 driver.switchTo().window(id);
//				 break;
//			 }
//		}
		switchToWindowByID(githubID);
		// verify title
		Assert.assertEquals(driver.getTitle(), "Google"); 
		
		String googleID = driver.getWindowHandle();
		
		// switch về parent window
		switchToWindowByID(googleID);
		
		// click face link
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(3);
		
		switchToWindowByID(githubID);
	}
	
	@Test
	public void TC_02_Kyna() {
		
	}
	
	@Test
	public void TC_03_Techpanda() {
		
	}
	
	@Test
	public void TC_04_Cambridge() {
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void switchToWindowByID(String windowID) {
		Set<String> allID = driver.getWindowHandles();
		 for (String id : allID) {
			 if(!id.equals(windowID)) {
				 driver.switchTo().window(id);
				 break;
			 }
		}
	}
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}