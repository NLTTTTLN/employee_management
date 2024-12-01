document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    const toggleButton = document.querySelector('.toggle-btn');
    const modal = document.getElementById('actionModal');
    const addModal = document.getElementById('addModal');
    const userSelect = document.getElementById('userSelect');
    const confirmButton = document.getElementById('confirmActionBtn');
    const modalTitle = document.querySelector('#actionModal h3');
    const closeBtn = document.getElementById('closeBtn');
    const closeAddBtn = document.getElementById('closeAddBtn');

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
        fetch('/employee_management/admin/dashboard-info') 
            .then(response => response.json())
            .then(data => {
                document.getElementById('managerCount').textContent = data.managerCount;
                document.getElementById('employeeCount').textContent = data.employeeCount;

                const managerActivities = data.managerActivities.map(activity => `<li>${activity}</li>`).join('');
                const employeeActivities = data.employeeActivities.map(activity => `<li>${activity}</li>`).join('');

                document.getElementById('managerActivities').innerHTML = managerActivities;
                document.getElementById('employeeActivities').innerHTML = employeeActivities;
            })
            .catch(error => console.error('Error fetching dashboard data:', error));
    }

    fetchDashboardData();

    function fetchUsers(callback) {
        fetch('/employee_management/admin/management-data') 
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch user data');
                }
                return response.json();
            })
            .then(data => {
                populateTable('managers', data.managerData);
                populateTable('employees', data.employeeData);
                callback(data);
            })
            .catch(error => console.error('Error fetching user data:', error));
    }

    fetchUsers();

    // Modal trigger buttons
    document.getElementById('addBtn').onclick = function () {
        openAddModal(); // Open Add User modal
    };
        
    document.getElementById('deleteBtn').onclick = function() {
        openModal('delete');
    };

    document.getElementById('promoteBtn').onclick = function() {
        openModal('promote');
    };

    // Event delegation for the close button
    modal.addEventListener('click', function(event) {
        if (event.target === closeBtn || event.target === modal) {
            closeModal();
        }
    });

    closeAddBtn.onclick = function () {
        closeAddModal();  // Close the Add User modal
    };

    // Open the Add User modal
    function openAddModal() {
        addModal.style.display = 'block';  // Show the Add User modal
    }

    // Open the Action Modal (Delete/Promote)
    function openModal(actionType) {
        // Reset the user selection dropdown
        userSelect.innerHTML = '';

        // Reset modal title and action button text
        if (actionType === 'add') {
            modalTitle.textContent = 'Thêm người dùng mới';
            userSelect.style.display = 'none';  // Hide dropdown for 'add' action
            modal.querySelector('#confirmActionBtn').textContent = 'Thêm';
        } else {
            modal.querySelector('#confirmActionBtn').textContent = actionType === 'promote' ? 'Thăng chức' : 'Xóa';
            userSelect.style.display = 'block'; // Show dropdown for delete/promote

            // Fetch and populate the user options based on the action type
            fetchUsers((data) => {
                let options = '';
                if (actionType === 'promote') {
                    modalTitle.textContent = 'Thăng chức nhân viên';
                    data.employeeData.forEach((user) => {
                        options += `<option value="${user.username}">${user.username}</option>`;
                    });
                } else if (actionType === 'delete') {
                    modalTitle.textContent = 'Xóa người dùng';
                    options += '<optgroup label="Managers">';
                    data.managerData.forEach((user) => {
                        options += `<option value="${user.username}">Manager: ${user.username}</option>`;
                    });
                    options += '</optgroup>';
                    options += '<optgroup label="Employees">';
                    data.employeeData.forEach((user) => {
                        options += `<option value="${user.username}">Employee: ${user.username}</option>`;
                    });
                    options += '</optgroup>';
                }

                userSelect.innerHTML = options;
            });
        }

        // Store the action type in the confirm button's data attribute
        confirmButton.dataset.actionType = actionType;

        // Show the modal
        modal.style.display = 'block';
    }

    // Close the modal
    function closeModal() {
        modal.style.display = 'none';
    }

    // Close the Add User modal
    function closeAddModal() {
        addModal.style.display = 'none';
    }
	
	document.getElementById('confirmAddBtn').onclick = function() {
	    const username = document.getElementById('username').value;
	    const password = document.getElementById('password').value;
	    const role = document.getElementById('role').value;

	    if (username && password && role) {
	        addUser(username, password, role);
	    } else {
	        alert('Please provide all details to add a user.');
	    }
	};

    // Confirm Action Button (Delete/Promote/Add User)
    document.getElementById('confirmActionBtn').onclick = function() {
        const actionType = this.dataset.actionType;
        const selectedUser = userSelect.value;

        if (actionType === 'promote') {
            if (!selectedUser) {
                alert('Please select a user to promote.');
                return;
            }
            promoteUser(selectedUser);
        } else if (actionType === 'delete') {
            if (!selectedUser) {
                alert('Please select a user to delete.');
                return;
            }
            deleteUser(selectedUser);
        }

        closeModal(); // Close the modal after action
    };
	

    // Populate user tables (Managers/Employees)
    function populateTable(type, users) {
        const tableBody = document.querySelector(`.${type}-table tbody`);
        tableBody.innerHTML = ''; // Clear existing rows
        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.username}</td>
                <td>${user.role}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    // Add User function
    function addUser(username, password, role) {
        fetch('/employee_management/admin/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}&role=${encodeURIComponent(role)}`,
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add user');
            }
            return response.text();
        })
        .then(() => {
            alert(`Người dùng ${username} đã được thêm thành công.`);
            fetchUsers(); // Refresh the tables
            closeAddModal(); // Close the Add Modal
        })
        .catch(error => console.error('Error adding user:', error));
    }

    // Delete User function
    function deleteUser(username) {
        if (confirm(`Are you sure you want to delete user ${username}?`)) {
            fetch('/employee_management/admin/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `username=${encodeURIComponent(username)}`,
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete user');
                }
                return response.text();
            })
            .then(() => {
                alert(`User ${username} deleted successfully.`);
                fetchUsers(); // Refresh the table
            })
            .catch(error => console.error('Error deleting user:', error));
        }
    }

    // Promote User function
    function promoteUser(username) {
        if (confirm(`Are you sure you want to promote user ${username} to Manager?`)) {
            fetch('/employee_management/admin/promote', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `username=${encodeURIComponent(username)}`,
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to promote user');
                }
                return response.text();
            })
            .then(() => {
                alert(`User ${username} promoted successfully.`);
                fetchUsers(); // Refresh the table
            })
            .catch(error => console.error('Error promoting user:', error));
        }
    }
});