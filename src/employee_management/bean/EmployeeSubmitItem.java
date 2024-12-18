package employee_management.bean;

public class EmployeeSubmitItem {
    private int id;
    private String type; // "Report" or "Absence Request"
    private String title;
    private String description;
    private String filePath;
    private int employee_id;
    private String submittedBy;
    private String status;
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
    
    
	public EmployeeSubmitItem(int id, String type, String title, String description, String filePath,
			String submittedBy, String date) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.description = description;
		this.filePath = filePath;
		this.submittedBy = submittedBy;
		this.date = date;
	}
	
	public EmployeeSubmitItem(int id, String type, String title, String description, String submittedBy, String date) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.description = description;
		this.submittedBy = submittedBy;
		this.date = date;
	}
	
    
    // Getters and Setters
    



	



	public int getId() {
		return id;
	}
	public int getEmployeeId() {
		return employee_id;
	}

	public void setEmployeeId(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
