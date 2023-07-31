package dependTestcase;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Depends {
	// Chỉ apply cho những TC chạy theo luồng/ có phụ thuộc dl của nhau
	
	@Test
	public void TC_01_Create_New_Product() {
		System.out.println("Create_New_Product");
		
		Assert.assertTrue(false);
	}
	
	@Test(dependsOnMethods = "TC_01_Create_New_Product")
	public void TC_02_View_Existing_Product() {
		System.out.println("View_Existing_Product");
	}
	
	@Test(dependsOnMethods = "TC_02_View_Existing_Product")
	public void TC_03_Move_Product_To_Catagory() {
		System.out.println("Move_Product_To_Catagory");
	}
	
	@Test(dependsOnMethods = {"TC_03_Move_Product_To_Catagory","TC_01_Create_New_Product"})
	public void TC_04_Edit_Product() {
		System.out.println("Edit_Product");
	}
	@Test(dependsOnMethods = "TC_04_Edit_Product")
	public void TC_05_Delete_Product() {
		System.out.println("Delete_Product");
	}
}
