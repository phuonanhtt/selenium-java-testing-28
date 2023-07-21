package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_24_Wait_Element_Status {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	// khai báo wait
	WebDriverWait explicitWait;

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
		// khởi tạo wait
		explicitWait = new WebDriverWait(driver, 30);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Displayed() {
		driver.get("https://www.facebook.com/");
		
		// điều kiện bắt buộc của visible: phải có trên UI
		// Điều kiện 1: E có trên giao diện (UI) và có trong DOM
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[name='login']")));
	}
	
	@Test
	public void TC_02_Undisplayed_In_HTML() {
		driver.get("https://www.facebook.com/");
		
		// click đăng ký
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		// điều kiện bắt buộc của invisible: phải không có trên UI
		// Điều kiện 2: E không có trên giao diện (UI) nhưng có trong DOM
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
				
	}
	
	@Test
	public void TC_03_Undisplayed_Not_In_HTML() {
		driver.get("https://www.facebook.com/");
		// điều kiện bắt buộc của invisible: phải không có trên UI
		// bước 1: find element
		// findElement
		// nếu tìm thấy thì không cần đợi cho hết time
		// nếu k tìm thấy thì đi tìm lại cho đến khi hết time
		// bước 2: apply wait
		// Điều kiện 3: E không có trên giao diện (UI) và không có trong DOM
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
				
	}
	
	@Test
	public void TC_04_Presence() {
		driver.get("https://www.facebook.com/");
		// Điều kiện bắt buộc: phải có trên DOM
		// Điều kiện 1: E có trên giao diện (UI) và có trong DOM
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[name='login']")));
		
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		// Điều kiện 2: E không có trên giao diện (UI) nhưng có trong DOM
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
	}
	
	@Test
	public void TC_05_Staleness() {
		driver.get("https://www.facebook.com/");
		
		// 1 element ở thời điểm trước đó có => sau k hiển thị nữa
		// click đăng ký
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		
		// tại thời điểm này đang có trong HTML
		WebElement confirmEmail = driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']"));
		
		// click tắt popup
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		
		// wait cho confirm email không còn tồn tại trong HTML nữa
		explicitWait.until(ExpectedConditions.stalenessOf(confirmEmail));
		
		explicitWait.until(ExpectedConditions.invisibilityOf(confirmEmail));
		
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