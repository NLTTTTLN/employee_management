document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    const toggleButton = document.querySelector('.toggle-btn');
    const modal = document.getElementById('actionModal');
    const addModal = document.getElementById('addModal');
    const confirmButton = document.getElementById('confirmActionBtn');
    const modalTitle = document.querySelector('#actionModal h3');
    const closeBtn = document.getElementById('closeBtn');
    const closeAddBtn = document.getElementById('closeAddBtn');
    const editSelectModal = document.getElementById('editSelectModal');
    const editModal = document.getElementById('editModal');
    const deleteModal = document.getElementById('deleteModal');
    const closeDeleteBtn = document.getElementById('closeDeleteBtn');
    const closeEditSelectBtn = document.getElementById('closeEditSelectBtn');
    const confirmSelectBtn = document.getElementById('confirmSelectBtn');
    const confirmEditBtn = document.getElementById('confirmEditBtn');
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
	const closeSubmitModalBtn = document.getElementById('closeSubmitModalBtn');
	const approveSubmitBtn = document.getElementById('approveBtn');
	const rejectSubmitBtn = document.getElementById('rejectBtn');
    // SIDEBAR SCRIPT
    window.toggleSidebar = function() {
        if (sidebar) {
            sidebar.classList.toggle('collapsed');
            const elements = sidebar.querySelectorAll('.logo, .nav-links, .bottom-link');
            elements.forEach(element => element.style.opacity = sidebar.classList.contains('collapsed') ? '0' : '1');
        }
    };

    // FETCHING DATA FROM DATABASE
	// FETCHING DATA FROM DATABASE
	function fetchDashboardData() {
	    fetch('/employee_management/manager/dashboard-info')
	        .then(response => response.json())
	        .then(data => {
	            // Update the dashboard with counts
	            document.getElementById('employee-count').textContent = data.employeeCount || 0;  // Assuming employee count exists in the response or needs to be handled

	            // Set pending counts
	            document.getElementById('pending-absence-count').textContent = data.pendingAbsenceCount;
	            document.getElementById('pending-report-count').textContent = data.pendingReportCount;

	            // Populate the table with pending items (both Absence and Report)
	            const tableBody = document.querySelector('#pending-table tbody');
	            tableBody.innerHTML = ''; // Clear existing rows

	            // Loop through each pending item and add a new row to the table
	            data.pendingItems.forEach(item => {
	                const row = document.createElement('tr');
	                row.classList.add('pending-item');
					
					// Set data-id and data-type attributes on each row
					    row.setAttribute('data-id', item.id);  // Set item ID
					    row.setAttribute('data-type', item.type);

	                const typeCell = document.createElement('td');
	                typeCell.textContent = item.type;
	                const titleCell = document.createElement('td');
	                titleCell.textContent = item.title;
	                const submittedByCell = document.createElement('td');
	                submittedByCell.textContent = item.submittedBy;  // Display employee name
	                const dateCell = document.createElement('td');
	                dateCell.textContent = new Date(item.date).toLocaleDateString();  // Format date

	                // Append cells to the row
	                row.appendChild(typeCell);
	                row.appendChild(titleCell);
	                row.appendChild(submittedByCell);
	                row.appendChild(dateCell);

	                // Append the row to the table body
	                tableBody.appendChild(row);
					// Add click event listener to the row to trigger the modal
					               row.addEventListener('click', function() {
					                   const itemId = row.getAttribute('data-id');
					                   const itemType = row.getAttribute('data-type');
					                   openSubmitDetailModal(item.id, item.type);
					               });
	            });
	        })
	        .catch(error => console.error('Error fetching dashboard data:', error));
	}

	fetchDashboardData();


	
	// Function to open the modal and load data dynamically
	function openSubmitDetailModal(itemId, itemType) {
	    console.log("itemID: " + itemId);
	    console.log("itemType: " + itemType);

	    // Construct the URL with query parameters
	    const apiUrl = `/employee_management/manager/submit-detail?itemId=${encodeURIComponent(itemId)}&itemType=${encodeURIComponent(itemType)}`;

	    // Send a GET request with query parameters
	    fetch(apiUrl)
	        .then(response => {
	            if (!response.ok) {
	                throw new Error('Network response was not ok');
	            }
	            return response.json(); // Parse the JSON if successful
	        })
	        .then(data => {
	            // Populate modal content with data
	            document.getElementById('modal-title').textContent = `Tiêu đề: ${data.title}`;
	            document.getElementById('modal-submitted-by').textContent = `Gửi bởi: ${data.submittedBy}`;
	            document.getElementById('modal-date').textContent = `Ngày: ${data.date}`;
	            document.getElementById('modal-type').textContent = `Loại: ${data.type}`;
	            document.getElementById('modal-description').textContent = `Mô tả: ${data.description}`;

	            // Conditionally show/hide file path based on itemType
	            const filePathContainer = document.getElementById('modal-file-path');
	            if (itemType === 'Report' && data.filePath) {
	                const fileName = data.filePath.split('/').pop();  // This gets the last part of the URL path
	                filePathContainer.innerHTML = `<p><strong>File đính kèm:</strong> <a href="#" id="download-link">${fileName}</a></p>`;
	                filePathContainer.style.display = 'block';

	                // Add event listener for file download
	                document.getElementById('download-link').addEventListener('click', function(event) {
	                    event.preventDefault(); // Prevent default action
	                    // Now, construct the full URL with the correct file extension
	                    const downloadUrl = `/employee_management/manager/download/${fileName}`;  
	                    console.log("Downloading file from URL: " + downloadUrl);  // Log the download URL
	                    window.location.href = downloadUrl;  // Redirect to the file URL, triggering the download
	                });
	            } else {
	                filePathContainer.style.display = 'none';
	            }

	            // Store item ID and type in the modal
	            document.getElementById('submit-detail-modal').setAttribute('data-item-id', itemId);
	            document.getElementById('submit-detail-modal').setAttribute('data-item-type', itemType);

	            // Show the modal
	            document.getElementById('submit-detail-modal').style.display = 'block';
	        })
	        .catch(error => console.error('Lỗi đồng bộ dữ liệu báo cáo/đơn:', error));
	}





	if (approveSubmitBtn) {
		       approveSubmitBtn.onclick = function() {
		           handleApproval('Chấp thuận');
		       };
		   }
	if (rejectSubmitBtn) {
		   		rejectSubmitBtn.onclick = function() {
		   		   handleApproval('Từ chối ');
		   		};
		   }
	// Function to close the modal
	if (closeSubmitModalBtn) {
	       closeSubmitModalBtn.onclick = function() {
	           closeSubmitDetailModal();
	       };
	   }

	   function closeSubmitDetailModal() {
		console.log("Closing...");
	        document.getElementById('submit-detail-modal').style.display = 'none';
	   }

	   // Function to handle approval/rejection actions
	   function handleApproval(action) {
	       const itemId = document.getElementById('submit-detail-modal').getAttribute('data-item-id');
	       const itemType = document.getElementById('submit-detail-modal').getAttribute('data-item-type');

	       if (!itemId || !itemType) {
	           console.error('Error: ID và loại đơn còn thiếu.');
	           return;
	       }

	       console.log(`Item ID: ${itemId}, Item Type: ${itemType}, Action: ${action}`);

	       // Make the API request to handle the approval/rejection
	       fetch(`/employee_management/manager/handleApproval?itemId=${encodeURIComponent(itemId)}&itemType=${encodeURIComponent(itemType)}&action=${encodeURIComponent(action)}`, {
	           method: 'POST',
	           headers: {
	               'Content-Type': 'application/json'
	           },
	           body: JSON.stringify({ action }) // Send item type and action status
	       })
	       .then(response => response.json())
	       .then(data => {
	           if (data.success) {
	               alert(`Đơn đã ${action.toLowerCase()} thành công.`);
	               closeSubmitDetailModal(); // Close modal after action
	               location.reload(); // Refresh the page to show updated status
	           } else {
	               alert('Error: ' + data.message);
	           }
	       })
	       .catch(error => console.error('Lỗi khi chấp thuận/từ chối:', error));
	   }




    function fetchEmployee() {
        fetch('/employee_management/manager/management-data')
            .then(response => response.json())
            .then(data => {
                populateTable('employees', data.employeeData);
                populateEmployeeUsername(data.employeeData);
            })
            .catch(error => console.error('Lỗi khi đồng bộ danh sách nhân viên:', error));
    }
    fetchEmployee();

    // POPULATE EMPLOYEE MANAGEMENT TABLE
    function populateTable(type, employees) {
        const tableBody = document.querySelector('.manager-management-table tbody');
        if (tableBody) {
            tableBody.innerHTML = ''; // Clear existing rows
            employees.forEach(employee => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${employee.username}</td>
                    <td>${employee.name}</td>
                    <td>${employee.gender}</td>
                    <td>${employee.dob}</td>
                    <td>${employee.email}</td>
                    <td>${employee.phone_num}</td>
                    <td>${employee.address}</td>
                    <td>${employee.department}</td>
                    <td>${employee.salary}</td>
                `;
                tableBody.appendChild(row);
            });
        }
    }

    // POPULATE EMPLOYEE USERNAME
    function populateEmployeeUsername(employees) {
        const employeeList = document.getElementById('employeeList');
        const employeeListEdit = document.getElementById('employeeListEdit');
        if (employeeList && employeeListEdit) {
            employeeList.innerHTML = ''; // Clear previous options
            employeeListEdit.innerHTML = ''; // Clear previous options
            employees.forEach(employee => {
                const option = document.createElement('option');
                option.value = employee.username;
                option.textContent = employee.username;
                employeeList.appendChild(option);
                employeeListEdit.appendChild(option.cloneNode(true)); // Clone the option for employeeListEdit
            });
        }
    }

    // ADD EMPLOYEE FUNCTION CODE BLOCK
    const addBtn = document.getElementById('addBtn');
    if (addBtn) {
        addBtn.onclick = function() {
            openAddModal();
        };
    }

    function openAddModal() {
        if (addModal) {
            addModal.style.display = 'block';
        }
    }

    if (closeAddBtn) {
        closeAddBtn.onclick = function() {
            closeAddModal();
        };
    }

    function closeAddModal() {
        if (addModal) {
            addModal.style.display = 'none';
        }
    }

    const confirmAddBtn = document.getElementById('confirmAddBtn');
    if (confirmAddBtn) {
        confirmAddBtn.onclick = function() {
            const username = document.getElementById('username').value;
            const name = document.getElementById('name').value;
            const gender = document.getElementById('gender').value;
            const dob = document.getElementById('dob').value;
            const email = document.getElementById('email').value;
            const phone_num = document.getElementById('phone_num').value;
            const address = document.getElementById('address').value;
            const department = document.getElementById('department').value;
            const salary = document.getElementById('salary').value;

            const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (username && name && gender && dob && email && phone_num && address && department && salary) {
                if (phone_num.length === 10) {
                    if (emailRegex.test(email)) {
                        addEmployee(username, name, gender, dob, email, phone_num, address, department, salary);
                    } else {
                        alert("Vui lòng nhập đúng địa chỉ email.");
                    }
                } else {
                    alert('Vui lòng nhập đúng số điện thoại.');
                }
            } else {
                alert('Vui lòng điền đầy đủ thông tin.');
            }
        };
    }

    function addEmployee(username, name, gender, dob, email, phone_num, address, department, salary) {
        fetch('/employee_management/manager/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `username=${encodeURIComponent(username)}&name=${encodeURIComponent(name)}&gender=${encodeURIComponent(gender)}&dob=${encodeURIComponent(dob)}&email=${encodeURIComponent(email)}&phone_num=${encodeURIComponent(phone_num)}&address=${encodeURIComponent(address)}&department=${encodeURIComponent(department)}&salary=${encodeURIComponent(salary)}`
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    throw new Error(`Lỗi khi thêm nhân viên: ${errorMessage}`);
                });
            }
            return response.text();
        })
        .then(() => {
            alert(`Nhân viên ${username} được thêm thành công.`);
            fetchEmployee(); // Refresh employee data
            closeAddModal(); // Close the Add Modal
        })
        .catch(error => {
            console.error('Lỗi khi thêm nhân viên:', error);
            alert(`Lỗi: ${error.message}`);
        });
    }

    // DELETE EMPLOYEE FUNCTION CODE BLOCK
    const deleteBtn = document.getElementById('deleteBtn');
    if (deleteBtn) {
        deleteBtn.onclick = function() {
            openDeleteModal();
        };
    }

    function openDeleteModal() {
        if (deleteModal) {
            deleteModal.style.display = 'block';
        }
    }

    if (closeDeleteBtn) {
        closeDeleteBtn.onclick = function() {
            closeDeleteModal();
        };
    }

    function closeDeleteModal() {
        if (deleteModal) {
            deleteModal.style.display = 'none';
        }
    }

    const searchEmployeeInput = document.getElementById('searchEmployee');
    if (searchEmployeeInput) {
        searchEmployeeInput.addEventListener('input', function() {
            const searchQuery = this.value.toLowerCase();
            const employeeList = document.getElementById('employeeList');
            if (employeeList) {
                const options = employeeList.getElementsByTagName('option');
                Array.from(options).forEach(option => {
                    option.style.display = option.textContent.toLowerCase().includes(searchQuery) ? 'block' : 'none';
                });
            }
        });
    }

    function selectEmployee() {
        const deleteUsername = document.getElementById('employeeList').value;
        const selectUsername = document.getElementById('selectUsername');
        if (selectUsername) {
            selectUsername.value = deleteUsername;
        }
    }

    const employeeList = document.getElementById('employeeList');
    if (employeeList) {
        employeeList.addEventListener('change', function() {
            const deleteUsername = document.getElementById('deleteUsername');
            if (deleteUsername) {
                deleteUsername.value = this.value || '';
            }
        });
    }

    if (confirmDeleteBtn) {
        confirmDeleteBtn.onclick = function() {
            const username = document.getElementById('deleteUsername').value;
            if (username) {
                deleteUser(username);
                closeDeleteModal();
            } else {
                alert('Không có nhân viên nào được chọn để xóa.');
            }
        };
    }

    function deleteUser(username) {
        if (confirm(`Bạn chắc chắn muốn xóa nhân viên ${username}?`)) {
            fetch('/employee_management/manager/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `username=${encodeURIComponent(username)}`
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Không thể xóa nhân viên');
                }
                return response.text();
            })
            .then(() => {
                alert(`Nhân viên ${username} đã được xóa thành công.`);
                fetchEmployee(); // Refresh employee data
            })
            .catch(error => console.error('Lỗi khi xóa nhân viên:', error));
        }
    }

    // EDIT EMPLOYEE FUNCTION CODE BLOCK
    const editBtn = document.getElementById('editBtn');
    if (editBtn) {
        editBtn.onclick = function() {
            openEditSelectModal();
        };
    }

    function openEditSelectModal() {
        if (editSelectModal) {
            editSelectModal.style.display = 'block';
        }
    }

    if (closeEditSelectBtn) {
        closeEditSelectBtn.onclick = function() {
            closeEditSelectModal();
        };
    }

    function closeEditSelectModal() {
        if (editSelectModal) {
            editSelectModal.style.display = 'none';
        }
    }


    const searchEditEmployeeInput = document.getElementById('searchEditEmployee');
    if (searchEditEmployeeInput) {
        searchEditEmployeeInput.addEventListener('input', function() {
            const searchQuery = this.value.toLowerCase();
            const employeeListEdit = document.getElementById('employeeListEdit');
            if (employeeListEdit) {
                const options = employeeListEdit.getElementsByTagName('option');
                Array.from(options).forEach(option => {
                    option.style.display = option.textContent.toLowerCase().includes(searchQuery) ? 'block' : 'none';
                });
            }
        });
    }



	// When you select an employee
	function selectEditEmployee() {
	    const selectEditUsername = document.getElementById('employeeListEdit').value;
	    const selectedUsernameInput = document.getElementById('selectedEditUsername');
	    
	    if (selectEditUsername && selectedUsernameInput) {
	        selectedUsernameInput.value = selectEditUsername; // Set the selected employee's username
	        console.log('Selected employee:', selectEditUsername); // Debugging
	        
	        // Fetch the full employee data and populate the edit modal
	        fetch(`/employee_management/manager/get-employee?username=${encodeURIComponent(selectEditUsername)}`)
	            .then(response => response.json())
	            .then(data => {
					console.log('Employee Data:', data);
	                if (data) {
	                    // Populate the fields in the edit modal with the employee's data
						document.getElementById('editUsername').value = data.username;
	                    document.getElementById('editName').value = data.name;
	                    document.getElementById('editGender').value = data.gender;
	                    document.getElementById('editDob').value = data.dob;
	                    document.getElementById('editEmail').value = data.email;
	                    document.getElementById('editPhone_num').value = data.phone_num;
	                    document.getElementById('editAddress').value = data.address;
	                    document.getElementById('editDepartment').value = data.department;
	                    document.getElementById('editSalary').value = data.salary;
	                } else {
	                    alert('Không tìm thấy nhân viên.');
	                }
	            })
	            .catch(error => {
	                console.error('Error fetching employee data:', error);
	                alert('Lỗi khi đồng bộ thÔng tin nhân viên.');
	            });
	    }
	}

	// Event listener for dropdown change
	const employeeListEdit = document.getElementById('employeeListEdit');
	if (employeeListEdit) {
			     employeeListEdit.addEventListener('change', selectEditEmployee);
			    
			 }

	// Confirm button action (this should open the edit modal)
	if (confirmSelectBtn) {
	    confirmSelectBtn.onclick = function() {
	        const selectEditUsername = document.getElementById('employeeListEdit').value;
	        if (selectEditUsername) {
	            selectedEditEmployee = { username: selectEditUsername };
	            openEditModal();
	        } else {
	            alert('Xin hãy chọn nhân viên.');
	        }
	    };
	}

    function openEditModal() {
        if (editModal) {
            editModal.style.display = 'block';
        }
    }
	const closeEditBtn = document.getElementById('closeEditBtn');
    if (closeEditBtn) {
        closeEditBtn.onclick = function() {
            closeEditSelectModal();
            closeEditModal();
        };
    }

    function closeEditModal() {
        if (editModal) {
            editModal.style.display = 'none';
        }
    }

    if (confirmEditBtn) {
        confirmEditBtn.onclick = function() {
            const username = document.getElementById('editUsername').value;
            const name = document.getElementById('editName').value;
            const gender = document.getElementById('editGender').value;
            const dob = document.getElementById('editDob').value;
            const email = document.getElementById('editEmail').value;
            const phone_num = document.getElementById('editPhone_num').value;
            const address = document.getElementById('editAddress').value;
            const department = document.getElementById('editDepartment').value;
            const salary = document.getElementById('editSalary').value;

            if (username && name && gender && dob && email && phone_num && address && department && salary) {
                editEmployee(username, name, gender, dob, email, phone_num, address, department, salary);
            } else {
                alert('Vui lòng điền đầy đủ thông tin.');
            }
        };
    }
    function editEmployee(username, name, gender, dob, email, phone_num, address, department, salary) {
        fetch('/employee_management/manager/edit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `username=${encodeURIComponent(username)}&name=${encodeURIComponent(name)}&gender=${encodeURIComponent(gender)}&dob=${encodeURIComponent(dob)}&email=${encodeURIComponent(email)}&phone_num=${encodeURIComponent(phone_num)}&address=${encodeURIComponent(address)}&department=${encodeURIComponent(department)}&salary=${encodeURIComponent(salary)}`
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    throw new Error(`Lỗi khi cập nhật thông tin nhân viên: ${errorMessage}`);
                });
            }
            return response.text();
        })
        .then(() => {
            alert(`Nhân viên ${username} được cập nhật thành công.`);
            fetchEmployee(); // Refresh employee data

            closeEditModal(); // Close the Edit Modal
			closeEditSelectModal();
        })
        .catch(error => {
            console.error('Error editing user:', error);
            alert(`Lỗi: ${error.message}`);
        });
    }
	
	
	
	
	
	const profileBtn = document.getElementById("profileBtn");
	const changePasswordBtn = document.getElementById("changePasswordBtn");
	const profileDiv = document.querySelector('.profile'); // Div for personal info
	const changePasswordDiv = document.querySelector('.changePassword'); // Div for password change
	const submitProfileBtn = document.getElementById('submitProfileBtn');
	const submitChangePwdBtn = document.getElementById('submitChangePwdBtn');
	
	// Toggle visibility between profile and change password
		if (profileBtn) {
		     profileBtn.addEventListener('click', function() {
		         console.log("Profile button clicked!");
		         showPersonalInfo();
		     });
		 }

		 if (changePasswordBtn) {
		     changePasswordBtn.addEventListener('click', function() {
		         console.log("Change Password button clicked!");
		         showChangePassword();
		     });
		 }
		 if (profileDiv) showPersonalInfo();
	

	    // Function to show the personal info form and hide the change password form
	    function showPersonalInfo() {
			console.log("1");
	        if (profileDiv) {
	            profileDiv.style.display = 'block';
	        }
	        if (changePasswordDiv) {
	            changePasswordDiv.style.display = 'none';
	        }
	    }

	    // Function to show the change password form and hide the personal info form
	    function showChangePassword() {
	        if (profileDiv) {
	            profileDiv.style.display = 'none';
	        }
	        if (changePasswordDiv) {
	            changePasswordDiv.style.display = 'block';
	        }
	    }
		
		   
		   // Function to validate change password form
		   function validatePasswordForm() {
		       

		       if (!oldPass || !newPass || !confirmPass) {
		           alert('Vui lòng điền đầy đủ thông tin');
		           return false;
		       }

		       if (newPass !== confirmPass) {
		           alert('Mật khẩu mới và xác nhận mật khẩu không khớp');
		           return false;
		       }

		       return true;
		   }
		   
		   // Add event listener to the submit button of the change password form
		   
		   if (submitChangePwdBtn) {
			submitChangePwdBtn.addEventListener('click', function(event) {
			        event.preventDefault(); // Prevent the default form submission
					const oldPass = document.getElementById('oldPass').value;
						   const newPass = document.getElementById('newPass').value;
						   const confirmPass = document.getElementById('confirmPass').value;
						   const employeeUsername = document.getElementById('username').value;
			        const oldPassword = oldPass;
			        const newPassword = newPass;
			        const confirmPassword = confirmPass;

			        // Basic validation checks
			        if (!oldPassword || !newPassword || !confirmPassword) {
			            alert('Vui lòng điền đủ thông tin.');
						console.log(oldPassword);
						console.log(newPassword);
						console.log(confirmPassword);
			            return;
			        }

			        if (newPassword !== confirmPassword) {
			            alert('Mật khẩu mới và mật khẩu xác nhận không khớp.');
			            return;
			        }

			        if (newPassword.length < 6) {
			            alert('Mật khẩu mới phải có ít nhất 6 ký tự.');
			            return;
			        }

			        // Send the password change data to the server
			        const formData = new FormData();
			        formData.append('oldPassword', oldPassword);
			        formData.append('newPassword', newPassword);
					formData.append('username', employeeUsername);

			        // Use fetch to send the data to the backend
			        fetch('/employee_management/manager/change-password', {
			            method: 'POST',
			            body: formData
			        })
			        .then(response => response.json())
			        .then(data => {
			            if (data.success) {
			                alert('Mật khẩu đã được cập nhật thành công.');
			                // Reset form fields after successful change
							document.getElementById('oldPass').value = '';
							document.getElementById('newPass').value = '';
							document.getElementById('confirmPass').value = '';
			            } else {
			                alert('Có lỗi khi đổi mật khẩu. Vui lòng thử lại.');
			            }
			        })
			        .catch(error => {
			            console.error('Error:', error);
			            alert('Có lỗi khi kết nối với máy chủ.');
			        });
			    });
		   }
		  
		   if (submitProfileBtn) {
		           submitProfileBtn.addEventListener('click', function (event) {
		               event.preventDefault(); // Prevent the default form submission behavior
		               
		               // Collect form data
		               const username = document.getElementById('username').value;
		               const name = document.getElementById('name').value;
		               const gender = document.getElementById('gender').value;
		               const dob = document.getElementById('dob').value;
		               const email = document.getElementById('email').value;
		               const phone_num = document.getElementById('phone_num').value;
		               const address = document.getElementById('address').value;

		               // Prepare data to be sent to the server
		               const formData = new FormData();
		               formData.append('username', username);
		               formData.append('name', name);
		               formData.append('gender', gender);
		               formData.append('dob', dob);
		               formData.append('email', email);
		               formData.append('phone_num', phone_num);
		               formData.append('address', address);

		               // Send data via fetch to the backend (Assume the URL is '/employee_management/employee/update')
		               fetch('/employee_management/manager/update', {
		                   method: 'POST',
		                   body: formData,
		               })
		               .then(response => response.json()) // Parse the JSON response
		               .then(data => {
		                   if (data.success) {
		                       alert('Thông tin cá nhân đã được cập nhật.');
							   location.reload();
		                   } else {
		                       alert('Có lỗi khi cập nhật thông tin.');
		                   }
		               })
		               .catch(error => {
		                   console.error('Error:', error);
		                   alert('Có lỗi xảy ra. Vui lòng thử lại.');
		               });
		           });
		       }
});
