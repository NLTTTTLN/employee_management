document.addEventListener('DOMContentLoaded', function() {
	console.log("DOM is fully loaded");
    const sidebar = document.querySelector('.sidebar');
    const profileBtn = document.getElementById("profileBtn");
    const changePasswordBtn = document.getElementById("changePasswordBtn");
    const profileDiv = document.querySelector('.profile'); // Div for personal info
    const changePasswordDiv = document.querySelector('.changePassword'); // Div for password change
    const employeeId = document.getElementById('employeeId').value;
    const tableBody = document.querySelector('#pending-table tbody');

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
        });
    }

    // Call the function to fetch and populate submissions
    fetchEmployeeSubmissions();

    // Toggle visibility between profile and change password
    profileBtn.addEventListener('click', function() {
		console.log("Profile button clicked!");
        showPersonalInfo();
    });

    changePasswordBtn.addEventListener('click', function() {
		console.log("Lmao button clicked!");
        showChangePassword();
    });

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

    // Optionally, initialize to show the profile by default
    showPersonalInfo();
});
