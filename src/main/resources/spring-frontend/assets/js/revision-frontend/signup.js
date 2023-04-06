let domen = 'http://localhost:8080'
let signUpEndpoint = '/signup'

async function postData(url = '', data = {}) {
    // Default options are marked with *
    return await fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *client
        body: JSON.stringify(data) // body data type must match "Content-Type" header
    }); // parses JSON response into native JavaScript objects
}

async function registerUser() {
    let name = document.getElementById('name').value;
    let email = document.getElementById("mail").value;
    let phone = document.getElementById('phone').value;
    postData(domen + '/signup', {name: name, phone: phone, email: email})
        .then((data) => {
            if (data.status === 200) {
                window.location.href = domen;
            } else {
                alert(data.status + ": " + data.json().error);
            }
    });
}