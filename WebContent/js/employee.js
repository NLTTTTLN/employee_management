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
                    console.log('No submissions found.');
                }
            })
            .catch(error => {
                console.error('Error fetching submissions:', error);
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
		                throw new Error('Network response was not ok');
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
		        .catch(error => console.error('Error fetching submit item details:', error));
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
				       if (confirm(`Are you sure you want to delete this ${itemType} with ID: ${itemId}?`)) {
				           // Make a DELETE request to the backend to delete the item
				           fetch(`/employee_management/employee/delete-submit?itemId=${encodeURIComponent(itemId)}&itemType=${encodeURIComponent(itemType)}`, {
				               method: 'POST',
				           })
				           .then(response => {
				               if (!response.ok) {
				                   throw new Error('Failed to delete submission');
				               }
				               return response.json();
				           })
				           .then(data => {
				               // Check if the deletion was successful
				               if (data.success) {
				                   console.log(`Successfully deleted ${itemType} with ID: ${itemId}`);
								   alert(`Successfully deleted ${itemType} with ID: ${itemId}`);
				                   // Remove the item from the table (UI update)
				                   const row = document.querySelector(`tr[data-id="${itemId}"]`);
				                   if (row) {
				                       row.remove(); // Remove the row from the table
				                   }

				                   // Close the modal after successful deletion
				                   closeSubmitDetailModal();
								   location.reload();
				               } else {
				                   console.error('Failed to delete the submission: ' + data.message);
				               }
				           })
				           .catch(error => {
				               console.error('Error deleting submission:', error);
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
	   addBtn.addEventListener('click', function() {
	       chooseTypeModal.style.display = 'block';
	   });

	   // Close the "choose type" modal
	   closeChooseModalBtn.addEventListener('click', function() {
	       chooseTypeModal.style.display = 'none';
	   });

	   // Choose to create a Report
	   createReportBtn.addEventListener('click', function() {
	       itemTypeInput.value = "Report";
	       document.getElementById("modal-title").textContent = "Tạo Báo cáo";
	       chooseTypeModal.style.display = 'none';
	       createItemModal.style.display = 'block';
	   });

	   // Choose to create an Absence Request
	   createAbsenceBtn.addEventListener('click', function() {
	       itemTypeInput.value = "Absence Request";
	       document.getElementById("modal-title").textContent = "Yêu cầu Vắng Mặt";
	       chooseTypeModal.style.display = 'none';
	       createItemModal.style.display = 'block';
	   });

	   // Close the "create item" modal
	   closeCreateItemModalBtn.addEventListener('click', function() {
	       createItemModal.style.display = 'none';
	   });

	   // Handle the form submission for creating a report or absence request
	   createItemForm.addEventListener('submit', function(event) {
	       event.preventDefault();

	       const itemTitle = itemTitleInput.value;
	       const itemDescription = itemDescriptionInput.value;
	       const itemType = itemTypeInput.value;

	       // Send the data to the backend for creation
	       fetch(`/employee_management/employee/create-item`, {
	           method: 'POST',
	           headers: {
	               'Content-Type': 'application/json'
	           },
	           body: JSON.stringify({
	               title: itemTitle,
	               description: itemDescription,
	               type: itemType
	           })
	       })
	       .then(response => response.json())
	       .then(data => {
	           if (data.success) {
	               alert(`Đã tạo ${itemType} thành công.`);
	               // Close the modal and reset the form
	               createItemModal.style.display = 'none';
	               createItemForm.reset();
	           } else {
	               alert('Có lỗi khi tạo đơn.');
	           }
	       })
	       .catch(error => {
	           console.error('Error:', error);
	       });
	   });
    // Optionally, initialize to show the profile by default
});
