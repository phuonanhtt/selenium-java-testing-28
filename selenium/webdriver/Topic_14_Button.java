package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Button {
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
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		sleepInSecond(30);
		
		// click tab đăng nhập
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		sleepInSecond(3);
		
		By loginButton = By.cssSelector("button.fhs-btn-login");
		
		// verify button đăng nhập
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());
		
		// verify color button đăng nhập
		// lấy ra mã màu của 1 element
		// chrome/edge: rgb
		// firefox: rgba
		String loginButtonColor = driver.findElement(loginButton).getCssValue("background-color");
		
		// Chuyển từ rgb sang kiểu color
		Color color = Color.fromString(loginButtonColor);
		
		// color có hàm chuyển qua hexa
		// #c92127
		// toUpperCase => viết hoa chữ cái
		String loginButtonHex = color.asHex().toUpperCase();
		Assert.assertEquals(loginButtonHex,"#C92127");
		
		// nhập email/password
		driver.findElement(By.id("login_username")).sendKeys("testButton@gmail.com");
		driver.findElement(By.id("login_password")).sendKeys("12345678");
		
		// verify button đăng nhập
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());
		
		// verify color button đăng nhập
		Assert.assertTrue(Color.fromString(driver.findElement(loginButton).getCssValue("background-color")).asHex().toUpperCase(),"#C92127");
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