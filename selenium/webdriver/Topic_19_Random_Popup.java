package webdriver;

import java.util.List;
import java.util.Random;
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

public class Topic_19_Random_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	
	Random rand = new Random();
	String email = "anhttp" + rand.nextInt(99999) + "@gmail.com";
	
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
	public void TC_01_Random_Popup_In_HTML_Java() {
		// mới mở page ra thì popup chưa có trong HTML
		driver.get("https://www.javacodegeeks.com/");
		sleepInSecond(35);
		
		// List findElements
		By firstStepPopup2 = By.cssSelector("div[data-title='Newsletter-Books Anime Brief']:not([data-page='confirmation'])");
		By firstStepPopup = By.cssSelector("div[data-title='Newsletter Free eBooks']:not([data-page='confirmation'])");
		By secondStepPopup2 = By.cssSelector("div[data-title='Newsletter-Books Anime Brief'][data-page='confirmation']");
		By secondStepPopup = By.cssSelector("div[data-title='Newsletter Free eBooks'][data-page='confirmation']");
		
		List<WebElement> firstStepPopupElement2 = driver.findElements(firstStepPopup2);
		List<WebElement> firstStepPopupElement = driver.findElements(firstStepPopup);
		
		// Nếu như có hiển thị thì nhập thông tin vào và click OK
		// Xử lý tiếp step tiếp theo đến khi nào popup đóng lại
		if (firstStepPopupElement.size() > 0 && firstStepPopupElement.get(0).isDisplayed()) {
			// nhập email
			driver.findElement(By.cssSelector("input[placeholder='Your Email']")).sendKeys(email);
			sleepInSecond(3);
			// click ok
			driver.findElement(By.cssSelector("a[data-label='OK']")).click();
			sleepInSecond(4);
			
			// ktra popup xác nhận được hiển thị
			Assert.assertTrue(driver.findElement(secondStepPopup).isDisplayed());
			sleepInSecond(10);
			
			// ktra cả 2 popup đã đóng
			Assert.assertFalse(driver.findElement(firstStepPopup).isDisplayed());
			Assert.assertFalse(driver.findElement(secondStepPopup).isDisplayed());
			
		}
		
		if (firstStepPopupElement2.size() > 0 && firstStepPopupElement2.get(0).isDisplayed()) {
			// nhập email
			driver.findElement(By.cssSelector("input[placeholder='Enter your e-mail address']")).sendKeys(email);
			sleepInSecond(3);
			
			// click ok
			driver.findElement(By.cssSelector("a[data-label='Get the Books']")).click();
			sleepInSecond(4);
			
			// ktra popup xác nhận được hiển thị
			Assert.assertTrue(driver.findElement(secondStepPopup2).isDisplayed());
			sleepInSecond(10);
			
			// ktra cả 2 popup đã đóng
			Assert.assertFalse(driver.findElement(firstStepPopup2).isDisplayed());
			Assert.assertFalse(driver.findElement(secondStepPopup2).isDisplayed());
			
		}
		
		// Nếu như nó ko hiển thị thì qua step tiếp theo
		// search
		driver.findElement(By.cssSelector("input[id='search-input']")).sendKeys("Agile Testing Explained");
		driver.findElement(By.cssSelector("button#search-submit")).click();
		sleepInSecond(3);
		
		// verify
		Assert.assertEquals(driver.findElement(By.cssSelector("h1.page-title>span")).getText(), "Agile Testing Explained");
	}
	
	@Test
	public void TC_02_Random_Popup_In_HTML_VNK() {
		driver.get("https://vnk.edu.vn/");
		sleepInSecond(30);
		
//		// close mess
//		driver.findElement(By.cssSelector("div[aria-label='đóng']")).click();
		
		By popup = By.cssSelector("div#tve_editor");
		
		List<WebElement> popupElement = driver.findElements(popup);
		
		if (popupElement.size() > 0 && popupElement.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector("div.tcb-icon-display")).click();
			sleepInSecond(2);
		}
		
		Assert.assertFalse(driver.findElement(popup).isDisplayed());
		
		// click danh sach khoa hoc
		driver.findElement(By.cssSelector("button.btn-danger")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://vnk.edu.vn/lich-khai-giang/");
	}
	
	@Test
	public void TC_03_() {
		driver.get("https://dehieu.vn/");
		sleepInSecond(10);
		
		By popup = By.cssSelector("div.popup-content");
		
		List<WebElement> popupElement = driver.findElements(popup);
		
		if (popupElement.size() > 0 && popupElement.get(0).isDisplayed()) {
			// nhập thông tin hoặc close popup
//			driver.findElement(By.cssSelector("input#popup-name")).sendKeys("anhttp");
//			driver.findElement(By.cssSelector("input#popup-email")).sendKeys(email);
//			driver.findElement(By.cssSelector("input#popup-phone")).sendKeys("0123456789");
//			driver.findElement(By.cssSelector("button.submit-button")).click();
			// đóng popup
			driver.findElement(By.cssSelector("button#close-popup")).click();
			
			sleepInSecond(3);
		}
		
		// k hiển thị popup
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://dehieu.vn/khoa-hoc");
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