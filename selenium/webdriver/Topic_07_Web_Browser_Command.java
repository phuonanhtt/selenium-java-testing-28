package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Web_Browser_Command {
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
	public void TC_01_Browser() {
		// Các command/hàm để tương tác với browser thì nó thông qua biến driver
		// driver
		//đóng tab . Dùng khi handle windows/tab
		driver.close(); //*
		// đóng browser
		driver.quit(); //**
		
		//Tìm 1 element với 1 locator nào đó (id/class/name/css/xpath,..)
		driver.findElement(By.id("")); //**
		
		//Tìm ra nhiều element với 1 locator nào đó
		//Tìm ra tất cả đường link của page hiện tại
		driver.findElements(By.id("//a[@href]")); //**
		
		// mở ra 1 page/link url nào đó
		driver.get("http://live.techpanda.org/"); //**
		
		// lấy ra url của page hiện tại
		// đang đứng ở page nào thì lấy url của page đó
		driver.getCurrentUrl(); //*
		
		//1 - dùng duy nhất 1 step
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/");
		
		//2 - dùng cho nhiều hơn 1 step thì mới khai báo biến
		// code rườm rà, tốn bộ nhớ
		String homeUrl = driver.getCurrentUrl();
		Assert.assertEquals(homeUrl, "http://live.techpanda.org/");
		
		// lấy ra code HTML/CSS/JS của page hiện tại
		driver.getPageSource();
		
		// lấy ra title của page hiện tại
		driver.getTitle(); //*
		
		Assert.assertEquals(driver.getTitle(), "Molbile");
		
		//lấy ra id của tab/ window hiện tại
		driver.getWindowHandle(); //*
		
		// lấy ra tất cả các id của tab/window
		driver.getWindowHandles(); //*
		
		/// cookies
		driver.manage().getCookies();
		driver.manage().deleteAllCookies(); //*
		
		// để chờ cho element xuất hiện trong vòng bao lâu
		// 2 hàm findElement/ findElements
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.DAYS); //**
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.MINUTES);
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		
		// để chờ cho page được load trong vòng bao lâu
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.MINUTES);
		
		// để chờ cho script được load trong vòng bao lâu
		driver.manage().timeouts().setScriptTimeout(15, TimeUnit.MINUTES);
		
		// windows
		driver.manage().window().maximize(); //**
		driver.manage().window().fullscreen();
		
		// set tại 1 vị trí nào đó
		driver.manage().window().setPosition(new Point(200,200));
		
		//set chiều cao/ chiêu rộng cho browser
		driver.manage().window().setSize(new Dimension(1024,768));
		
		//window/ tab
		// frame/ Iframe
		// alert
		driver.switchTo().alert(); //*
		driver.switchTo().frame(1); //*
		driver.switchTo().window(""); //*
		
	}
	@Test
	public void TC_02_Element() {
		// Các command/hàm để tương tác với element thì nó thông qua việc findElement
		driver.findElement(By.xpath(""));
	}
	@Test
	public void TC_03_Tip() {
		// Chia ra gồm 3 nhóm chính
		
		// Nhóm 1 - hàm để tương tác/action
		// tên hàm sẽ thể hiện rõ chức năng của hàm đó
		// không trả về dữ liệu nào ~ void
		// click/ sendKeys / select /...
		// driver.findElement(By.xpath("")).click();
		
		// Nhóm 2 - lấy ra dữ liệu cho mục đích nào đó (step hiện tại/tiếp theo)
		// nó sẽ bắt đầu bằng tiền tố là getXXX
		// sẽ trả về (return) dữ liệu ~ String
		// getText/ getCurrentUrl/ getTitle/ ...
		// dùng để ktra dữ liệu thực tế (actual result) bằng với dữ liệu mong muốn (expected result) 
		// Assert (jUnit/- TestNG-/ AssertJ/Hamcrest/...) ~ thư viện
		
		// Nhóm 3 - kiểm tra dữ liệu
		// dùng để ktra tính đúng đắn của dữ liệu (true/false/equals) → hàm assert
		// Assert (jUnit/- TestNG-/ AssertJ/Hamcrest/...) ~ thư viện
		// bắt đầu bằng tiền tố isXXX
		// sẽ trả về (return) dữ liệu ~ boolean
		// isDisplayed/ isEnabled/ isSelected / ..
		
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