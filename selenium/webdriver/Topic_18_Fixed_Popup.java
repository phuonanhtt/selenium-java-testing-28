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

public class Topic_18_Fixed_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;
	String user, pass, mail;

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
		
		user = "phanhddd";
		pass = "123456";
		mail = "phanh@gmail.com";

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_Fixed_Popup_In_HTML_NN() {
		driver.get("https://ngoaingu24h.vn/");
		
		// Luôn có trong HTML (dù có hay không hiển thị)
		// sử dụng hàm isDisplayed()
		
		// click đăng nhập
		driver.findElement(By.cssSelector("button.login_")).click();
		sleepInSecond(2);
		
		// Luôn có trong html dù có hiển thị hay không
		By loginPopup = By.cssSelector("div[id='modal-login-v1'][style]>div.modal-dialog");
		
		//verify
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		// nhập thông tin
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] input#account-input")).sendKeys(user);
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] input#password-input")).sendKeys(pass);
		
		// click đăng nhập
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] button.btn-login-v1")).click();
		sleepInSecond(3);
		
		// verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] div.error-login-panel")).getText(), "Tài khoản không tồn tại!");
		
		// đóng popup
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] button.close")).click();
		
		// isDisplayed()
		// element k hiển thị nhưng vẫn có trong HTML => false
		// element hiển thị => true
		// element k hiển thị và không có trong HTML => k chạy đc (do fail ở hàm findElement())
		
		// verify đã đóng popup
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

	}
	
	//@Test
	public void TC_02_Fixed_Popup_In_HTML_Kyna() {
		driver.get("https://skills.kynaenglish.vn/");
		
		By loginPopup2 = By.cssSelector("div#k-popup-account-login-mb div.k-popup-account-mb-content");
		
		// click đăng nhập
		driver.findElement(By.cssSelector("a.login-btn")).click();
		
		// verify
		Assert.assertTrue(driver.findElement(loginPopup2).isDisplayed());
		
		// nhập thông tin
		driver.findElement(By.cssSelector("input#user-login")).sendKeys(mail);
		driver.findElement(By.cssSelector("input#user-password")).sendKeys(pass);
		
		// click login
		driver.findElement(By.cssSelector("button#btn-submit-login")).click();
		sleepInSecond(3);
		
		// verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");
		
		// đóng popup
		driver.findElement(By.cssSelector("button.k-popup-account-close")).click();
		
		// verify popup không được hiển thị
		Assert.assertFalse(driver.findElement(loginPopup2).isDisplayed());
	}
	
	//@Test
	public void TC_03_Fixed_Popup_Not_In_HTML_Tiki() {
		driver.get("https://tiki.vn/");
		sleepInSecond(3);
		
		By loginPopup = By.cssSelector("div.ReactModal__Content");
		
		// click tk
		driver.findElement(By.xpath("//span[text()='Tài khoản']")).click();
		sleepInSecond(2);
		
		// verify hiển thị
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		// Click dky bằng email
		driver.findElement(By.cssSelector("p.login-with-email")).click();
		sleepInSecond(2);
		
		// k nhập =>click login
		driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
		sleepInSecond(2);
		
		// verify error
		Assert.assertEquals(driver.findElement(By.xpath("//input[@name='email']/parent::div/following-sibling::span")).getText(),"Email không được để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@type='password']/parent::div/following-sibling::span")).getText(),"Mật khẩu không được để trống");
		
		// close popup
		driver.findElement(By.cssSelector("img.close-img")).click();
		sleepInSecond(2);
		
		// verify popup close
		// dùng findElements() để tìm
		// nếu tìm thấy thì trả về 1 list element
		// k tìm thấy thì trả về list rỗng (k đánh fia TC)
		Assert.assertEquals(driver.findElements(loginPopup).size(), 0);
	}
	@Test
	public void TC_04_Fixed_Popup_Not_In_HTML_FB() {
		driver.get("https://www.facebook.com/");
		
		// click dky
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		sleepInSecond(3);
		
		By regPopup = By.xpath("//div[text()='Sign Up']/parent::div/parent::div");
		
		// ktra hiển thị popup
		Assert.assertTrue(driver.findElement(regPopup).isDisplayed());
		
		// close popup
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		sleepInSecond(2);
		
		// verify k hthi
		Assert.assertEquals(driver.findElements(regPopup).size(),0);
		
		
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