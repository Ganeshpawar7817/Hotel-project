package zomato.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import zomato.dto.Hotel.Food;

public class UserSide {

	Scanner scanner = new Scanner(System.in);
	Zomato zomato = new Zomato();
	Hotel hotel = new Hotel();

	public void userOptions() {
		String hotelNavbar = "Name                Location                 Rating";
		System.out.println("1.Choose hotel  2.Back");
		int option = scanner.nextInt();
		if (option == 1) {
			chooseHotel();

		} else if (option == 2)
			return;
		else {
			System.out.println("Choose correct Option ?");
			return;
		}

	}

	public void chooseHotel() {
		zomato.seeHotels();
		System.out.println();
		System.out.println("enter your choise >>");
		int choise = scanner.nextInt();
		if (choise < 1) {
			System.out.println("enter correct choise >>");
			chooseHotel();
		}
		String hotelName = zomato.getHotelName(choise);
		if (hotelName == null) {
			System.out.println("Choose correct opton ?");
			chooseHotel();
		}

		System.out.println("1.Make Order    2.Back");

		if (scanner.nextInt() == 1) {
			makeOrder(hotelName);
		
		}

		else
			return;

	}

	public void makeOrder(String hotelName) {
		boolean show = true;
		List<Food> order = new ArrayList();
		int sr = 1;
		System.out.println("enter your name >>");
		String name = scanner.next();
		System.out.println("enter your address >>");
		String address = scanner.next();
		System.out.println("enter your contact No.");
		long phone = scanner.nextLong();
		User user = new User(name, address, phone);

		while (show == true) {
			hotel.showMenuList(hotelName);
			System.out.println("1.Choose menu  2.Make Order 3.see Order  4.Edit Order  5.Cancel Order");
			switch (scanner.nextInt()) {
			case 1:
				Food food;
				System.out.println("enter item no.");
				int n = scanner.nextInt();
				food = hotel.getFood(hotelName, n);
				if (food == null) {
					System.out.println("Choose correct Option ??");
					continue;
				}

				if (food != null) {
					System.out.println("enter quantity of food >>");
					food.quantity = scanner.nextInt();
					food.srNo = order.size() + 1;
					order.add(food);

				} else
					System.out.println("Choose correct option ?");
				System.out.println();

				for (int i = 0; i < order.size(); i++) {
					Food f = order.get(i);
				}
				break;
			case 2:
				if (order.size() < 1) {
					System.out.println("Please add items first ?");
					System.out.println();
					continue;
				}
				boolean transaction = makeOrder(order);
				System.out.println(transaction);
				if (transaction == true) {
					hotel.saveOrder(hotelName, order, user);
					System.out.println("Order successfull !");
					System.out.println("Thank You !! Visit Again ...)");
					System.out.println();
					return;
				}

				break;
			case 3:
				if(order.size()==0) {
					System.out.println("Order not made ?");
					System.out.println();
					break;
				}
				ShowOrder(order);
				break;
			case 4:
				if(order.size()==0) {
					System.out.println("Order not made ?");
					System.out.println();
					break;
				}
				order=editOrder(order);
				break;
			case 5:
				order = null;
				System.out.println("Your order is cancelled !");
				System.out.println();
				return;
			default:
				System.out.println("enter valid option ?");
				System.out.println();

			}
			System.out.println();
		}
	}

	public boolean makeOrder(List<Food> order) {
		
		double total=ShowOrder(order);
		
		System.out.println("please make payment");
		double payment=scanner.nextDouble();
		if (payment < total) {
			boolean b = false;
			while (b != true) {
				System.out.println("Soory bill amount is more >>");
				System.out.println("1.Make bill  2.Back");
				int billOption = scanner.nextInt();
				if (billOption == 1) {
					System.out.println("pay amount >>");
					if (scanner.nextDouble() >= total) {

						return true;
					}
				} else if (billOption == 2)
					return false;

				else {
					System.out.println("Choose correct option ??");
					System.out.println();
					continue;
				}
			}

		}
		else if (payment >= total) {

			return true;
		}
		 
			return false;

	}
	
	public double ShowOrder(List<Food>order) {
		
	    double payment=0;
		
		String foodOrderNav = "SrNo.   Dish-Name           price          quantity-ordered    Total\n"
				+ "------------------------------------------------------------------------------------";
		System.out.println(foodOrderNav);
		double total=0;
		for (int i = 0; i < order.size(); i++) {
			total = 0;
			Food food = order.get(i);
			String s = "";
			s += food.srNo + ".";

			int sn = food.srNo + " ".length();
			s = concatSpace(s, 8 - sn);
			s += food.name;

			s = concatSpace(s, 20 - food.name.length());
			s += food.price;
			String p = food.price + "";

			s = concatSpace(s, 20 - p.length());
			s += food.quantity;

			s = concatSpace(s, 20 - food.quantity + "".length());
			s += (food.quantity * food.price);
			System.out.println(s);

			total += food.price * food.quantity;
			payment+=total;

		}
		System.out.println("------------------------------------------------------------------------------------");
		String bill = "";
		bill = concatSpace(bill, 48);
		bill += "Total Bill >>";
		bill = concatSpace(bill, 7);
		bill += payment;
		System.out.println(bill);

		System.out.println("_____________________________________________________________________________________");
		System.out.println();
		return payment;
		
	}
	
	List<Food> editOrder(List<Food>order) {
		ShowOrder(order);
		
		System.out.println("1.Edit By SrNo.  2.Cancel Order  3.Back");
		switch(scanner.nextInt()) {
		case 1:
			System.out.println("enter srNo.");
			int index=scanner.nextInt();
			
			Food food=null;
			for(int i=0;i<order.size();i++) {
				if(order.get(i).srNo==index) {
					food=order.get(i);
					break;
				}
			}
			if(food==null) {
				System.out.println("choose correct option ??");
				System.out.println();
				editOrder(order);
				break;
			}
			
			System.out.println("1.Edit Quantity  2.Cancel Item ");
			int option=scanner.nextInt();
			if(option==1) {
				System.out.println("enter new quantity >>");
				food.quantity=scanner.nextInt();
			}
			else if(option==2) {
					order.remove(index-1);
				}
			else {
				System.out.println("Choose correct Option ??");
				System.out.println();
				editOrder(order);
			}
			System.out.println("Order is edited !");
			System.out.println();
			break;
		case 2:
			order.clear();
			System.out.println("Your Order Cancelled !");
			System.out.println();
			break;
		case 3:
			break;
		}
		
		return order;
	}

	public void placerder(String hotelName) {

	}

	public String concatSpace(String s, int n) {
		for (int i = 0; i < n; i++) {
			s += " ";
		}

		return s;
	}

}
