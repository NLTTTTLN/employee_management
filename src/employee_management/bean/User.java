package employee_management.bean;

public class User {
    private String username;
    private String name;
    private String gender;
    private java.sql.Date dob;
    private String email;
    private String phone_num;
    private String address;
    
    public User() {}

    // Constructor
    public User(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.phone_num = phone_num;
        this.address = address;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public java.sql.Date getDob() {
		return dob;
	}

	public void setDob(java.sql.Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getphone_num() {
		return phone_num;
	}

	public void setphone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", email="
				+ email + ", phone_num=" + phone_num + ", address=" + address + "]";
	}
    
    
}
