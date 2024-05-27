const apiUrl = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {

    // Login form
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(loginForm);
            const data = {
                username: formData.get('username'),
                password: formData.get('password')
            };

            try {
                const response = await fetch(`${apiUrl}/authenticate`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    throw new Error('Login failed');
                }

                const result = await response.json();
                localStorage.setItem('token', result.token);
                alert('Login successful!');
                window.location.href = 'index.html';
            } catch (error) {
                alert(error.message);
            }
        });
    }

    // Register form
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(registerForm);
            const data = {
                login: formData.get('login'),
                password: formData.get('password'),
                fullName: formData.get('fullName'),
                dateOfBirth: formData.get('dateOfBirth'),
                email: formData.get('email'),
                phoneNumber: formData.get('phoneNumber'),
                bankAccount: {
                    initialDeposit: formData.get('initialDeposit'),
                    balance: formData.get('initialDeposit')  // assuming balance is the initial deposit
                }
            };

            try {
                const response = await fetch(`${apiUrl}/users/register`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    throw new Error('Registration failed');
                }

                alert('Registration successful!');
                window.location.href = 'login.html';
            } catch (error) {
                alert(error.message);
            }
        });
    }

    // Transfer form
    const transferForm = document.getElementById('transferForm');
    if (transferForm) {
        transferForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const token = localStorage.getItem('token');
            if (!token) {
                alert('Please login first');
                return;
            }

            const formData = new FormData(transferForm);
            const data = {
                toUserId: formData.get('toUserId'),
                amount: formData.get('amount')
            };

            try {
                const response = await fetch(`${apiUrl}/accounts/transfer`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    throw new Error('Transfer failed');
                }

                alert('Transfer successful!');
            } catch (error) {
                alert(error.message);
            }
        });
    }

    // Search form
    const searchForm = document.getElementById('searchForm');
    if (searchForm) {
        searchForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const token = localStorage.getItem('token');
            if (!token) {
                alert('Please login first');
                return;
            }

            const formData = new FormData(searchForm);
            const queryParameters = new URLSearchParams();
            for (let [key, value] of formData.entries()) {
                if (value) {
                    queryParameters.append(key, value);
                }
            }

            try {
                const response = await fetch(`${apiUrl}/search?${queryParameters.toString()}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (!response.ok) {
                    throw new Error('Search failed');
                }

                const results = await response.json();
                const resultsDiv = document.getElementById('searchResults');
                resultsDiv.innerHTML = '<h3>Search Results:</h3>';

                results.forEach(user => {
                    const userDiv = document.createElement('div');
                    userDiv.textContent = `ID: ${user.id}, Name: ${user.fullName}, Email: ${user.email}, Phone: ${user.phoneNumber}`;
                    resultsDiv.appendChild(userDiv);
                });
            } catch (error) {
                alert(error.message);
            }
        });
    }

});
