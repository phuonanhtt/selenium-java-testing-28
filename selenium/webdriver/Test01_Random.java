package webdriver;

import java.util.Random;

public class Test01_Random {
	public static void main (String[] arhs) {
		Random rand = new Random();
		
		String emailAddress = "anhttp" + rand.nextInt(99999) + "@gmail.com";
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextDouble());
		System.out.println(rand.nextBoolean());
		System.out.println(rand.nextFloat());
		System.out.println(emailAddress);
		
		String firstName = "Phuong Anh";
		String lastName = "Tran";
		String fullName = firstName + " " + lastName;
		System.out.println(fullName);

	}
}
