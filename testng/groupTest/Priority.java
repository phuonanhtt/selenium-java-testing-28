package groupTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class Priority { // Test class
	@Test (groups = {"pay","other"})
	public void TC_02() { // Test method/ TC
		System.out.println("TC02");
	}

	@Test (groups = "pay")
	public void TC_01() {
		System.out.println("TC01");
	}
	@Test (groups = "pay")
	public void TC_03() {
		System.out.println("TC03");
	}
	@Test(groups = "pay")
	public void TC_04() {
		System.out.println("TC04");
	}
	@Test(groups = "pay")
	public void TC_05() {
		System.out.println("TC05");
	}
	@Test(groups = "pay")
	public void TC_06() {
		System.out.println("TC06");
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("Before Class");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("After Class");
	}

}
