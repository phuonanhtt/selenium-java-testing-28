package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

		// khởi tạo biến wait để đợi alert
		explicitWait = new WebDriverWait(driver, 10);
		
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
		
		
		// search
		driver.findElement(By.name("q")).sendKeys("Selenium");
		driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
		sleepInSecond(3);
		
		String googleID = driver.getWindowHandle();
		
		// switch về parent window
		switchToWindowByTitle("Selenium WebDriver");
		
		// click face link
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(3);
		
		switchToWindowByTitle("Facebook – log in or sign up");
		
		// nhập dữ liệu
		driver.findElement(By.cssSelector("input#email")).sendKeys("anhttp@gmail.com");
		driver.findElement(By.cssSelector("input#pass")).sendKeys("123456panh");
		sleepInSecond(3);
		
		switchToWindowByTitle("Selenium WebDriver");
		
		// click tiki link
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		sleepInSecond(3);
		
		switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		sleepInSecond(3);
		
		// đóng các tab con
		closeAllWindowWithoutExpectedID(githubID);
	}
	
	@Test
	public void TC_02_Kyna() {
		driver.get("https://kyna.vn/");
		// lấy id tab cha
		String kynaID = driver.getWindowHandle();
		
		// scroll xuống cuối trang
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		
		// click face ở footer
		driver.findElement(By.cssSelector("div.hotline img[alt='facebook']")).click();
		sleepInSecond(3);
		
		// switch sang trang fb
		switchToWindowByTitle("Kyna.vn | Ho Chi Minh City | Facebook");
		
		// đóng popup login
		By popup = By.cssSelector("div[role='dialog']");
		
		List<WebElement> popupElement = driver.findElements(popup);
		
		if (popupElement.size() > 0 && popupElement.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector("div[aria-label='Close']")).click();
			sleepInSecond(2);
		}
		
		// verify hiển thị page kyna
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Kyna.vn']")).isDisplayed());
		
		// về lại trang kyna
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		
		// click youtube ở footer
		driver.findElement(By.cssSelector("div.hotline img[alt='youtube']")).click();
		sleepInSecond(3);
		
		// switch sang trang yt
		switchToWindowByTitle("Kyna.vn - YouTube" );
		
		// verify
		Assert.assertTrue(driver.findElement(By.xpath("//yt-formatted-string[@id='text' and text()='Kyna.vn']")).isDisplayed());
		
		// về lại trang kyna
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		
		// click đã thông báo ở footer
		driver.findElement(By.cssSelector("div#k-footer-copyright img[src*='dathongbao.png']")).click();
		sleepInSecond(3);
		
		// switch sang trang gov
		switchToWindowByTitle("Thông tin website thương mại điện tử - Online.Gov.VN" );
		
		// verify
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Địa chỉ tên miền:']/parent::div/following-sibling::div/p[contains(text(),'kyna.vn')]")).isDisplayed());
		
		// về lại trang kyna
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		
		// click đã thông báo ở footer
		driver.findElement(By.cssSelector("div#k-footer-copyright img[src*='logoCCDV.png']")).click();
		sleepInSecond(3);
		
		// switch sang trang gov
		switchToWindowByTitle("Thông tin website thương mại điện tử - Online.Gov.VN");
		
		// verify
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Địa chỉ tên miền:']/parent::div/following-sibling::div/p[contains(text(),'kyna.vn')]")).isDisplayed());
		
		// đóng hết các tab con
		closeAllWindowWithoutExpectedID(kynaID);
	}
	
	@Test
	public void TC_03_Techpanda() {
		driver.get("http://live.techpanda.org/");
		
		String pandaID = driver.getWindowHandle();
		
		// click tab mobile
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		
		// add sản phẩm sony vào dể compare
		driver.findElement(By.xpath("//a[@title='Xperia']/following-sibling::div//a[@class='link-compare']")).click();
		
		// verify text
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg")).getText(), "The product Sony Xperia has been added to comparison list.");
		
		// add sản phẩm samsung vào dể compare
		driver.findElement(By.xpath("//a[@title='Samsung Galaxy']/following-sibling::div//a[@class='link-compare']")).click();
		
		// verify text
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg")).getText(), "The product Samsung Galaxy has been added to comparison list.");
		
		// click compare
		driver.findElement(By.cssSelector("button[title='Compare']")).click();
		sleepInSecond(3);
		
		// switch window compare
		switchToWindowByID(pandaID);
		
		// verify title
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		String compare = driver.getWindowHandle();
		
		// close window con, chuyển về window cha
		driver.findElement(By.cssSelector("button[title='Close Window']")).click();
		sleepInSecond(3);
		
		switchToWindowByID(compare);
		
		// click clear all
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		sleepInSecond(2);
		
		// wait thấy rồi switch qua
		Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		// accept
		alert.accept();
		sleepInSecond(2);
		
		// verify text
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg")).getText(), "The comparison list was cleared.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void closeAllWindowWithoutExpectedID(String expectedID) {
		Set<String> allIDs = driver.getWindowHandles();
		
		for (String id : allIDs) {
			if (!id.equals(expectedID)) {
				driver.switchTo().window(id);
				driver.close();
			}
		}
		driver.switchTo().window(expectedID);
	}
	
	// Có thể chạy cho 2 hoặc nhiều hơn 2 window/tab
	public void switchToWindowByTitle(String title) {
		Set<String> allID = driver.getWindowHandles();
		for (String id : allID) {
			// switch qua từng page
			driver.switchTo().window(id);
			// lấy ra tilte của page hiện tại
			String actualTitle = driver.getTitle();
			// nếu đúng title thì thoát vòng lặp
			if(actualTitle.equals(title)) {
				break;
			}
		}
	}
	
	// duy nhất 2 window/ tab
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