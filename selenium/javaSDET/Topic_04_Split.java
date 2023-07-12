package javaSDET;

public class Topic_04_Split {
	public static void main(String[] args) {
		String url = "http://the-internet.herokuapp.com/basic_auth";
		
		String[] newurl = url.split("//");
		// http:
		// the-internet.herokuapp.com/basic_auth
		url = newurl[0] + "//" + "admin"+ ":" + "admin" + "@" +  newurl[1];
		System.out.println(url);
	}
}
