const domen = 'http://localhost:8080'
const EMAIL_REGEXP = /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/iu;

function sendData() {
    console.log('123123')
    let name = document.getElementById('name').value
    let mail = document.getElementById('mail').value
    let topic = document.getElementById('topic').value
    let message = document.getElementById('message').value

    if (name != null && mail != null && topic != null && message != null) {
        postData(domen + '/contacts/sendMessage', {
            user: name,
            theme: topic,
            email: mail,
            message: message
        }).then((data) => {
            if (data.status === 200) {
                window.location.href = domen + '/contacts';
            } else {
                alert(data.status + ": " + data.json().error);
            }
        });
    } else {
        alert('')
    }
}


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
