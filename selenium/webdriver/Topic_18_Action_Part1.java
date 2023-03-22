package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_Action_Part1 {
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
	public void TC_01_ToolTip() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		// Di chuột tới element r click
		// action.click(driver.findElement(By.cssSelector("")));
		action.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
	}
	
	@Test
	public void TC_02_ToolTip() {
		driver.get("https://www.healthkart.com/");
		action.moveToElement(driver.findElement(By.cssSelector("div.container-item.support"))).perform();
		sleepInSecond(2);
		
		action.click(driver.findElement(By.xpath("//div[contains(@class,'container-item')]//a[text()='Terms & Conditions']"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "Terms and Conditions");
		
	}
	
	@Test
	public void TC_03_ClickAndHold () {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		/*
		// 1. click chuột trái vào số 1 số bắt đầu
		// 2. vẫn giữ chuột trái
		action.clickAndHold(driver.findElement(By.xpath("//li[text()='1']")))
		// 3. kéo/move chuột tới số kết thúc
		.moveToElement(driver.findElement(By.xpath("//li[text()='4']")))
		//4. nhả chuột trái ra
		.release()
		// 5. thực thi các action trên
		.perform();
		
		sleepInSecond(5);
		
		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='1' and contains(@class, 'ui-selected')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='2' and contains(@class, 'ui-selected')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='3' and contains(@class, 'ui-selected')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[text()='4' and contains(@class, 'ui-selected')]")).isDisplayed());
		*/
		//nên lưu hết tất cả 12 số này lại
		// muốn thao tác vs số snaof thì lôi số đó ra để thao tác
		
		List<WebElement> allNum = driver.findElements(By.cssSelector("ol#selectable>li"));
		
		action.clickAndHold(allNum.get(0)).moveToElement(allNum.get(3)).release().perform();
		
		List<WebElement> numberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(numberSelected.size(), 4);
	}
	@Test
	public void TC_04_ClickAndHold_Random () {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> allNum = driver.findElements(By.cssSelector("ol#selectable>li"));
		
		//1. nhấn phím ctrl xuống
		action.keyDown(Keys.CONTROL).perform();
		
		//2. click chọn các số
		action.click(allNum.get(0))
		.click(allNum.get(2))
		.click(allNum.get(5))
		.click(allNum.get(7))
		.click(allNum.get(9)).perform();
		
		sleepInSecond(2);
		
		List<WebElement> numberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(numberSelected.size(), 5);
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