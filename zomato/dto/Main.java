package zomato.dto;

import java.util.List;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);
	static Zomato zomato=new Zomato();

	public static void main(String[] args) {
		UserSide userSide=new UserSide();
		AdminSide adminSide=new AdminSide();

		for (;;) {
			System.out.println("Choose options >>");
			System.out.println("enter 1.User   2.Admin   0.Exit");
			switch (scanner.nextInt()) {
			case 1:
				userSide.userOptions();
				break;
			case 2:
				adminSide.adminOptions();
				break;
			case 0:
				System.err.println("Application is closed");
				return;				
			default:
				System.out.println("Choose correct Option ?");
			}
		}
	}
}
