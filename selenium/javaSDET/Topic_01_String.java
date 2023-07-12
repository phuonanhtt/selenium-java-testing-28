package javaSDET;

public class Topic_01_String {
	public static void main(String[] args) {
		boolean a,b;
		
		// AND Nếu 1 trong 2 điều kiện mà sai = sai
		a = true;
		b = true;
		System.out.println(a && b);
		
		a = true;
		b = false;
		System.out.println(a && b);
		
		a = false;
		b = true;
		System.out.println(a && b);
		
		a = false;
		b = false;
		System.out.println(a && b);
		
		// OR Nếu 1 trong 2 điều kiện mà đúng = đúng
		a = true;
		b = true;
		System.out.println(a || b);
		
		a = true;
		b = false;
		System.out.println(a || b);
		
		a = false;
		b = true;
		System.out.println(a || b);
		
		a = false;
		b = false;
		System.out.println(a || b);
	}
}
