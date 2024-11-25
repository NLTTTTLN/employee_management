document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    const toggleButton = document.querySelector('.toggle-btn');

    window.toggleSidebar = function() {
        sidebar.classList.toggle('collapsed');
        
        // Fade out the contents when collapsing
        if (sidebar.classList.contains('collapsed')) {
            sidebar.querySelector('.logo').style.opacity = '0';
            sidebar.querySelector('.nav-links').style.opacity = '0';
            sidebar.querySelector('.bottom-link').style.opacity = '0';
        } else {
            sidebar.querySelector('.logo').style.opacity = '1';
            sidebar.querySelector('.nav-links').style.opacity = '1';
            sidebar.querySelector('.bottom-link').style.opacity = '1';
        }
    };

    // Fetch dashboard data
    function fetchDashboardData() {
        // Replace with your API endpoint
        fetch('/employee_management/admin/dashboard-info') 
            .then(response => response.json())
            .then(data => {
                document.getElementById('managerCount').textContent = data.managerCount;
                document.getElementById('employeeCount').textContent = data.employeeCount;

                // Populate recent activities
                const managerActivities = data.managerActivities.map(activity => `<li>${activity}</li>`).join('');
                const employeeActivities = data.employeeActivities.map(activity => `<li>${activity}</li>`).join('');

                document.getElementById('managerActivities').innerHTML = managerActivities;
                document.getElementById('employeeActivities').innerHTML = employeeActivities;
            })
            .catch(error => console.error('Error fetching dashboard data:', error));
    }

    // Call the fetch function when the page loads
    fetchDashboardData();
});

// Modal functionality
document.getElementById('deleteBtn').onclick = function() {
    openModal('delete');
};

document.getElementById('promoteBtn').onclick = function() {
    openModal('promote');
};

document.getElementById('closeBtn').onclick = function() {
    closeModal();
};

function openModal(actionType) {
    // Clear previous selection and action type
    document.getElementById('userSelect').innerHTML = '';

    // Fetch data for managers or employees based on action type
    let options = '';
    if (actionType === 'delete') {
        // Populate the select with users to delete
        // You may fetch from a server or use static data here
        options = `<option value="manager1">Manager 1</option>
                   <option value="manager2">Manager 2</option>
                   <option value="employee1">Employee 1</option>
                   <option value="employee2">Employee 2</option>`;
    } else if (actionType === 'promote') {
        // Populate the select with users to promote
        options = `<option value="employee1">Employee 1</option>
                   <option value="employee2">Employee 2</option>`;
    }

    document.getElementById('userSelect').innerHTML = options;

    // Show the modal
    document.getElementById('actionModal').style.display = "block";
}

function closeModal() {
    document.getElementById('actionModal').style.display = "none";
}

// Confirm action (delete or promote)
document.getElementById('confirmActionBtn').onclick = function() {
    let selectedUser = document.getElementById('userSelect').value;
    alert(`Action confirmed for: ${selectedUser}`);
    closeModal(); // Close modal after action
};

