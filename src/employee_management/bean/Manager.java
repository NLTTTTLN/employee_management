package employee_management.bean;

public class Manager extends User {
	private Integer manager_id;
    private String department;

    
    
    
 // Default constructor
    public Manager() {
        super();
    }
    
    // Constructor
    public Manager(Integer manager_id,String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address, String department) {

    	super(username, name, gender, dob, email, phone_num, address); // Call the User constructor
        this.manager_id = manager_id;
        this.department = department;
    }
    public Manager(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address, String department) {
        super(username, name, gender, dob, email, phone_num, address); // Call the User constructor
        this.department = department;
    }
    
    public Integer getManagerId() {
        return manager_id;
    }

    public void setManagerId(Integer managerId) {
        this.manager_id = managerId;
    }

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}



	@Override
	public String toString() {
	    return super.toString() + ", Employee [department=" + department ;
	}


    
}
