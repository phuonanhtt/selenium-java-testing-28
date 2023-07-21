package webdriver;

import java.util.Date;
import java.util.List;
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

public class Topic_25_Wait_FindElement {
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

//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_FindElement() {
		driver.get("https://www.facebook.com/");
		
		// Tìm thấy có đúng duy nhất 1 element
		// nó không cần chờ hết time
		System.out.println("Start time: " + getDateTimeNow());
		driver.findElement(By.cssSelector("input#email"));
		System.out.println("End time: " + getDateTimeNow());
		
		// Ko tìm thấy element nào
		// Nó sẽ có cơ chế tìm lại, mỗi 0.5s tìm lại 1 lần
		// 1 - tìm lại mà thấy element thì trả về element đó => không cần tìm tiếp nữa
		// 2- tìm lại vẫn không thấy và hết timeout
		// sẽ đánh fail TC tại chính step dó
		// ném ra (throw) 1 ngoai lệ: NoSuchElementException (k tìm thấy element)
		System.out.println("Start time: " + getDateTimeNow());
		driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']"));
		System.out.println("End time: " + getDateTimeNow());
		// Tìm thấy nhiều hơn 1 element
		// lấy ra element đầu tiên
		// khi mình thao tác với 1 element nào đó => tối ưu ở phần locator trước
		driver.findElement(By.xpath("//input[@id='email' or @id='pass']")).sendKeys("testauto");
	}
	@Test
	public void TC_02_FindElements() {
		driver.get("https://www.facebook.com/");
		
		List<WebElement> elements;
		// Tìm thấy có đúng duy nhất 1 element
		// nó không cần chờ hết time
		System.out.println("1 - Start time: " + getDateTimeNow());
		elements = driver.findElements(By.cssSelector("input#email"));
		System.out.println("1 - End time: " + getDateTimeNow());
		System.out.println("Duy nhất 1 element: " + elements.size());
		// Ko tìm thấy element nào
		// Nó sẽ có cơ chế tìm lại, mỗi 0.5s tìm lại 1 lần
		// 1 - tìm lại mà thấy element thì trả về element đó => không cần tìm tiếp nữa
		// 2- tìm lại vẫn không thấy và hết timeout
		// sẽ k dánh fail TC (vẫn chayjo tiếp step tiếp theo)
		// k ném ra ngoại lệ
		// trả về 1 list rỗng (k có element)
		System.out.println("2 - Start time: " + getDateTimeNow());
		elements = driver.findElements(By.cssSelector("input[name='reg_email_confirmation__']"));
		System.out.println("2 - End time: " + getDateTimeNow());
		Assert.assertTrue(elements.isEmpty());
		System.out.println("Không có element nào: " + elements.size());
		// Tìm thấy nhiều hơn 1 element
		// nó không cần chờ hết time
		// trả về 1 list chứa tất cả các element được tìm thấy
		System.out.println("3 - Start time: " + getDateTimeNow());
		elements = driver.findElements(By.xpath("//input[@id='email' or @id='pass']"));
		System.out.println("3 - End time: " + getDateTimeNow());
		Assert.assertFalse(elements.isEmpty());
		System.out.println("Nhiều element: " + elements.size());
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