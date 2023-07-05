package webdriver;

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

public class Topic_10_WebElement_Exercise {
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

	//@Test
	public void TC_01_Displayed() {
		// truy cập
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement email = driver.findElement(By.xpath("//input[@name='user_email']"));
		WebElement age = driver.findElement(By.xpath("//label[text()='Under 18']"));
		WebElement education = driver.findElement(By.xpath("//textarea[@name='user_edu']"));
		
		if (email.isDisplayed()) {
			email.sendKeys("anhttp@gmail.com");
			System.out.println("Email is displayed");
		} else {
			System.out.println("Email is not displayed");
		}
		if (age.isDisplayed()) {
			age.click();
			System.out.println("Age under 18 is displayed");
		} else {
			System.out.println("Age under 18 is not displayed");
		}
		if (education.isDisplayed()) {
			education.sendKeys("College");
			System.out.println("Education is displayed");
		} else {
			System.out.println("Education is not displayed");
		}
		
		if (driver.findElement(By.xpath("//h5[text()='Name: User5']")).isDisplayed()) {
			System.out.println("Name user 5 is displayed");
		} else {
			System.out.println("Name user 5 is not displayed");
		}
	}
	
	//@Test
	public void TC_02_Enabled() {
		// truy cập
		driver.get("https://automationfc.github.io/basic-form/index.html");

		if (driver.findElement(By.id("mail")).isEnabled()) {
			System.out.println("Email is Enable");
		} else {
			System.out.println("Email is Disable");
		}
		
		if (driver.findElement(By.xpath("//label[text()='Under 18']")).isEnabled()) {
			System.out.println("Age under 18 is Enable");
		} else {
			System.out.println("Age under 18 is Disable");
		}
		
		if (driver.findElement(By.id("edu")).isEnabled()) {
			System.out.println("Education is Enable");
		} else {
			System.out.println("Education is Disable");
		}
		
		if (driver.findElement(By.id("job1")).isEnabled()) {
			System.out.println("Job1 is Enable");
		} else {
			System.out.println("Job1 is Disable");
		}
		
		if (driver.findElement(By.id("job2")).isEnabled()) {
			System.out.println("Job2 is Enable");
		} else {
			System.out.println("Job2 is Disable");
		}
		
		if (driver.findElement(By.id("development")).isEnabled()) {
			System.out.println("Interest development is Enable");
		} else {
			System.out.println("Interest development is Disable");
		}
		
		if (driver.findElement(By.id("slider-1")).isEnabled()) {
			System.out.println("Slider1 is Enable");
		} else {
			System.out.println("Slider1 is Disable");
		}
		
		if (driver.findElement(By.id("disable_password")).isEnabled()) {
			System.out.println("Password is Enable");
		} else {
			System.out.println("Password is Disable");
		}
		
		if (driver.findElement(By.xpath("//label[text()='Radio button is disabled']")).isEnabled()) {
			System.out.println("Age radio is Enable");
		} else {
			System.out.println("Age radio is Disable");
		}
		
		if (driver.findElement(By.xpath("//textarea[@name='user_bio']")).isEnabled()) {
			System.out.println("Biography is Enable");
		} else {
			System.out.println("Biography is Disable");
		}
		if (driver.findElement(By.id("job3")).isEnabled()) {
			System.out.println("Job3 is Enable");
		} else {
			System.out.println("Job3 is Disable");
		}
		
		if (driver.findElement(By.xpath("//label[text()='Checkbox is disabled']")).isEnabled()) {
			System.out.println("Interest development is Enable");
		} else {
			System.out.println("Interest development is Disable");
		}
		
		if (driver.findElement(By.id("slider-2")).isEnabled()) {
			System.out.println("Slider2 is Enable");
		} else {
			System.out.println("Slider2 is Disable");
		}
	}
	
	//@Test
	public void TC_03_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// click chọn
		driver.findElement(By.xpath("//input[@id='under_18']")).click();
		driver.findElement(By.xpath("//input[@id='java']")).click();
		sleepInSecond(2);
		
		// ktra đã được check chưa
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='under_18']")).isSelected());
		Assert.assertTrue(driver.findElement(By.id("java")).isSelected());
		
		//in ra console
		if (driver.findElement(By.xpath("//input[@id='under_18']")).isSelected()) {
			System.out.println("Age under 18 is selected");
		} else {
			System.out.println("Age under 18 is not selected");
		}
		
		if (driver.findElement(By.xpath("//input[@id='java']")).isSelected()) {
			System.out.println("Java is selected");
		} else {
			System.out.println("Java is not selected");
		}
		
		//bỏ chọn java
		driver.findElement(By.xpath("//input[@id='java']")).click();
		sleepInSecond(2);
		
		Assert.assertFalse(driver.findElement(By.id("java")).isSelected());
		
		if (driver.findElement(By.xpath("//input[@id='java']")).isSelected()) {
			System.out.println("Java is selected");
		} else {
			System.out.println("Java is not selected");
		}
	}
	
	@Test
	public void TC_04_Mailchimp() {
		driver.get("https://login.mailchimp.com/signup/");
		
		driver.findElement(By.id("create-account-enabled")).click();
		sleepInSecond(1);
		
		// verify singup button is enable
		Assert.assertTrue(driver.findElement(By.id("create-account-enabled")).isEnabled());
		
		//verify message
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='email']/following-sibling::span")).getText(), "An email address must contain a single @.");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='new_username']/following-sibling::span")).getText(), "Please enter a value");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']/span")).getText(), "One lowercase character");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']/span")).getText(), "One uppercase character");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='number-char not-completed']/span")).getText(), "One number");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='special-char not-completed']/span")).getText(), "One special character");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='8-char not-completed']/span")).getText(), "8 characters minimum");
		
		//nhập email hợp lệ
		driver.findElement(By.cssSelector("input#email")).sendKeys("anhttp@gmail.com");
		
		// TH chỉ nhập số
		driver.findElement(By.cssSelector("input#new_password")).sendKeys("123");
		driver.findElement(By.id("create-account-enabled")).click();
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());
		
		// TH nhập chữ thường
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.cssSelector("input#new_password")).sendKeys("abc");
		driver.findElement(By.id("create-account-enabled")).click();
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());
		
		// TH nhập chữ hoa
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.cssSelector("input#new_password")).sendKeys("BCD");
		driver.findElement(By.id("create-account-enabled")).click();
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());
		
		// TH nhập ký tự đặc biệt
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.cssSelector("input#new_password")).sendKeys("@#$");
		driver.findElement(By.id("create-account-enabled")).click();
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());
		
		// TH nhập > 7 ký tự
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.cssSelector("input#new_password")).sendKeys("12345678");
		driver.findElement(By.id("create-account-enabled")).click();
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char completed']")).isDisplayed());
		
		// TH nhập thỏa mãn
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.cssSelector("input#new_password")).sendKeys("Abcd123@");
		driver.findElement(By.id("create-account-enabled")).click();
		sleepInSecond(1);
		
		Assert.assertFalse(driver.findElement(By.cssSelector("li.number-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.cssSelector("li.lowercase-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.cssSelector("li.uppercase-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.cssSelector("li.special-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.cssSelector("li[class='8-char completed']")).isDisplayed());
		
		// click check box
		driver.findElement(By.id("marketing_newsletter")).click();
		sleepInSecond(2);
		//ktra đã check
		Assert.assertTrue(driver.findElement(By.id("marketing_newsletter")).isSelected());
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