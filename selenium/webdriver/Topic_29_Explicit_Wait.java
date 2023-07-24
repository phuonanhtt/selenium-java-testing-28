package webdriver;

import java.io.File;

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

public class Topic_29_Explicit_Wait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	WebDriverWait explicitWait;
	// khai báo tên image
	String space = "space.jpg";
	String star = "star.jpg";
	String tree = "tree.jpg";
	
	// khai báo đường dẫn
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

		driver.manage().window().maximize();
		explicitWait = new WebDriverWait(driver, 30);
	}

	@Test
	public void TC_01_Visible() {		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		// wait cho text được visible
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4")));
		
		// get text
		String helloText = driver.findElement(By.cssSelector("div#finish>h4")).getText();
		
		// verify
		Assert.assertEquals(helloText,"Hello World!");
	}
	
	@Test
	public void TC_02_Invisible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		// wait cho loading icon invisible (biến mất)
		// khi cái này biến mất thì cái kia sẽ xuất hiện hoặc ngược lại
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));
		
		// get text
		String helloText = driver.findElement(By.cssSelector("div#finish>h4")).getText();
		
		// verify
		Assert.assertEquals(helloText,"Hello World!");
	}

	@Test
	public void TC_03_Text() {

		// khởi tạo
		explicitWait = new WebDriverWait(driver, 10);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		// wait cho text xuất hiện
		Assert.assertTrue(explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("div#finish>h4"),"Hello World!")));
		
		// get text
		String helloText = driver.findElement(By.cssSelector("div#finish>h4")).getText();
		
		// verify
		Assert.assertEquals(helloText,"Hello World!");
	}
	
	@Test
	public void TC_04_Telerik() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		// wait cho calendar visible
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#ctl00_ContentPlaceholder1_Panel1")));
		
		// wait và verify cho locator chứa text
		Assert.assertTrue(explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"), "No Selected Dates to display.")));
	
		// wait cho ngày được click
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='24']/parent::td"))).click();
		
		// wait cho loading icon biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id$='RadCalendar1']>div.raDiv")));
		
		// wait và verify cho locator chứa text
		Assert.assertTrue(explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"), "Monday, July 24, 2023")));
		
	}
	
	@Test
	public void TC_05_Upload() {
		driver.get("https://gofile.io/welcome");
		
		// wait cho tất cả icon loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.spinner-border"))));
		
		// upload file
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text() = 'Upload Files']"))).click();
		
		// wait cho tất cả icon loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.spinner-border"))));
		
		// upload 3 file
		driver.findElement(By.cssSelector("input#filesUploadInput")).sendKeys(spacePath + "\n" + starPath + "\n" + treePath);
		
		// wait cho tất cả upload file biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress-bar"))));
		
		// wait for text + verify
		Assert.assertTrue(explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("div.mainUploadSuccess div.border-success"), "Your files have been successfully uploaded")));
		
		driver.get(driver.findElement(By.cssSelector("div.mainUploadSuccessLink a.ajaxLink")).getText());
		
		// wait cho tất cả upload file biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress-bar"))));
		
		// wait + verify
		Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='contentName' and text() ='" + space + "']"))).isDisplayed());
		Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='contentName' and text() ='" + star + "']"))).isDisplayed());
		Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='contentName' and text() ='" + tree + "']"))).isDisplayed());

	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}