console.log('Login JavaScript loaded');
document.addEventListener('DOMContentLoaded', function(e) {
	console.log("Form submitted"); // Log form submission
	e.preventDefault(); // Prevent the default form submission
    const loginForm = document.getElementById('loginForm');
    const messageDiv = document.getElementById('message');

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent the default form submission

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
		console.log("Sending login request with:", { username, password }); // Debugging log
        // Send the login request to the server
        fetch('/employee_management/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        })
        .then(function(response) {
			console.log("Response received"); // Log response receipt
            return response.json();
        })
        .then(function(result) {
			console.log("Login result:", result);
            if (result.success) {
                messageDiv.textContent = 'Login successful!';
                messageDiv.style.color = 'green';
                window.location.href = result.redirectUrl; // Redirect based on server response
            } else {
                messageDiv.textContent = 'Invalid username or password.';
                messageDiv.style.color = 'red';
            }
        })
        .catch(function(error) {
            console.error('Error during login:', error);
            messageDiv.textContent = 'An error occurred. Please try again.';
            messageDiv.style.color = 'red';
        });
    });
});
