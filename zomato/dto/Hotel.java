package zomato.dto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {

	String name;
	String location;
	String rating;
	int srNo = 1;
	ArrayList<Food> foods;
	Scanner scanner = new Scanner(System.in);
	Zomato zomato = new Zomato();

	public Hotel(String name, String location, int srNo) {
		super();
		this.name = name;
		this.location = location;
		this.srNo = srNo;
		this.foods = new ArrayList<Food>();
		
		fooItems();
		boolean b=createHotel();
		
		if(b==true) {
		zomato.saveHotelName(this);
		
		System.out.println("Hotel is saved Successfully ");
		System.out.println();
		}
		else {
			System.out.println("Hotel already present ?");
			System.out.println();
		}

	}

	Hotel() {

	}

	public void fooItems() {
		System.out.println("enter no of food items >>");
		int noOfFoodItems = scanner.nextInt();
		for (int i = 1; i <= noOfFoodItems; i++) {
			System.out.println("enter 'name' of '" + i + "th' food >>");
			String name = scanner.next();
			System.out.println("enter 'price' of '" + i + "th' food >>");
			double price = scanner.nextDouble();
			int srNo = i;
			foods.add(new Food(srNo, name, price));

		}
		System.out.println();
	}

	public static class Food {
		String name;
		double price;
		int quantity;
		int srNo;
		Hotel hotel = new Hotel();

		public Food(int srNo, String name, double price) {

			this.name = name;
			this.price = price;
			this.srNo = srNo;

		}

		public Food(String name, double price) {
			this.name = name;
			this.price = price;
		}


		public int getFoodSrNo() {

			if (hotel.foods == null)
				return 1;
			else
				return hotel.foods.get(hotel.foods.size() - 1).srNo + 1;

		}

		@Override
		public String toString() {
			return "Food [name=" + name + ", price=" + price + "]";
		}

	}

	public boolean createHotel() {
		String folderPath = getHotelsPath() + "\\" + this.name;
		String filePath = folderPath + "\\foods.txt";
		String usersrNoPath = folderPath + "\\UserSrNo.txt";
		String orderListPath = folderPath + "\\OrdersList.txt";

		File folder = new File(getHotelsPath() + "\\" + this.name);
		File foods = new File(filePath);
		File userSrNo = new File(usersrNoPath);
		File orderList = new File(orderListPath);

		if (!folder.exists()) {
			folder.mkdir();
			
		}
		else if(folder.exists()) {
			return false;
		}

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			foods.createNewFile();
			userSrNo.createNewFile();
			orderList.createNewFile();
			fileWriter = new FileWriter(foods, true);
			bufferedWriter = new BufferedWriter(fileWriter);

			List<Food> l = this.foods;
			for (int i = 0; i < l.size(); i++) {
				Food food = l.get(i);
				bufferedWriter.write(food.srNo + ". " + food.name + " " + food.price);
				bufferedWriter.newLine();
			}

			bufferedWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (fileWriter != null)
					fileWriter.close();
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public void showMenuList(String hotelName) {
		File file = new File(getHotelsPath() + "\\" + hotelName + "\\foods.txt");
		Scanner scanner = null;

		String menuNavBar = "______________________________________________________\n\n"
				+ "SrNo. Menu-Name           Menu-price          \n"
				+ "---------------------------------------------------------";
		try {
			scanner = new Scanner(file);

			System.out.println(menuNavBar);

			while (scanner.hasNextLine()) {
				String s = "";
				String line = scanner.nextLine();
				String[] menu = line.split(" ");
				if (menu.length < 3)
					continue;
				s += menu[0];
				s = concatSpace(s, 6 - menu[0].length());
				s += menu[1];
				s = concatSpace(s, 20 - menu[1].length());
				s += menu[2];
				s = concatSpace(s, 20 - menu[2].length());

				System.out.println(s);
			}
			System.out.println("________________________________________________________");
			System.out.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finally {
			if (scanner != null) {
				scanner.close();
			}
		}

	}

	public Food getFood(String hotelName, int n) {
		Food food = null;
		String s = "";
		File file = new File(getHotelsPath() + "\\" + hotelName + "\\" + "foods.txt");
		try {
			Scanner scanner = new Scanner(file);

			for (int i = 1; scanner.hasNextLine(); i++) {
				if (i == n) {
					s = scanner.nextLine();
					break;
				}
				scanner.nextLine();

			}

			if (s.length() > 0) {
				String[] foodString = s.split(" ");
				String name = foodString[1];
				double price = Double.parseDouble(foodString[2]);
				food = new Food(name, price);

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return food;

	}

	public int getUserSrNo(String hotelName) {
		File file = new File(getHotelsPath() + "\\" + hotelName + "\\UserSrNo.txt");
		Scanner scannerF = null;
		FileWriter fileWriter = null;
		int srNo = 0;
		try {
			scannerF = new Scanner(file);
			String s = "";
			int n = 0;

			if (scannerF.hasNextLine()) {
				s += scannerF.next();
				srNo = Integer.parseInt(s);
			}

			srNo += 1;
			s = srNo + "";

			FileReader fileReader = new FileReader(file);
			fileWriter = new FileWriter(file);
			fileWriter.write(s);
			fileWriter.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (scannerF != null)
				scannerF.close();
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}

		return srNo;

	}

	public void saveOrder(String hotelName, List<Food> order, User user) {
		File file = new File(getHotelsPath() + "\\" + hotelName + "\\OrdersList.txt");
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			int srNo = getUserSrNo(hotelName);
			LocalDate localDate = LocalDate.now();
			LocalTime localTime = LocalTime.now();

			bufferedWriter.write("SrNo. " + srNo);
			bufferedWriter.newLine();
			bufferedWriter.write("User Name :-" + user.name);
			bufferedWriter.newLine();
			bufferedWriter.write("address :-" + user.address);
			bufferedWriter.newLine();
			bufferedWriter.write("Date :- " + localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-"
					+ localDate.getYear());
			bufferedWriter.newLine();
			bufferedWriter.write(
					"Time :- " + localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond());
			bufferedWriter.newLine();

			bufferedWriter.write(
					"------------------------------------------------------------------------------------------");
			bufferedWriter.newLine();

			double bill = 0;
			for (int i = 0; i < order.size(); i++) {
				Food food = order.get(i);
				bufferedWriter.write(food.srNo + ".  " + food.name + "  " + food.price + "  " + food.quantity);
				bufferedWriter.newLine();
				bill += (food.price * food.quantity);
			}

			bufferedWriter.write("----------------------------------------------------------------");
			bufferedWriter.newLine();
			bufferedWriter.write("Bill :- " + bill);
			bufferedWriter.newLine();
			bufferedWriter.write(
					"------------------------------------------------------------------------------------------");
			bufferedWriter.newLine();
			bufferedWriter.write(
					"__________________________________________________________________________________________");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			bufferedWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {

			try {
				if (fileWriter != null)
					fileWriter.close();
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void EditHotel(String hotelName) {
		File file = new File(getHotelsPath() + "\\" + hotelName + "\\foods.txt");
		Scanner scannerF = null;
		try {
			scannerF = new Scanner(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getHotelsPath() {
		String path = "C:\\Users\\hp\\Desktop\\File Handling\\Zomato\\Hotels";
		return path;
	}

	public String concatSpace(String s, int n) {
		for (int i = 0; i < n; i++) {
			s += " ";
		}
		return s;
	}

	@Override
	public String toString() {
		String name = this.name;
		String location = this.location;
		String rating = this.rating;
		String s = "";

		return s;
	}

}
