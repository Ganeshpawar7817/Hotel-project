package zomato.dto;

import java.util.List;
import java.util.Scanner;

import zomato.dto.Hotel.Food;

public class AdminSide {
	Scanner scanner = new Scanner(System.in);
	Zomato zomato = new Zomato();
	Hotel hotel;

	public void adminOptions() {
		String hotelNavbar = "Name                Location                 Rating";
		System.out.println("1.Add hotel   2.See Hotels  3.Edit Hotel Data  4.Back");
		switch(scanner.nextInt()) {
		case 1:
			System.out.println("enter name of hotel >>");
			String name = scanner.next();
			System.out.println("enter location of hotel >>");
			String location = scanner.next();
			int srNo = zomato.getHotelSrNo();

			hotel = new Hotel(name, location, srNo);
			
			break;
		case 2:
			zomato.seeHotels();
			System.out.println();
			break;
		case 3:
			System.out.println("1. Edit by Name  2.Edit By Id  3.Edit By List");
			break;
		case 4:
			return;
		default:
			System.out.println("Choose correct Option ?");
			return;
			
		}
	}

}
