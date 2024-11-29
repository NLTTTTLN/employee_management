document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    const toggleButton = document.querySelector('.toggle-btn');
    const addModal = document.getElementById('addModal');
    const editSelectModal = document.getElementById('editSelectModal');
    const editModal = document.getElementById('editModal');
    const deleteModal = document.getElementById('deleteModal');
    const closeAddBtn = document.getElementById('closeAddBtn');
    const closeEditSelectBtn = document.getElementById('closeEditSelectBtn');
    const closeEditBtn = document.getElementById('closeEditBtn');
    const closeDeleteBtn = document.getElementById('closeDeleteBtn');
    const confirmAddBtn = document.getElementById('confirmAddBtn');
    const confirmSelectBtn = document.getElementById('confirmSelectBtn');
    const confirmEditBtn = document.getElementById('confirmEditBtn');
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
    const employeeListEdit = document.getElementById('employeeListEdit');
    const employeeList = document.getElementById('employeeList');

    // SIDEBAR SCRIPT
    window.toggleSidebar = function() {
        if (sidebar) {
            sidebar.classList.toggle('collapsed');
            const elements = sidebar.querySelectorAll('.logo, .nav-links, .bottom-link');
            elements.forEach(element => element.style.opacity = sidebar.classList.contains('collapsed') ? '0' : '1');
        }
    };

    // FETCHING DATA FROM DATABASE
    function fetchDashboardData() {
        fetch('/employee_management/manager/dashboard-info')
            .then(response => response.json())
            .then(data => {
                const employeeCount = document.getElementById('employeeCount');
                if (employeeCount) {
                    employeeCount.textContent = data.employeeCount;
                }
            })
            .catch(error => console.error('Error fetching dashboard data:', error));
    }
    fetchDashboardData();

    function fetchEmployee() {
        fetch('/employee_management/manager/management-data')
            .then(response => response.json())
            .then(data => {
                populateTable('employees', data.employeeData);
                populateEmployeeUsername(data.employeeData);
            })
            .catch(error => console.error('Error fetching user data:', error));
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
                        alert("Please enter a valid email.");
                    }
                } else {
                    alert('Please enter a valid phone number.');
                }
            } else {
                alert('Please fill out all fields.');
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
                    throw new Error(`Failed to add user: ${errorMessage}`);
                });
            }
            return response.text();
        })
        .then(() => {
            alert(`User ${username} added successfully.`);
            fetchEmployee(); // Refresh employee data
            closeAddModal(); // Close the Add Modal
        })
        .catch(error => {
            console.error('Error adding user:', error);
            alert(`Error: ${error.message}`);
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
            if (employeeList) {
                const options = employeeList.getElementsByTagName('option');
                Array.from(options).forEach(option => {
                    option.style.display = option.textContent.toLowerCase().includes(searchQuery) ? 'block' : 'none';
                });
            }
        });
    }

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
                alert('No user selected for deletion.');
            }
        };
    }

    function deleteUser(username) {
        if (confirm(`Are you sure you want to delete user ${username}?`)) {
            fetch('/employee_management/manager/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `username=${encodeURIComponent(username)}`
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete user');
                }
                return response.text();
            })
            .then(() => {
                alert(`User ${username} deleted successfully.`);
                fetchEmployee(); // Refresh employee data
            })
            .catch(error => console.error('Error deleting user:', error));
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
            if (employeeListEdit) {
                const options = employeeListEdit.getElementsByTagName('option');
                Array.from(options).forEach(option => {
                    option.style.display = option.textContent.toLowerCase().includes(searchQuery) ? 'block' : 'none';
                });
            }
        });
    }

    function selectEditEmployee() {
        const selectEditUsername = document.getElementById('employeeListEdit').value;
        const selectUsername = document.getElementById('selectEditUsername');
        if (selectUsername) {
            selectUsername.value = selectEditUsername;
        }
    }

    if (employeeListEdit) {
        employeeListEdit.addEventListener('change', function() {
            selectEditEmployee();
        });
    }

    if (confirmSelectBtn) {
        confirmSelectBtn.onclick = function() {
            const selectEditUsername = document.getElementById('selectEditUsername').value;
            if (selectEditUsername) {
                openEditModal(selectEditUsername);
            } else {
                alert('Please select an employee.');
            }
        };
    }

    function openEditModal(username) {
        if (editModal) {
            // Fetch the employee data to pre-fill the form
            fetch(`/employee_management/manager/get-employee?username=${encodeURIComponent(username)}`)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('editUsername').value = data.username;
                    document.getElementById('editName').value = data.name;
                    document.getElementById('editGender').value = data.gender;
                    document.getElementById('editDob').value = data.dob;
                    document.getElementById('editEmail').value = data.email;
                    document.getElementById('editPhone_num').value = data.phone_num;
                    document.getElementById('editAddress').value = data.address;
                    document.getElementById('editDepartment').value = data.department;
                    document.getElementById('editSalary').value = data.salary;
                    editModal.style.display = 'block';
                })
                .catch(error => console.error('Error fetching employee data:', error));
        }
    }

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
                alert('Please fill out all fields.');
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
                    throw new Error(`Failed to edit user: ${errorMessage}`);
                });
            }
            return response.text();
        })
        .then(() => {
            alert(`User ${username} edited successfully.`);
            fetchEmployee(); // Refresh employee data
            closeEditModal(); // Close the Edit Modal
        })
        .catch(error => {
            console.error('Error editing user:', error);
            alert(`Error: ${error.message}`);
        });
    }
});
