package webdriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_30_Mixing_Wait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
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
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_Element_Found() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 30);
		
		// Dùng cả 2 loại nhưng element được tìm thấy
		// ko có ngoại lệ xảy ra => happy case
		driver.get("https://www.facebook.com/");
		
		// Implicit
		System.out.println("1 - Start time: " + getDateTimeNow());
		driver.findElement(By.cssSelector("input#email"));
		System.out.println("1 - End time: " + getDateTimeNow());
		
		// explicit
		System.out.println("2 - Start time: " + getDateTimeNow());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
		System.out.println("2 - End time: " + getDateTimeNow());
	}
	
	//@Test
	public void TC_02_Element_Not_Found_Only_Implicit() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Dùng cả 2 loại nhưng element được tìm thấy
		// ko có ngoại lệ xảy ra => happy case
		driver.get("https://www.facebook.com/");
		
		// Implicit
		// Không tìm element
		// Đánh fail TC tại step findElement
		// chờ hết time của implicit
		System.out.println("3 - Start time: " + getDateTimeNow());
		driver.findElement(By.cssSelector("input#automation"));
		System.out.println("3 - End time: " + getDateTimeNow());
		
	}
	
	//@Test
	public void TC_03_Element_Not_Found_Only_Explicit() {
		explicitWait = new WebDriverWait(driver, 10);
		
		driver.get("https://www.facebook.com/");
		
		// Explicit wait
		// chờ hết 10s rồi đánh fail TC tại step dòng code số 88
		System.out.println("4 - Start time: " + getDateTimeNow());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#automation")));
		System.out.println("4 - End time: " + getDateTimeNow());
	}
	
	@Test
	public void TC_04_Element_Not_Found_Implicit_Explicit() {
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 10);
		
		driver.get("https://www.facebook.com/");
		// Mấu chốt: Luôn luôn ưu tiên ưu tiên implicit trước
		// Tìm được element rồi mới làm gì thì làm
		
		// Implicit
//		System.out.println("5 - Start time: " + getDateTimeNow());
//		driver.findElement(By.cssSelector("input#automation"));
//		System.out.println("5 - End time: " + getDateTimeNow());
		
		// explicit
		// bị áp dụng cả 2 loại wait trong step này
		// 10s của implicit cho findElement
		// 10s của explicit cho điều kiện
		System.out.println("6 - Start time: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#automation")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("6 - End time: " + getDateTimeNow());
		
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
	}	

}