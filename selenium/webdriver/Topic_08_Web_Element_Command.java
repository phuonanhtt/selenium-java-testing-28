package webdriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Web_Element_Command {
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
	public void TC_01_WebElement() {
		// Element: textbox/ textarea/ dropdown/ checkbox/ radio/ link/ text/...
		
		//1- chỉ tương tác lên element này 1 lần (k khai báo biến)
		driver.findElement(By.id("send2")).click();
		
		//2- element dùng lại nhiều lần trong trang hiện tại(khai báo biến)
		WebElement loginButton = driver.findElement(By.id("send2"));
		loginButton.isDisplayed();
		loginButton.click(); //**
		
		List <WebElement> textboxes =  driver.findElements(By.xpath("//input[@type='text']"));
		
		// xóa 1 dữ liệu trong textbox/ text area/ dropdown (editable)
		driver.findElement(By.id("send2")).clear();//**
		
		// nhập dữ liệu vào textbox/ text area/ dropdown (editable)
		driver.findElement(By.id("send2")).sendKeys("");//**
		
		// lấy giá trị của thuộc tính
		driver.findElement(By.id("search")).getAttribute("placeholder");//**
		
		//GUI: Font/size/color/position/location/..
		// ưu tiên thấp - ít apply để làm auto
		//lấy giá trị thuộc tính css
		driver.findElement(By.id("search")).getCssValue("font-size");//*
		
		// kích thước của element: cao/ rộng
		Dimension loginButtonSize = driver.findElement(By.id("search")).getSize();
		
		// lấy ra tọa độ bên ngoài svs độ phân giải MH
		Point loginButtonLocation = driver.findElement(By.id("search")).getLocation();
		
		Rectangle loginButtonRect = driver.findElement(By.id("search")).getRect();
		loginButtonSize = loginButtonRect.getDimension();
		loginButtonLocation = loginButtonRect.getPoint();
		
		// report html + take screenshot
		File screenshortFile = driver.findElement(By.id("search")).getScreenshotAs(OutputType.FILE);
		String screenshortBase64 = driver.findElement(By.id("search")).getScreenshotAs(OutputType.BASE64);
		
		// láy ra tên thẻ khi dùng các loại selector mà k biết trước tên thẻ là gì 
		driver.findElement(By.cssSelector("#search")).getTagName();
		
		/// lấy ra text của chính nó và các thẻ con của nó
		String benefitText = driver.findElement(By.cssSelector("ul.benefits")).getText();//**
		
		// áp dụng cho tất cả các element
		//1 element có hiển thị trên mh hay không
		// nhìn thấy dc, có kích thước cụ thê
		driver.findElement(By.cssSelector("ul.benefits")).isDisplayed();//**
		
		// áp dụng cho tất cả các element
		//1 element có thao tác lên được hay không (k bị read-only)
		// nhìn thấy dc, có kích thước cụ thê
		driver.findElement(By.cssSelector("ul.benefits")).isEnabled();
		
		// áp dụng cho 3 loại element: checkbix/ radio/ dropdown (select)
		// 1 element đc chọn r hay chưa
		driver.findElement(By.cssSelector("ul.benefits")).isSelected();//*
		
		//chỉ apply cho cái form/ element trong form
		driver.findElement(By.cssSelector("ul.benefits")).submit();
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