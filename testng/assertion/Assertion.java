package assertion;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Assertion {
	@Test
	public void TC_01() {
		
		// boolean: isDisplayed/ isEnabled/ is selected/ is mutipled/ User-Defined 
		// Assert.assertTrue/ False: thám số nhận vào là boolean
		Assert.assertTrue(isEmailTextboxDisplayed());
		
		// int/ String/ float/...
		// Assert.assertEquals: 2 mong đợi và thực tế như nhau
		Assert.assertEquals(getSuccessMessage(),15);
		
		Object studentNumber = 15;
		
		Assert.assertNull(studentNumber);
		
		Assert.assertNotNull(studentNumber);
	}
	
	public boolean isEmailTextboxDisplayed() {
		// Action
		
		return true;
	}
	public String getSuccessMessage() {
		return "15";
	}
}
