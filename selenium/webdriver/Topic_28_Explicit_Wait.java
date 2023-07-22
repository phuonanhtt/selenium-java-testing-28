package webdriver;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Topic_28_Explicit_Wait {
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
		explicitWait = new WebDriverWait(driver, 15);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_() {
		driver.get("https://www.facebook.com/");
		
		// chờ cho 1 alert xuất hiện ở trong HTML
		// chờ + switch vào luôn
		Alert alert =explicitWait.until(ExpectedConditions.alertIsPresent());//**
		alert.accept();
		
		// chờ cho 1 attribute có value
		// dùng trước hàm getAttribute()
		explicitWait.until(ExpectedConditions.attributeContains(By.cssSelector("input#login_username"),"placeholder","Nhập số điện thoại"));
		explicitWait.until(ExpectedConditions.attributeToBe(By.cssSelector("input#login_username"),"placeholder","Nhập số điện thoại hoặc email"));//*
		
		// dùng để chờ cho 1 element có thể dc click hay ko: button/ checkbox/ radio/ link/ image
		// dùng trước hàm click()
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.fhs-btn-login")));//**
	
		// chờ cho 1 element đã được chọn hay chưa: checkbox/ radio
		// dùng trước hàm isSelected()
		explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("input[name='sex']")));//*
	
		// chờ cho frame xuất hiện và switch vào frame đó
		explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("")));//**
		
		// chờ cho 1 element k còn visible nữa
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("")));//**
	
		// chờ cho nhiều element k còn visible nữa
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector(""))));
		
		// var arguments
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElement(By.cssSelector(""))));
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElement(By.cssSelector("")),driver.findElement(By.cssSelector(""))));
		
		// chờ cho các element nó có tổng số lượng là bn
		// bằng
		explicitWait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input[name='sex']"),3));//*
		// ít hơn
		explicitWait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("input[name='sex']"),3));
		// nhiều hơn
		explicitWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("input[name='sex']"),3));
		
		// lấy ra số lượng element bằng bn
		int radioNumber = driver.findElements(By.cssSelector("")).size();
	
		// thao tác và nó mở ra các tab/ window
		// chờ cho bn window/ tab được xuất hiện
		explicitWait.until(ExpectedConditions.numberOfWindowsToBe(3));
		
		// chờ cho element nó có trong HTML (không cần quan tâm có visible hay không)
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='sex']")));//*
		
		// chờ cho nhiều element nó có trong HTML (không cần quan tâm có visible hay không)
		// dropdown (item)
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[name='sex']")));
		
		// chờ cho element nested presence
		WebElement loginNameTextBoxElement = explicitWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.cssSelector("div.popup-login-content"),By.cssSelector("input#login_username")));
		
		// chờ cho 1 element ko còn trong HTML nữa
		Assert.assertTrue(explicitWait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("input[name='sex']")))));
	
		// trước hàm getText()
		Assert.assertTrue(explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("li.popup-login-tab-login"),"Đăng nhập")));//*
		
		// Dùng trước hàm getTitle()
		explicitWait.until(ExpectedConditions.titleContains("Fahasa.com - FAHASA.COM"));
		explicitWait.until(ExpectedConditions.titleIs("Nhà sách trực tuyến Fahasa.com - FAHASA.COM"));
		
		// Dùng trước hàm getCurentUrl()
		explicitWait.until(ExpectedConditions.urlContains("fahasa.com/"));
		explicitWait.until(ExpectedConditions.urlToBe("https://www.fahasa.com/"));
		
		// chờ cho 1 element được hiển thị
		explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(""))));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(""))); //**
		
		// chờ cho nhiều element được hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector(""))));
		explicitWait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.cssSelector("")),driver.findElement(By.cssSelector(""))));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("")));//**
		
		explicitWait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(By.cssSelector("cha"),By.cssSelector("con")));
		explicitWait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(driver.findElement(By.cssSelector("cha")),By.cssSelector("con")));
		
		
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