package employee_management.bean;

public class Manager extends User {
	private Integer manager_id;


    
    
    
 // Default constructor
    public Manager() {
        super();
    }
    
    // Constructor
    public Manager(Integer manager_id,String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address) {

    	super(username, name, gender, dob, email, phone_num, address); // Call the User constructor
        this.manager_id = manager_id;

    }
    public Manager(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address) {
        super(username, name, gender, dob, email, phone_num, address); // Call the User constructor

    }
    
    public Integer getManagerId() {
        return manager_id;
    }

    public void setManagerId(Integer managerId) {
        this.manager_id = managerId;
    }





	@Override
	public String toString() {
	    return super.toString() ;
	}


    
}
