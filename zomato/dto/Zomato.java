package zomato.dto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zomato {
	ArrayList<Hotel> hotels = new ArrayList();

	public int getHotelSrNo() {
		File file = new File(getPath());
		FileWriter fileWriter = null;
		Scanner scanner = null;

		int srNo = 0;
		String s = "";

		try {
			scanner = new Scanner(file);
			if (scanner.hasNextLine()) {
				s += scanner.next();
			}
			if (s.length() != 0) {
				srNo = Integer.parseInt(s);
			}
			srNo += 1;
			s = srNo + "";
			fileWriter = new FileWriter(file);
			fileWriter.write(s);
			fileWriter.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
				if (scanner != null)
					scanner.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return srNo;

	}

	public void saveHotelName(Hotel newHotel) {
		File file = new File(getFilePath() + "HotelNames.txt");
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter
					.write(newHotel.srNo + " . " + newHotel.name + " " + newHotel.location + " " + newHotel.rating);
			bufferedWriter.newLine();
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
	}

	public void seeHotels() {
		File file = new File(getFilePath() + "HotelNames.txt");
		Scanner scanner = null;

		List<String> hotelNames = new ArrayList();

		String hotelNavBar = "__________________________________________________________________\n\n"
				+ "srNo  Name                Location            Rating\n"
				+ "--------------------------------------------------------------------";

		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				hotelNames.add(scanner.nextLine());
			}
			System.out.println(hotelNavBar);
			for (int i = 0; i < hotelNames.size(); i++) {
				String[] names = hotelNames.get(i).split(" ");
				if (names.length < 5)
					continue;

				String s = "";
				s += names[0] + names[1];
				s = concatSpace(s, 6 - names[1].length() - 1);
				s += names[2];
				s = concatSpace(s, 20 - names[2].length());
				s += names[3];
				s = concatSpace(s, 20 - names[3].length());
				s += names[4];
				s = concatSpace(s, 20 - names[4].length());
				System.out.println(s);

			}
			System.out.println("_______________________________________________________________________");
			System.out.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finally {
			if (scanner != null)
				scanner.close();
		}
	}

	public String concatSpace(String s, int n) {
		for (int i = 0; i < n; i++) {
			s += " ";
		}
		return s;
	}

	public String getHotelName(int n) {
		File file = new File(getFilePath() + "HotelNames.txt");
		Scanner scanner = null;
		;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String s = null;
		for (int i = 0; scanner.hasNextLine(); i++) {
			if ((Integer.parseInt(scanner.next()) == n)) {
				scanner.next();
				s = scanner.next();
			}
			scanner.nextLine();
		}
		return s;
	}

	private String getPath() {
		String path = "C:\\Users\\hp\\Desktop\\File Handling\\Zomato\\SrNo.txt";
		return path;
	}

	public String getFilePath() {
		String path = "C:\\Users\\hp\\Desktop\\File Handling\\Zomato\\";
		return path;
	}

}
