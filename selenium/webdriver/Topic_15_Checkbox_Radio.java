package webdriver;

import java.util.List;
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

public class Topic_15_Checkbox_Radio {
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
	public void TC_01_Checkbox_Default() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		By dualZoneCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
		// CLick checkbox
		if (!driver.findElement(dualZoneCheckbox).isSelected()) {
			driver.findElement(dualZoneCheckbox).click();
			sleepInSecond(2);
		}
		
		// Verify đã được chọn
		Assert.assertTrue(driver.findElement(dualZoneCheckbox).isSelected());
		
		// bỏ check
		if (driver.findElement(dualZoneCheckbox).isSelected()) {
			driver.findElement(dualZoneCheckbox).click();
			sleepInSecond(2);
		}
		
		// Verify chưa được chọn
		Assert.assertFalse(driver.findElement(dualZoneCheckbox).isSelected());
		
	}
	
	@Test
	public void TC_02_Radio_Default() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		
		By dieselTwoDotZero = By.xpath("//label[text()='2.0 Diesel, 103kW']/preceding-sibling::input");
		By petroTwoDotZero = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");
		
		// check
		if (!driver.findElement(petroTwoDotZero).isSelected()) {
			driver.findElement(petroTwoDotZero).click();
			sleepInSecond(2);
		}
		
		// Verify đã được chọn
		Assert.assertTrue(driver.findElement(petroTwoDotZero).isSelected());
		
		// bỏ check
		if (driver.findElement(petroTwoDotZero).isSelected()) {
			driver.findElement(dieselTwoDotZero).click();
			sleepInSecond(2);
		}
		
		// Verify đã được bỏ chọn
		Assert.assertTrue(driver.findElement(dieselTwoDotZero).isSelected());
		Assert.assertFalse(driver.findElement(petroTwoDotZero).isSelected());
		
	}

	@Test
	public void TC_03_Select_All_Checkbox() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		// dùng 1 list element để chứa hết tất cả các checkbox
		List<WebElement> allCheckbox = driver.findElements(By.xpath("//div[@class='form-single-column']//input[@class='form-checkbox']"));
		
		// Click hết toàn bộ
		for (WebElement checkbox : allCheckbox) {
			if (!checkbox.isSelected()) {
				checkbox.click();
				//sleepInSecond(1);
			}
		}
		
		// verify đã được chọn toàn bộ
		for (WebElement checkbox : allCheckbox) {
			Assert.assertTrue(checkbox.isSelected());
		}

	}
	
	@Test
	public void TC_04_Select_Radio_Checkbox_By_Condition() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		// dùng 1 list element để chứa hết tất cả các checkbox
		List<WebElement> allCheckbox = driver.findElements(By.xpath("//div[@class='form-single-column']//input[@class='form-checkbox']"));
		
		// checkbox tên là Gallstones mới click
		for (WebElement checkbox : allCheckbox) {
			if (!checkbox.isSelected() && checkbox.getAttribute("value").equals("Gallstones")) {
				checkbox.click();
				//sleepInSecond(1);
			}
		}
		
		// verify chỉ có check box là Gallstones được chọn
		// verify đã được chọn toàn bộ
		for (WebElement checkbox : allCheckbox) {
			if (checkbox.getAttribute("value").equals("Gallstones")) {
				Assert.assertTrue(checkbox.isSelected());
			}
		}
		
		// lấy tất cả radio của exercise
		List<WebElement> allRadio = driver.findElements(By.xpath("//label[contains(text(),' Exercise ')]/following-sibling::div//input[@class='form-radio']"));
		
		for (WebElement radio : allRadio) {
			if (!radio.isSelected() && radio.getAttribute("value").equals("3-4 days")) {
				radio.click();
			}
		}
		// veriry chỉ có radio 3-4 days được chọn
		for (WebElement radio : allRadio) {
			if (radio.getAttribute("value").equals("3-4 days")) {
				Assert.assertTrue(radio.isSelected());
			}
		}
	}
	
	@Test
	public void TC_05_Radio_Custom() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		
		By registedRadio1 = By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input");
//		By registedRadio2 = By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/div[@class='mat-radio-outer-circle']");
		
		// click vào đăng ký cho người thân
//		driver.findElement(registedRadio2).click();
//		sleepInSecond(2);
		
		// case 1: nếu dùng thẻ input thì k click được + nhưng lại verify được
		// case 2: dùng thẻ khác hiển thị để click + nhưng lại k verify được
		// => case 3: dùng thẻ khác input để click + dùng thẻ input để verify
		// Assert.assertTrue(driver.findElement(registedRadio1).isSelected());
		
		// case 4: vẫn dùng input để click + verify
		// JS nó k qtam element có bị che hay không
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(registedRadio1));
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(registedRadio1).isSelected());
	}
	
	@Test
	public void TC_06_Checkbox_Custom() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
				
		By hpradio = By.xpath("//div[@aria-label='Hải Phòng']");
		By qncheckbox = By.xpath("//div[@aria-label='Quảng Ngãi']");
				
		// verify chưa được chọn
		Assert.assertEquals(driver.findElement(hpradio).getAttribute("aria-checked"), "false");
		Assert.assertEquals(driver.findElement(qncheckbox).getAttribute("aria-checked"), "false");
		
		// click chọn
		driver.findElement(hpradio).click();
		driver.findElement(qncheckbox).click();
		
		// verify đã được chọn chưa
		Assert.assertEquals(driver.findElement(hpradio).getAttribute("aria-checked"), "true");
		Assert.assertEquals(driver.findElement(qncheckbox).getAttribute("aria-checked"), "true");
		
		// list element chứa tất cả các checkbox
		List<WebElement> allCheckboxs = driver.findElements(By.xpath("//div[@role = 'checkbox']"));
		
		for (WebElement checkbox : allCheckboxs) {
			if (checkbox.getAttribute("aria-checked").equals("false")) {
				checkbox.click();
				sleepInSecond(1);
			}
		}
		// verify
		for (WebElement checkbox : allCheckboxs) {
			Assert.assertEquals(checkbox.getAttribute("aria-checked"), "true");
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

}