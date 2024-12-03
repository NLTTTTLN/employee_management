package employee_management.bean;

public class Employee extends User {
	private Integer employee_id;
    private String department;
    private double salary;
    
    
    
 // Default constructor
    public Employee() {
        super();
    }
    
    // Constructor
    public Employee(Integer employee_id,String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address, String department, double salary) {

    	super(username, name, gender, dob, email, phone_num, address); // Call the User constructor
        this.employee_id = employee_id;
        this.department = department;
        this.salary = salary;
    }
    public Employee(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address, String department, double salary) {
        super(username, name, gender, dob, email, phone_num, address); // Call the User constructor
        this.department = department;
        this.salary = salary;
    }
    
    public Integer getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employee_id = employeeId;
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
