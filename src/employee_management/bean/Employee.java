package employee_management.bean;

public class Employee extends User {
    private String department;
    private double salary;
    
    
 // Default constructor
    public Employee() {
        super();
    }
    
    // Constructor
    public Employee(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address, String department, double salary) {
        super(username, name, gender, dob, email, phone_num, address); // Call the User constructor
        this.department = department;
        this.salary = salary;
    }

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
	    return super.toString() + ", Employee [department=" + department + ", salary=" + salary + "]";
	}


    
}
