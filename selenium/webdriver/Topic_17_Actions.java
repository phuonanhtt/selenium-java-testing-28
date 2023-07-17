package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Actions {
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
			//System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver114.exe");
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		//driver = new ChromeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_ToolTip() {
		// Action: 1 thư viện dùng để giả lập lại cá hành động của chuột/ bàn phím
		// user interaction
		// khi testscript đang chạy về action thì k được sử dụng chuột và bàn phím
		
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		
		// di chuột tới element trước r click
		// action.click(driver.findElement(By.cssSelector(""))).perform();
		// click bt, k di chuột
		// driver.findElement(By.cssSelector("")).click();
		
		// Hover vào tooltip
		action.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
		sleepInSecond(2);
		
		// firefox: chạy lệnh console: setTimeout(() => {debugger;},3000);
		// hover chuột và dợi tooltip hiện, chờ tới timeout để bắt element
		// verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
		
	}
	
	@Test
	public void TC_02_ToolTip() {
//		driver.get("http://www.myntra.com/");
//		
//		// hover vào kid
//		action.moveToElement(driver.findElement(By.xpath("//a[@data-group='kids']"))).perform();
//		sleepInSecond(2);
//		
//		// click home&bath
//		action.click(driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Home & Bath']"))).perform();
//		sleepInSecond(2);
//		
//		Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
		driver.get("https://www.healthkart.com/");
		
		// hover
	    action.moveToElement(driver.findElement(By.xpath("//div[text()='Customer Support']"))).perform();
	    sleepInSecond(2);
	    
	    // click
		action.click(driver.findElement(By.xpath("//div[text()='Terms & Conditions']"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.xpath("//h1[text()='Terms and Conditions']")).getText(), "Terms and Conditions");
	}
	
	@Test
	public void TC_03_Click_And_Hold() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
//		// 1. click chuột trái vào số 1
//		// 2. vẫn giữ chuột trái
//		action.clickAndHold(driver.findElement(By.xpath("//li[text()='1']")))
//		
//		// 3. kéo chuột tới số kết thúc
//		.moveToElement(driver.findElement(By.xpath("//li[text()='4']")))
//		
//		// 4. nhả chuột trái ra
//		.release()
//		// thực thi các hành động trên
//		.perform();
//		
//		sleepInSecond(4);
//		
//		// verify
//		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='1' and contains(@class,'ui-selected')]")).isDisplayed());
//		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='2' and contains(@class,'ui-selected')]")).isDisplayed());
//		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='3' and contains(@class,'ui-selected')]")).isDisplayed());
//		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='4' and contains(@class,'ui-selected')]")).isDisplayed());
		
		// c2: Nên lưu hết 12 số này lại
		// muốn thao tác với số nào thì lấy ra
		List<WebElement> allNumber = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		
		action.clickAndHold(allNumber.get(0)).moveToElement(allNumber.get(3)).release().perform();
		sleepInSecond(4);
		
		//verify
		
		List<WebElement> numberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(numberSelected.size(), 4);
		
		
	}
	
	@Test
	public void TC_04_Click_And_Selected_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");

		List<WebElement> allNumber = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		
		Keys key = null;
		
		if (osName.contains("Windows")) {
			key = Keys.CONTROL;
		} else {
			key = Keys.COMMAND;
		}
		
		// 1- nhấn phím Ctrl
		action.keyDown(key).perform();
		// 2- click chọn các số
		action.click(allNumber.get(0))
		.click(allNumber.get(2))
		.click(allNumber.get(5))
		.click(allNumber.get(7))
		.click(allNumber.get(9)).perform();
		
		// 3- nhả phím ctr
		action.keyUp(key).perform();
		sleepInSecond(4);
		
		//verify
		List<WebElement> numberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(numberSelected.size(), 5);
	}
	@Test
	public void TC_05_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// click
		// firefox: các element k nằm trong viewport thì thường k có click được
		// Console: var element = $x("//button[text()='Double click me']")[0];
		// 			element.scrollIntoView(true);
		if (driver.toString().contains("Firefox")) {
			jsExecutor.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//button[text()='Double click me']")));
		}
		
		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		sleepInSecond(3);
		
		// verify
		Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
	}
	
	@Test
	public void TC_06_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		
		// click chuột phải
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		sleepInSecond(2);
		
		// hover vào quit
		action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(3);
		
		// verify
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-hover")).isDisplayed());
		
		// click quit
		action.click(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(3);
		
		// accept alert
		driver.switchTo().alert().accept();
		sleepInSecond(3);
		
		// verify quit không được hiển thị
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
	}
	
	@Test
	public void TC_07_Drag_Drop() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		
		// drag/drop không nên làm auto 
		// ngoài ra còn có: captcha / SMS / OTP / bar code / qr code / chart / canvas / face / google / game
		action.dragAndDrop(driver.findElement(By.cssSelector("div#draggable")), driver.findElement(By.cssSelector("div#droptarget"))).perform();
		sleepInSecond(2);
		
		// verify hiển thị
		Assert.assertEquals(driver.findElement(By.cssSelector("div#droptarget")).getText(), "You did great!");
		Assert.assertEquals(Color.fromString(driver.findElement(By.cssSelector("div#draggable")).getCssValue("background-color")).asHex(), "#03a9f4");
		
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