package employee_management.bean;

public class EmployeeSubmitItem {
    private int id;
    private String type; // "Report" or "Absence Request"
    private String title;
    private String submittedBy;
    private String date;
    
	public EmployeeSubmitItem() {
		
	}
    
	public EmployeeSubmitItem(int id, String type, String title, String submittedBy, String date) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.submittedBy = submittedBy;
		this.date = date;
	}
    
    
    
    
    // Getters and Setters
    

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}


    
    
    
    
    
}
