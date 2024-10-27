document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const messageDiv = document.getElementById('message');

    loginForm.addEventListener('submit', (e) => {
        e.preventDefault(); // Prevent the default form submission

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Simulate a login check
        if (username === 'admin' && password === 'admin') {
            messageDiv.textContent = 'Login successful!';
            messageDiv.style.color = 'green';
            // Redirect to another page or perform further actions here
        } else {
            messageDiv.textContent = 'Invalid username or password.';
            messageDiv.style.color = 'red';
        }
    });
});
