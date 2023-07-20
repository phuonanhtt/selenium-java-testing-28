package webdriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_23_Upload_File {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	
	// khai báo tên image
	String space = "space.jpg";
	String star = "star.jpg";
	String tree = "tree.jpg";
	
	// khai báo đường dẫn
//	String spacePath = projectPath + "\\uploadFiles\\" + space;
	String spacePath = projectPath + File.separator + "uploadFiles" + File.separator + space;
	String starPath = projectPath + File.separator + "uploadFiles" + File.separator + star;
	String treePath = projectPath + File.separator + "uploadFiles" + File.separator + tree;
	
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
	public void TC_01_Upload_Single_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		// upload file
		WebElement uploadFile = driver.findElement(By.cssSelector("input[type='file']"));
		
		// sendKey đường dẫn của file vào thẻ input có type=file
		uploadFile.sendKeys(spacePath);
		sleepInSecond(5);
		
		// verify file đã được upload
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + space + "']")).isDisplayed());
		
		// click start
		driver.findElement(By.cssSelector("table button.start")).click();
		sleepInSecond(2);
		
		// verify file upload thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + space + "']")).isDisplayed());
		
	}
	
	@Test
	public void TC_02_() {
		
	}
	@Test
	public void TC_03_() {
		
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