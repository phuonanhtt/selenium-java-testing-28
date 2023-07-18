package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Iframe {
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
	public void TC_01_Kyna() {
		driver.get("https://skills.kynaenglish.vn/");

		WebElement iframeFace = driver.findElement(By.cssSelector("div.fanpage iframe"));
		
		// verify có hiển thị iframe
		Assert.assertTrue(iframeFace.isDisplayed());
		
		// Cần phải switch qua frame/ iframe
//		// by index
//		driver.switchTo().frame(0);
//		
//		// by id/name
//		driver.switchTo().frame("");
		
		
		// by web element
		driver.switchTo().frame(iframeFace);
		
		// verify follow
		Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::div/following-sibling::div")).getText(), "165K followers");
		
		// switch veef page trước đó
		driver.switchTo().defaultContent();
		
		// switch chat
		driver.switchTo().frame("cs_chat_iframe");
		
		//click open chat để mở cửa sổ lên
		driver.findElement(By.cssSelector("div.meshim_widget_Widget")).click();
		
		// nhập thông tin
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("phuong anh");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0123456789");
		
		new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Java bootcamp");
		sleepInSecond(3);
		
		// Nếu k về trang default thì k tương tác với các element của page khác được
		// switch về page trước đó
		driver.switchTo().defaultContent();
		
		String keyword = "Excel";
		// search
		driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys(keyword);
		driver.findElement(By.cssSelector("button.search-button")).click();
		
		List<WebElement> listCourse = driver.findElements(By.cssSelector("div.content>h4"));
		
		// verify course number
		Assert.assertEquals(listCourse.size(), 9);
		
		// verify course name
		for (WebElement course : listCourse) {
			System.out.println(course.getText());
			Assert.assertTrue(course.getText().contains(keyword));
		}
	}
	
	@Test
	public void TC_02_HDFC_Bank() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		// switch vào frame
		driver.switchTo().frame("login_page");
		
		// nhập vào user id
		driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("mary");
		sleepInSecond(2);
		
		// click continue
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(3);
		
		// switch về page trước đó
		driver.switchTo().defaultContent();
		
		// verify pass được hiển thị
		Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
		
		driver.findElement(By.cssSelector("input#keyboard")).sendKeys("123456lk");
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