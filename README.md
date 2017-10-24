```
const loginForm = document.getElementById('login');
const apiUrl = 'https://arcane-hollows-90989.herokuapp.com/';
const usernameSpan = document.getElementById('username');

function setLoginName() {
  fetch(apiUrl + 'user', { credentials: 'include' })
    .then(response => response.json())
    .then(data => {
      if (data.name !== undefined) {
        usernameSpan.innerText = data.name;
      } else {
        usernameSpan.innerText = 'NOBODY LOGGED IN';
      }
    });
}

loginForm.addEventListener('submit', function (event) {
  const name = document.getElementById('name').value;
  const password = document.getElementById('password').value;

  const fetchOpts = { 
    method: 'POST',
    body: JSON.stringify({ name: name, password: password }),
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json'
    }
  };

  // take the name and password and issue a request
  fetch(apiUrl + 'login', fetchOpts)
    .then(setLoginName);

  // this stops the form from submitting
  event.preventDefault();
});

setLoginName();
```
