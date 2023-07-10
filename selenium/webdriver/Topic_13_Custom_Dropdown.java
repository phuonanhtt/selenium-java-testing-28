package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Custom_Dropdown {
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
		
		explicitWait = new WebDriverWait(driver, 30);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_00_Facebook() {
		driver.get("https://www.facebook.com/reg");
		// Custom dropdown 
		// UnexpectedTagnameException - when element is not a SELECT
		// thẻ div/span/ul/li/...: cha /con
		// dev tùy biến từ framework họ sử dụng: JQuery/ Bootstrap/ Angular/ React
		
		
		// Hành vi:
		// Click vào 1 thẻ nào đó để cho nó xổ hết các item ra
		// chờ cho tất cả các item được load ra hết
		// nếu item mình cần chọn có hiển thị => thực hiện chọn
		// nếu item cần chọn chưa hiển thị thì cần scroll xuống cho đến khi thấy => click chọn
		
		// 1 - Vòng lặp: for
		// 2 - Điều kiện: if
		// 3 - break
		// 4 - Wait: explicit wait
		// 5 - JS Executor: scroll xuống
		
	}
	@Test
	public void TC_01_JQuery() {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
	
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "17");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "17");
		
		selectItemInCustomDropdown("//span[@id='speed-button']", "//ul[@id='speed-menu']/li/div", "Fast");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='speed-button']/span[@class='ui-selectmenu-text']")).getText(), "Fast");
		
		selectItemInCustomDropdown("//span[@id='files-button']", "//ul[@id='files-menu']/li[@class='ui-menu-item']/div", "Some other file with a very long option text");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='files-button']/span[@class='ui-selectmenu-text']")).getText(), "Some other file with a very long option text");
		
		selectItemInCustomDropdown("//span[@id='salutation-button']", "//ul[@id='salutation-menu']/li[@class='ui-menu-item']/div", "Mrs.");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='salutation-button']/span[@class='ui-selectmenu-text']")).getText(), "Mrs.");
		
		
	}
	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		// lưu ý lấy đến element chứa text
		selectItemInCustomDropdown("//div[@role='listbox']", "//div[@role='option']/span", "Elliot Fu");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Elliot Fu");
		
	}
	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdown("//div[@class='btn-group']", "//ul[@class='dropdown-menu']/li/a", "Second Option");
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");
	}
	@Test
	public void TC_04_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		selectItemInEditableDropDown("//input[@class='search']", "//div[@role='option']//span", "Belgium");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Belgium");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void selectItemInCustomDropdown(String xPathParent, String xPathChild, String expectedText) {
		// click vào 1 thẻ nào đó để cho nó xổ hết các item ra
		driver.findElement(By.xpath(xPathParent)).click();
		sleepInSecond(1);
		
		// chờ cho tất cả các item được load ra hết => trong vòng 30s
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xPathChild)));
				
		// lấy hết tất cả các item trong dropdown lưu vào list
		List<WebElement> allItems = driver.findElements(By.xpath(xPathChild));
		// duyệt qua từng item
		for (WebElement tempElement : allItems) {
			if (tempElement.getText().equals(expectedText)) {
				// scroll tới element
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tempElement);
				sleepInSecond(1);
				// click vào
				tempElement.click();
				// thoát khỏi vòng lặp
				break;
			}
		}
		// duyệt qua từng item
		// c1
		/* for (int i = 0; i < allItems.size(); i++) {
			// get text của từng item
			String itemText = allItems.get(i).getText();
			
			// ktra text đúng với cái mình cần chọn
			if (itemText.equals("5")) {
				
				// click vào
				allItems.get(i).click();
				
				// thoát khỏi vòng lặp
				break;
			}
		} */
		// c2 Foreach
	}
	
	public void selectItemInEditableDropDown(String xPathTextbox, String xPathChild, String expectedText) {
		// click vào 1 thẻ nào đó để cho nó xổ hết các item ra
		driver.findElement(By.xpath(xPathTextbox)).sendKeys(expectedText);;
		sleepInSecond(1);
		
		// chờ cho tất cả các item được load ra hết => trong vòng 30s
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xPathChild)));
				
		// lấy hết tất cả các item trong dropdown lưu vào list
		List<WebElement> allItems = driver.findElements(By.xpath(xPathChild));
		// duyệt qua từng item
		for (WebElement tempElement : allItems) {
			if (tempElement.getText().equals(expectedText)) {
				// scroll tới element
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tempElement);
				sleepInSecond(1);
				// click vào
				tempElement.click();
				// thoát khỏi vòng lặp
				break;
			}
		}
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}