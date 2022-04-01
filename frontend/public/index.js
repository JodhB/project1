let loginBtn = document.querySelector('#login-btn');

loginBtn.addEventListener('click', async () => {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    const URL = 'http://34.125.243.234:8080/login';

    const jsonString = JSON.stringify({
        "username": usernameInput.value,
        "password": passwordInput.value
    });

    let res = await fetch(URL, {
        method: 'POST',
        body: jsonString,
    });

    if (res.status === 200) {
        let user = await res.json();

        let token = res.headers.get('Token');
        localStorage.setItem('jwt', token);
        localStorage.setItem('user_id', user.id);

        if (user.role === 'Manager') {
            window.location = '/manager-page.html';
        } else if (user.role === 'Employee') {
            window.location = '/employee-page.html';
        }
    } else {
        let errorMsg = await res.text();
        console.log(errorMsg);

        let errorElement = document.querySelector('#error-msg');
        errorElement.innerText = errorMsg;
        errorElement.style.color = 'red';
    }
});