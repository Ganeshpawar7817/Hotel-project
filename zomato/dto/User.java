package zomato.dto;

public class User {
	
	String name;
	String address;
	long phone;

	public User(String name, String address, long phone) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	@Override
	public String toString() {
		String name=this.name;
		String address=this.address;
		long phone=this.phone;
			
		return "name      :" + name + "\naddress   :" + address + "\nphone     :" + phone;
	}
	
}
