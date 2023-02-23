package webdriver;

import java.util.Random;
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

public class Topic_11_Textbox_Textarea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	Random rand = new Random();
	
	String emailAddress = "pa" + rand.nextInt(99999) + "@gmail.com";
	String firstName = "Phuong";
	String lastName = "Tran";
	String fullName = firstName + " " + lastName;
	String password = "Luvina@123";
	
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
	public void TC_01_() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
		
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		
		driver.findElement(By.cssSelector("button[title='Register'")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "Thank you for registering with Main Website Store.");
		
		String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information'] /parent::div/following-sibling::div/p")).getText();
		Assert.assertTrue(contactInfo.contains(fullName));
		Assert.assertTrue(contactInfo.contains(emailAddress));
		
		driver.findElement(By.xpath("//a[text()='Account Information']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("firstname")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("lastname")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), emailAddress);

		driver.findElement(By.cssSelector("a.skip-account>span.label")).click();
		driver.findElement(By.cssSelector("a[title='Log Out']")).click();
		
		driver.findElement(By.cssSelector("div.page-title img")).isDisplayed();
		
		Assert.assertTrue(driver.findElement(By.cssSelector("div.page-title img")).isDisplayed());
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