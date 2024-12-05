document.addEventListener('DOMContentLoaded', function() {
	console.log("DOM is fully loaded");
    const sidebar = document.querySelector('.sidebar');
    const profileBtn = document.getElementById("profileBtn");
    const changePasswordBtn = document.getElementById("changePasswordBtn");
    const profileDiv = document.querySelector('.profile'); // Div for personal info
    const changePasswordDiv = document.querySelector('.changePassword'); // Div for password change
    const employeeId = document.getElementById('employeeId').value;
	
    const tableBody = document.querySelector('#pending-table tbody');
	const deleteSubmitBtn = document.getElementById('deleteSubmitBtn');
	const closeSubmitModalBtn = document.getElementById('closeSubmitModalBtn');
	const addBtn = document.getElementById("addBtn");
	    const chooseTypeModal = document.getElementById("choose-type-modal");
	    const createItemModal = document.getElementById("create-item-modal");
	    const closeChooseModalBtn = document.getElementById("closeChooseModalBtn");
	    const closeCreateItemModalBtn = document.getElementById("closeCreateItemModalBtn");
	    const createReportBtn = document.getElementById("createReportBtn");
	    const createAbsenceBtn = document.getElementById("createAbsenceBtn");
	    const itemTitleInput = document.getElementById("itemTitle");
	    const itemDescriptionInput = document.getElementById("itemDescription");
	    const itemTypeInput = document.getElementById("itemType");
	    const createItemForm = document.getElementById("createItemForm");
		const submitProfileBtn = document.getElementById('submitProfileBtn');
		const submitChangePwdBtn = document.getElementById('submitChangePwdBtn');


    // SIDEBAR SCRIPT
    window.toggleSidebar = function() {
        if (sidebar) {
            sidebar.classList.toggle('collapsed');
            const elements = sidebar.querySelectorAll('.logo, .nav-links, .bottom-link');
            elements.forEach(element => element.style.opacity = sidebar.classList.contains('collapsed') ? '0' : '1');
        }
    };
    
    // Fetch employee submissions from the backend
    function fetchEmployeeSubmissions() {
        fetch(`/employee_management/employee/dashboard-info?id=${employeeId}`)
            .then(response => response.json()) // Assume the response is in JSON format
            .then(data => {
                // Check if 'Submit Items' exists in the response
                const submissions = data['Submit Items']; // Correcting the data access here
                if (submissions && submissions.length > 0) {
                    populateTable(submissions);
                } else {
                    console.log('Không tìm thấy đơn.');
                }
            })
            .catch(error => {
                console.error('Lỗi khi đồng bộ đơn:', error);
            });
    }

    // Populate the table with the fetched submissions
    function populateTable(submissions) {
        tableBody.innerHTML = ''; // Clear existing table rows
        submissions.forEach(submission => {
            const row = document.createElement('tr');
			row.classList.add('submission');
			row.setAttribute('data-id', submission.id);  // Set item ID
			row.setAttribute('data-type', submission.type);
            
            // Create table data cells for each submission item
            const typeCell = document.createElement('td');
            typeCell.textContent = submission.type;
            row.appendChild(typeCell);

            const titleCell = document.createElement('td');
            titleCell.textContent = submission.title;
            row.appendChild(titleCell);

            const dateCell = document.createElement('td');
            dateCell.textContent = submission.date;  // Make sure it's properly formatted
            row.appendChild(dateCell);

            const statusCell = document.createElement('td');
            statusCell.textContent = submission.status;
            row.appendChild(statusCell);

            // Add row to the table body
            tableBody.appendChild(row);
			// Add click event listener to the row to trigger the modal
								               row.addEventListener('click', function() {
								                   const submissionId = row.getAttribute('data-id');
								                   const submissionType = row.getAttribute('data-type');
								                   openSubmitDetailModal(submission.id, submission.type);
								               });
        });
    }
	// Call the function to fetch and populate submissions
	    
	
	// Function to open the modal and load data dynamically
		function openSubmitDetailModal(itemId, itemType) {
		    console.log("itemID: " + itemId);
		    console.log("itemType: " + itemType);

		    // Construct the URL with query parameters
		    const apiUrl = `/employee_management/employee/submit-detail?itemId=${encodeURIComponent(itemId)}&itemType=${encodeURIComponent(itemType)}`;

		    // Send a GET request with query parameters
		    fetch(apiUrl)
		        .then(response => {
		            if (!response.ok) {
		                throw new Error('Phản hồi mạng không tốt.');
		            }
		            return response.json(); // Parse the JSON if successful
		        })
		        .then(data => {
		            // Populate modal content with data
		            document.getElementById('modal-title').textContent = `Tiêu đề: ${data.title}`;
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
		                    const downloadUrl = `/employee_management/employee/download/${fileName}`;  
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
		        .catch(error => console.error('Lỗi khi đồng bộ chi tiết đơn:', error));
		}
		// Function to close the modal
	
		   if (closeSubmitModalBtn) {
		   	     closeSubmitModalBtn.addEventListener('click', function() {
		   	         console.log("Close submit button clicked!");
		   	         closeSubmitDetailModal();
		   	     });
		   	 }

		   function closeSubmitDetailModal() {
			console.log("Closing...");
		        document.getElementById('submit-detail-modal').style.display = 'none';
		   }
		   if (deleteSubmitBtn) {
		   		       deleteSubmitBtn.onclick = function() {
		   		           deleteSubmit();
		   		       };
		   		   }
				   
				   function deleteSubmit() {
				       // Get the item ID and type from the modal
				       const itemId = document.getElementById('submit-detail-modal').getAttribute('data-item-id');
				       const itemType = document.getElementById('submit-detail-modal').getAttribute('data-item-type');
				       
				       // Confirm deletion with the user
				       if (confirm(`Bạn có chắc chắn muốn xóa ${itemType} ID: ${itemId} này không?`)) {
				           // Make a DELETE request to the backend to delete the item
				           fetch(`/employee_management/employee/delete-submit?itemId=${encodeURIComponent(itemId)}&itemType=${encodeURIComponent(itemType)}`, {
				               method: 'POST',
				           })
				           .then(response => {
				               if (!response.ok) {
				                   throw new Error('Xóa đơn thất bại.');
				               }
				               return response.json();
				           })
				           .then(data => {
				               // Check if the deletion was successful
				               if (data.success) {
				                   console.log(`Xóa đơn ${itemType} ID: ${itemId} thành công`);
								   alert(`Xóa đơn ${itemType} ID: ${itemId} thành công`);
				                   // Remove the item from the table (UI update)
				                   const row = document.querySelector(`tr[data-id="${itemId}"]`);
				                   if (row) {
				                       row.remove(); // Remove the row from the table
				                   }

				                   // Close the modal after successful deletion
				                   closeSubmitDetailModal();
								   location.reload();
				               } else {
				                   console.error('Xóa đơn thất bại: ' + data.message);
				               }
				           })
				           .catch(error => {
				               console.error('Lỗi khi xóa đơn:', error);
				           });
				       }
				   }


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
	 if (employeeId && tableBody) {
	         fetchEmployeeSubmissions();
	     }

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
	
	
	
	
	
	
	// Show the "choose type" modal when "Tạo +" button is clicked
	if (addBtn) {
	    addBtn.addEventListener('click', function() {
	        chooseTypeModal.style.display = 'block';
	    });
	}

	   // Close the "choose type" modal
	   if (closeChooseModalBtn) {
	       closeChooseModalBtn.addEventListener('click', function() {
	           chooseTypeModal.style.display = 'none';
	       });
	   }

	   // Choose to create a Report
	   if (createReportBtn) {
	       createReportBtn.addEventListener('click', function() {
	           itemTypeInput.value = "Report";
	           document.getElementById("modal-title").textContent = "Tạo báo cáo";
	           document.getElementById("file-upload-container").style.display = "block";
	           chooseTypeModal.style.display = 'none';
	           createItemModal.style.display = 'block';
	       });
	   }

	   // Choose to create an Absence Request
	   if (createAbsenceBtn) {
	       createAbsenceBtn.addEventListener('click', function() {
	           itemTypeInput.value = "Absence Request";
	           document.getElementById("modal-title").textContent = "Tạo đơn xin vắng mặt";
	           document.getElementById("file-upload-container").style.display = "none";
	           chooseTypeModal.style.display = 'none';
	           createItemModal.style.display = 'block';
	       });
	   }

	   // Close the "create item" modal
	   
	   if (closeCreateItemModalBtn) {
	       closeCreateItemModalBtn.addEventListener('click', function() {
	           createItemModal.style.display = 'none';
	       });
	   }

	   // Handle the form submission for creating a report or absence request
	   if (createItemForm) {
	       createItemForm.addEventListener('submit', function(event) {
	           event.preventDefault();

	           const itemTitle = document.getElementById("itemTitle").value;
	           const itemDescription = document.getElementById("itemDescription").value;
	           const itemType = document.getElementById("itemType").value;
	           const itemFile = document.getElementById("itemFile").files[0];

	           // Create FormData object to send form data including file
	           const formData = new FormData();

	           formData.append("title", itemTitle);
	           formData.append("description", itemDescription);
	           formData.append("employee_id", employeeId);
	           formData.append("type", itemType);

	           // If it's a Report, append the file to the FormData
	           if (itemType === "Report") {
	               if (itemFile) {
	                   formData.append("file", itemFile); // Append the file to FormData
	               } else {
	                   alert("Vui lòng chọn tệp để tải lên.");
	                   return; // Stop the form submission if no file is selected
	               }
	           }

	           // Send the data to the backend using fetch
	           fetch(`/employee_management/employee/create-item`, {
	               method: 'POST',
	               body: formData // Send FormData instead of JSON
	           })
	           .then(response => response.json())
	           .then(data => {
	               if (data.success) {
	                   alert(`Đã tạo ${itemType} thành công.`);
	                   // Close the modal and reset the form
	                   createItemModal.style.display = 'none';
	                   createItemForm.reset();
	                   location.reload();
	               } else {
	                   alert('Có lỗi khi tạo đơn.');
	               }
	           })
	           .catch(error => {
	               console.error('Error:', error);
	           });
	       });
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
		        fetch('/employee_management/employee/change-password', {
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
	               const employeeId = document.getElementById('employeeId').value;
	               const username = document.getElementById('username').value;
	               const name = document.getElementById('name').value;
	               const gender = document.getElementById('gender').value;
	               const dob = document.getElementById('dob').value;
	               const email = document.getElementById('email').value;
	               const phone_num = document.getElementById('phone_num').value;
	               const address = document.getElementById('address').value;

	               // Prepare data to be sent to the server
	               const formData = new FormData();
	               formData.append('employeeId', employeeId);
	               formData.append('username', username);
	               formData.append('name', name);
	               formData.append('gender', gender);
	               formData.append('dob', dob);
	               formData.append('email', email);
	               formData.append('phone_num', phone_num);
	               formData.append('address', address);

	               // Send data via fetch to the backend (Assume the URL is '/employee_management/employee/update')
	               fetch('/employee_management/employee/update', {
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

    // Optionally, initialize to show the profile by default
});
