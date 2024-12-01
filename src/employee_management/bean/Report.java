package employee_management.bean;

public class Report {
    private int id;
    private String title;
    private String description;
    private String filePath;
    private int employeeId;
    private int managerId;
    private String status;
    private String createdAt;
    
    
    
    
	public Report() {
		
	}

	public Report(int id, String title, String description, String filePath, int employeeId, int managerId,
			String status, String createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.filePath = filePath;
		this.employeeId = employeeId;
		this.managerId = managerId;
		this.status = status;
		this.createdAt = createdAt;
	}
    
    // Getters and Setters
    

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


    
    
}
