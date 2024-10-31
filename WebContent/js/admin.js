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
