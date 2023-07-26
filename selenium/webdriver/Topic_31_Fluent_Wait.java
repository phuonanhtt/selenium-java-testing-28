package webdriver;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_31_Fluent_Wait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	
	FluentWait<WebDriver> fluentWait;
	
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

		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		
//		driver.findElement(By.cssSelector("div#start>button")).click();
		findWebElement(By.cssSelector("div#start>button")).click();
		
//		fluentWait = new FluentWait<WebDriver>(driver);
//		// set timeout tổng time bằng bn
//		fluentWait.withTimeout(Duration.ofSeconds(5))
//		// polling/ interval time: lặp lại
//		.pollingEvery(Duration.ofMillis(200))
//		// ignore exception nếu k tìm thấy element
//		.ignoring(NoSuchElementException.class);
//		// điều kiện để wait
//		// wait cho element có locator này isDisplayed()
//		// //div[@id='finish']/h4[text()='Hello World!']
		boolean textStatus = isElementDisplayed(By.xpath("//div[@id='finish']/h4[text()='Hello World!']"));
		// verify
		Assert.assertTrue(textStatus);
		
		// wait cho element có cái text -> getText()
		// div#finish>h4
		String helloTextString = getElementText(By.cssSelector("div#finish>h4"));
		// verify
		Assert.assertEquals(helloTextString, "Hello World!");
		
		// Wrapper Class
		// byte - Byte
		// float - Float
		// int - Integer
		// short - Short
		// boolean - Boolean
	}
	
	@Test
	public void TC_02_() {
		driver.get("https://automationfc.github.io/fluent-wait/");
		
		fluentWait = new FluentWait<WebDriver>(driver);
		
		fluentWait.withTimeout(Duration.ofSeconds(15))
		.pollingEvery(Duration.ofSeconds(1))
		.ignoring(NoSuchElementException.class);
		Boolean textStatus = fluentWait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.xpath("//div[@id='javascript_countdown_time' and text()='01:01:00']")).isDisplayed();
			}
		});
		Assert.assertTrue(textStatus);
		
		Boolean textStatus1 = fluentWait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.xpath("//div[@id='javascript_countdown_time']")).getText().equals("01:01:00");
			}
		});
		Assert.assertTrue(textStatus1);
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public WebElement findWebElement(By by) {
		fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.withTimeout(Duration.ofSeconds(30))
		.pollingEvery(Duration.ofSeconds(1))
		.ignoring(NoSuchElementException.class);
		
		 return fluentWait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
	}
	public String getElementText(By by) {
		fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.withTimeout(Duration.ofSeconds(30))
		.pollingEvery(Duration.ofSeconds(1))
		.ignoring(NoSuchElementException.class);
		
		return fluentWait.until(new Function<WebDriver, String>() {
			@Override
			public String apply(WebDriver driver) {
				return driver.findElement(by).getText();
			}
		});
	}
	
	public Boolean isElementDisplayed(By by) {
		fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.withTimeout(Duration.ofSeconds(30))
		.pollingEvery(Duration.ofSeconds(1))
		.ignoring(NoSuchElementException.class);
		
		 return fluentWait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElement(by).isDisplayed();
			}
		});
	}
}