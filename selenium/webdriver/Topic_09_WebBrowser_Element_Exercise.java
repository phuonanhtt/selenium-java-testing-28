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

public class Topic_09_WebBrowser_Element_Exercise {
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
	public void TC_01_Url() {
		// truy cập trang 
		driver.get("http://live.techpanda.org/");
		// click button my account ở footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		// tạo delay chờ load trang
		sleepInSecond(2);
		// so sánh url có đúng trang login không
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		// click button tạo account
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);
		// verify url
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
	}
	
	@Test
	public void TC_02_Title() {
		// truy cập trang 
		driver.get("http://live.techpanda.org/");
		// click button my account ở footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		// tạo delay chờ load trang
		sleepInSecond(2);
		//cách lấy title: Vào tab console gõ "document.title"
		// so sánh title có đúng trang login không
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		// click button tạo account
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);
		// verify title
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}
	
	@Test
	public void TC_03_Navigation() {
		// truy cập trang 
		driver.get("http://live.techpanda.org/");
		// click button my account ở footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		// tạo delay chờ load trang
		sleepInSecond(2);
		// click button tạo account
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);
		// verify url
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
		// back lại trang login
		driver.navigate().back();
		sleepInSecond(2);
		// so sánh url có đúng trang login không
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		// forward đến trang đăng ký
		driver.navigate().forward();
		sleepInSecond(2);
		// verify title
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}
	
	@Test
	public void TC_04_Page_Source() {
		// truy cập trang 
		driver.get("http://live.techpanda.org/");
		// click button my account ở footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(2);
		// ktra page có chứa text
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account "));
		// click button tạo account
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);
		// ktra page có chứa text
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
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