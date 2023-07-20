package webdriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
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
//			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
//			System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
		}

		driver = new FirefoxDriver();
//		driver = new ChromeDriver();
//		driver = new EdgeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Upload_Single_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		// upload file
		By uploadFile = By.cssSelector("input[type='file']");
		
		// sendKey đường dẫn của file vào thẻ input có type=file
		driver.findElement(uploadFile).sendKeys(spacePath);
		sleepInSecond(3);
		driver.findElement(uploadFile).sendKeys(starPath);
		sleepInSecond(3);
		driver.findElement(uploadFile).sendKeys(treePath);
		sleepInSecond(3);
		
		// verify file đã được upload
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + space + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + star + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + tree + "']")).isDisplayed());
		
		// click start
//		driver.findElement(By.cssSelector("table button.start")).click();
		List<WebElement> startButton = driver.findElements(By.cssSelector("table button.start"));
		
		for (WebElement start : startButton) {
			start.click();
			sleepInSecond(3);
		}
		
		// verify file upload thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + space + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + star + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + tree + "']")).isDisplayed());
		
	}
	
	@Test
	public void TC_02_Upload_Multiple_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		// upload file
		By uploadFile = By.cssSelector("input[type='file']");
		
		// sendKey đường dẫn của file vào thẻ input có type=file
		driver.findElement(uploadFile).sendKeys(spacePath + "\n" + starPath + "\n" + treePath);
		sleepInSecond(3);
		
		// verify file đã được upload
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + space + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + star + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + tree + "']")).isDisplayed());
		
		// tạo biến lưu tất cả button start
		List<WebElement> startButton = driver.findElements(By.cssSelector("table button.start"));
		
		// click start từng file
		for (WebElement start : startButton) {
			start.click();
			sleepInSecond(4);
		}
		
		// verify file upload thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + space + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + star + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + tree + "']")).isDisplayed());
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