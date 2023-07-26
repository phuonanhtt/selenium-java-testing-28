package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_32_Page_Ready_Wait {
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
		explicitWait = new WebDriverWait(driver, 30);
	}

	//@Test
	public void TC_01_Ajax_Loading() {
		// check xem trang đã load xong chưa (console)
		// (window.jQuery != null) && (jQuery.active === 0);
		// document.readyState;
		
		driver.get("https://admin-demo.nopcommerce.com/login");
		// nhập tk
		driver.findElement(By.cssSelector("input#Email")).clear();
		driver.findElement(By.cssSelector("input#Email")).sendKeys("admin@yourstore.com");
		driver.findElement(By.cssSelector("input#Password")).clear();
		driver.findElement(By.cssSelector("input#Password")).sendKeys("admin");
		//login
		driver.findElement(By.cssSelector("button.login-button")).click();
		// C2: //ul[@role='menu']/li/a/p[contains(text(),'Customers')]
		
		Assert.assertTrue(waitForAjaxIconInvisible());
		driver.findElement(By.xpath("//i[contains(@class,'fa-user')]/parent::a/p")).click();
		Assert.assertTrue(waitForAjaxIconInvisible());
		
		driver.findElement(By.xpath("//p[text()=' Customers']")).click();
		Assert.assertTrue(waitForAjaxIconInvisible());
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'content-header')]/h1[contains(text(),'Customers')]")).isDisplayed());
	}
	
	//@Test
	public void TC_02_Page_Ready() {
		driver.get("https://admin-demo.nopcommerce.com/login");
		// nhập tk
		driver.findElement(By.cssSelector("input#Email")).clear();
		driver.findElement(By.cssSelector("input#Email")).sendKeys("admin@yourstore.com");
		driver.findElement(By.cssSelector("input#Password")).clear();
		driver.findElement(By.cssSelector("input#Password")).sendKeys("admin");
		//login
		driver.findElement(By.cssSelector("button.login-button")).click();
		Assert.assertTrue(isPageLoadedSuccess());
		// click dropdown customer
		driver.findElement(By.xpath("//i[contains(@class,'fa-user')]/parent::a/p")).click();
		// click customer
		driver.findElement(By.xpath("//p[text()=' Customers']")).click();
		Assert.assertTrue(isPageLoadedSuccess());
		// verify
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'content-header')]/h1[contains(text(),'Customers')]")).isDisplayed());
	}
	
	@Test
	public void TC_03_Test_Auto_Blog() {
		driver.get("https://blog.testproject.io/");
		// di chuột vào search
		action.moveToElement(driver.findElement(By.cssSelector("section#search-2 input.search-field"))).perform();
		Assert.assertTrue(isPageLoadedSuccess());
		
		// search
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("Selenium");
		driver.findElement(By.cssSelector("section#search-2 span.glass")).click();
		
		// wait hiển thị trang search
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Search Results']")));
		Assert.assertTrue(isPageLoadedSuccess());
		
		// verify kết quả search
		List<WebElement> postTitles = driver.findElements(By.cssSelector("div.post-content>h3>a"));
		for (WebElement title : postTitles) {
			System.out.println(title.getText());
			Assert.assertTrue(title.getText().contains("Selenium"));
		}
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
	public boolean waitForAjaxIconInvisible() {
		return explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#ajaxBusy")));
	}
	public boolean isPageLoadedSuccess() {
//		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
//		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// điều kiện 1
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
		// điều kiện 2
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
}