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

public class Payment { // Test class
	
	
	@Test(enabled = false)
	public void User_01_Create_new_User() { // Test method/ TC
		System.out.println("TC01");
	}

	//@Test
	public void User_02_View_Existing_User() {
		System.out.println("TC02");
	}

	@Test (description = "MenuItems_004_Careers - Careers menu Item")
	public void User_03_Update_User() {
		System.out.println("TC03");
	}
	// TMS: Test Management System - Jira/ TestLink/ TestRail/ ..
	@Test(description = "JIRA #8456 - move user to group")
	public void User_04_Move_User() {
		System.out.println("TC04");
	}

	@Test
	public void User_05_Delete_User() {
		System.out.println("TC05");
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
