var modal = document.getElementById("myModal");
var btn = document.getElementById("openModal");
var span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
    modal.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
function findUser(){
    const query = document.getElementById("querypage").value;
    const url = `http://localhost:8081/find-user-by/${query}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => console.log(error));

}