let modal = document.getElementById("myModal");
let btn = document.getElementById("openModal");
let span = document.getElementById("close-change-user");
let userId = null;
let userSlug = null;
let authorSlug = null;
let selectedAuthorSlugs = "";
// Получаем ссылку на модальное окно
const modalWindow = document.getElementById("authorChange");

const tagList = [];
let tagSlug = null
// Получаем кнопку, которая будет открывать модальное окно
const openModalBtn = document.getElementById("authorChangeButton");
let allFiles = [];

// Получаем элемент, который будет закрывать модальное окно
const closeModalBtn = document.getElementById('close-change-user');//TODO: создать новую переменную
const dropZone = document.getElementById('drop-zone');
let bookSlug = null;
const bookChangeButton = document.getElementById('bookChangeButton');
const bookChangesModal = document.getElementById('bookChanges');

 function addTagToTagList() {
     const result = document.getElementById("search-result-tag-add-book-1");
    if (!tagList.includes(tagSlug)) {
        tagList.push(tagSlug);
        alert('Теги: ' + tagList);
    } else {
        alert(`Тег "${tagSlug}" уже добавлен`);
    }
     result.innerHTML = '';
}

function addTagToTagList2() {
    const result = document.getElementById("search-result-tag-add-book-2");
    if (!tagList.includes(tagSlug)) {
        tagList.push(tagSlug);
        alert('Теги: ' + tagList);
    } else {
        alert(`Тег "${tagSlug}" уже добавлен`);
    }
    result.innerHTML = '';
}


['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, preventDefaults, false);
});

function preventDefaults (e) {
    e.preventDefault();
    e.stopPropagation();
}

['dragenter', 'dragover'].forEach(eventName => {
    dropZone.addEventListener(eventName, highlight, false);
});

['dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, unhighlight, false);
});

// Получаем ссылки на элементы
const modal3 = document.getElementById("myModal2");
const btn3 = document.getElementById("addBooksToUser");
const span3 = document.getElementsByClassName("close")[4];

// Открываем модальное окно при клике на кнопку
btn3.onclick = function() {
    modal3.style.display = "block";
}

// Закрываем модальное окно при клике на крестик
span3.onclick = function() {
    modal3.style.display = "none";
}


// Закрываем модальное окно при клике вне его области
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


window.onclick = function(event) {
    if (event.target === modal3) {
        modal3.style.display = "none";
    }
}


function highlight() {
    dropZone.classList.add('highlight');
}

function unhighlight() {
    dropZone.classList.remove('highlight');
}

dropZone.addEventListener('drop', handleDrop, false);

function handleDrop(e) {
    const dt = e.dataTransfer;
    const files = dt.files;
    handleFiles(files);
}


function handleFiles(files) {
    const fileInput = document.getElementById('bookFiles');
    fileInput.style.display = 'block';
    const items = Array.from(files);
    items.forEach(file => {
        if (!allFiles.includes(file)) {
            allFiles.push(file);
        }
    });
    const dt = new DataTransfer();
    allFiles.forEach(file => {
        dt.items.add(file);
    });
    fileInput.files = dt.files;
}


dropZone.addEventListener('dragenter', (event) => {
    event.preventDefault();
    dropZone.classList.add('dragging');
});

dropZone.addEventListener('dragover', (event) => {
    event.preventDefault();
});

dropZone.addEventListener('dragleave', (event) => {
    event.preventDefault();
    dropZone.classList.remove('dragging');
});

dropZone.addEventListener('drop', (event) => {
    event.preventDefault();
    dropZone.classList.remove('dragging');
    const files = event.dataTransfer.files;
    handleFiles(files);
});

btn.onclick = function() {
    modal.style.display = "block";
    span = document.getElementById("close-change-user");
    span.onclick = function () {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
}

window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

// Добавляем обработчик события на кнопку открытия модального окна
openModalBtn.onclick = function() {
    modalWindow.style.display = "block";
    let span = document.getElementById('author-change-close')
    span.onclick = function(){
        modalWindow.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modalWindow.style.display = "none";
        }
    }
}

window.onclick = function(event) {
    if (event.target === modalWindow) {
        modalWindow.style.display = "none";
    }
}
// Добавляем обработчик события на элемент закрытия модального окна
closeModalBtn.onclick = function() {
    modalWindow.style.display = "none";
}

// Добавляем обработчик события на щелчок за пределами модального окна, чтобы закрыть его
window.onclick = function(event) {
    if (event.target === modalWindow) {
        modalWindow.style.display = "none";
    }
}

bookChangeButton.addEventListener('click', () => {
    bookChangesModal.style.display = 'block';
});

// добавляем обработчик события на кнопку закрытия модального окна
const closeModalButton = bookChangesModal.querySelector('.close');
closeModalButton.addEventListener('click', () => {
    bookChangesModal.style.display = 'none';
});

// закрываем модальное окно, когда пользователь нажимает вне окна
window.addEventListener('click', (event) => {
    if (event.target === bookChangesModal) {
        bookChangesModal.style.display = 'none';
    }
});

const editButton = document.getElementById('change-review-btn');
const modalChangeReview = document.getElementById('modal-change-review');

editButton.addEventListener('click', function() {
    modalChangeReview.style.display = 'block';
});

window.addEventListener('click', (event) => {
    if (event.target === modalChangeReview) {
        modalChangeReview.style.display = 'none';
    }
});

const findDelBookButton = document.getElementById('find-del-book-btn');
const findDelBookModal = document.getElementById('modal-delete-book');

findDelBookButton.addEventListener('click', function() {
    findDelBookModal.style.display = 'block';
});

window.addEventListener('click', (event) => {
    if (event.target === findDelBookModal) {
        findDelBookModal.style.display = 'none';
    }
});
function findUser(){
    const query = document.getElementById("querypage").value;
    const url = `http://localhost:8081/cms/user/find-user-by/${query}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(query)
            if(query === null || query === ''){
                throw new Error('Введите значение в строку поиска.')
            }
            // обрабатываем ответ от сервера
            const resultElement = document.getElementById('search-result');
            resultElement.innerHTML = `<div style="display: flex;">
                <div style="padding-left: 20px">Имя: <span style="padding-left: 5px" contenteditable="true" data-property="name">${data.name}</span></div>
                <div style="padding-left: 20px">   Email: <span style="padding-left: 5px" contenteditable="true" data-property="email">${Object.keys(data.phoneAndApprove)[0]}</span></div>
                <div style="padding-left: 20px">   Телефон: <span style="padding-left: 5px" contenteditable="true" data-property="phone" data-mask="+7 (999) 999-99-99" data-validate="require">${Object.keys(data.emailAndApprove)[0]}</span></div>
                <div style="padding-left: 20px">   Баланс: <div data-property="balance">${data.balance}</div></div>
            </div>
            <div class="form-group form-group_center" style="padding-top: 10px">
                            <button onclick="sendEditUser()" id="register" class="btn btn_primary form-btn" type="submit">Отправить
                            </button>
             </div>`;
            userId = data.id;
        })
        .catch(error => console.log(error));
}
function findUserAddBook(){
    const query = document.getElementById("query-page-find-user").value;
    const url = `http://localhost:8081/cms/user/find-user-by/${query}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // обрабатываем ответ от сервера
            const resultElement = document.getElementById('search-result-user-add-book');
            resultElement.innerHTML = `<div style="display: flex;">
                <div style="padding-left: 20px">Имя: <span style="padding-left: 5px" contenteditable="true" data-property="name">${data.name}</span></div>
                <div style="padding-left: 20px">   Email: <span style="padding-left: 5px" contenteditable="true" data-property="email">${Object.keys(data.phoneAndApprove)[0]}</span></div>
                <div style="padding-left: 20px">   Телефон: <span style="padding-left: 5px" contenteditable="true" data-property="phone" data-mask="+7 (999) 999-99-99" data-validate="require">${Object.keys(data.emailAndApprove)[0]}</span></div>
                 <div style="padding-left: 20px">   Баланс: <div data-property="balance">${data.balance}</div></div>
                 <button id="find-user-add-book" class="btn btn_primary form-btn" type="submit" onclick="addUserSlug('${data.id}')">Отправить</button>
            </div>`;
        })
        .catch(error => console.log(error));
}

function addUserSlug(userSlugParam){
    userSlug = userSlugParam;
    const resultElement = document.getElementById('search-result-user-add-book');
    resultElement.innerHTML = '<input class="search-input" id="query-page-find-user" name="query-page-book-change" type="text" placeholder="Найти книгу по ее названию" />';
    console.log(userSlug)
    alert('Пользователь успешно добавлен. Slug: ' + userSlugParam + '. Выберите нужные книги.')
    const query = document.getElementById("query-page-find-user").value;
    query.innerHTML = '<input class="search-input" id="query-page-find-user" name="query-page-book-change" type="text" placeholder="Найти автора" />'
}

function sendEditUser() {
    const id = userId; // получаем значение идентификатора пользователя
    const name = document.querySelector('span[data-property="name"]').textContent; // получаем значение имени пользователя из HTML-разметки
    const phone = document.querySelector('span[data-property="email"]').textContent; // получаем значение email пользователя из HTML-разметки
    const email = document.querySelector('span[data-property="phone"]').textContent; // получаем значение телефона пользователя из HTML-разметки

    const payload = {
        name: name,
        email: email,
        phone: phone
    };

    fetch(`http://localhost:8081/cms/user/edit/${id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(response => response.json())
        .then(data => {
            try {
                if(data.error){
                    alert('Произошла ошибка! Ошибка: '+ data.error)
                    return;
                }
                if(data.result === true){
                    alert('Пользователь успешно изменен!')
                }
            }catch(error){
                alert('Пользователь успешно изменен!')
            }
        })
}

function findAuthor(){
    const resultContainer = document.getElementById('search-result-author-change');
    const name = document.getElementById('query-page-author-change').value;
    const uri = `/api/authors/find-author-by-name/${encodeURIComponent(name)}`;

    fetch(uri)
        .then(response => {
            if (response.status !== 200) {
                let responseError = response.json();
                throw new Error(responseError.error); // выбрасываем ошибку с текстом из поля error
            }
            return response.json();
        })
        .then(data => {
            resultContainer.innerHTML = `<div>
                <div>Имя: <span id = "change-author-name"  style="padding-left: 5px" contenteditable="true">${data.name}</idspan></div>
                <div>   Описание: <span id = "change-author-description"   style="padding-left: 5px" contenteditable="true">${data.description}</span></div>
            </div>
            <div class="form-group form-group_center" style="padding-top: 10px">
                            <button id="register" class="btn btn_primary form-btn" type="submit" onclick="sendEditAuthor()">Отправить
                            </button>
             </div>`;
            authorSlug = data.slug;
        })
        .catch(() => {
            alert(`Ошибка: Автор не найден`); // выводим ошибку в алерте
        });
}

// function findAuthor(){
//     const resultContainer = document.getElementById('search-result-author-change');
//     const name = document.getElementById('query-page-author-change').value;
//     const uri = `/api/authors/find-author-by-name/${encodeURIComponent(name)}`;
//
//     fetch(uri)
//         .then(response => {
//             if (response.status !== 200) {
//                 let responseError = response.json();
//                 throw new Error(responseError.error); // выбрасываем ошибку с текстом из поля error
//             }
//             return response.json();
//         })
//         .then(data => {
//             resultContainer.innerHTML = `<div>
//                 <div>Имя: <span id = "change-author-name"  style="padding-left: 5px" contenteditable="true">${data.name}</idspan></div>
//                 <div>   Описание: <span id = "change-author-description"   style="padding-left: 5px" contenteditable="true">${data.description}</span></div>
//             </div>
//             <div class="form-group form-group_center" style="padding-top: 10px">
//                             <button id="register" class="btn btn_primary form-btn" type="submit" onclick="sendEditAuthor()">Отправить
//                             </button>
//              </div>`;
//             authorSlug = data.slug;
//         })
//         .catch(() => {
//             alert(`Ошибка: Автор не найден`); // выводим ошибку в алерте
//         });
// }


function findAuthorAddBook(){
    const resultContainer = document.getElementById('search-result-author-change-2');
    const name = document.getElementById('query-page-author-add-book').value;
    const uri = `/api/authors/find-author-by-name/${encodeURIComponent(name)}`;
    console.log(123)
    fetch(uri)
        .then(response => {
            return response.json();
        })
        .then(data => {
            resultContainer.innerHTML = `<div id="block-find-author-add-book" class="change-author-container" onclick="addAuthor('${data.slug}')"
                                             onmouseover="this.style.cursor='pointer'; this.classList.add('hovered');"
                                             onmouseout="this.classList.remove('hovered');">
                                            <div id="change-author-description" style="padding-left: 5px; padding-top: 30px;">Имя: ${data.name}</div>
                                            <img src="${data.photo}" width="100px" style="padding-left: 5px; padding-top: 30px;" height="130px">
                                        </div>
                                        <div id="author-link-find-book" style="padding-left: 5px; padding-top: 30px;">
                                                <a href="http://localhost:8081/authors/${data.slug}" target="_blank">Cсылка на автора</a>
                                            </div>`;
            authorSlug = data.slug;
            const changeAuthorContainer = document.getElementById("block-find-author-add-book");

            changeAuthorContainer.addEventListener("mouseover", () => {
                changeAuthorContainer.classList.add("hovered");
            });
            changeAuthorContainer.addEventListener("mouseout", () => {
                changeAuthorContainer.classList.remove("hovered");
            });
        })
        .catch(() => {
            alert(`Ошибка: Автор не найден`); // выводим ошибку в алерте
        });
}

function findAuthorAddBook2(){
    const resultContainer = document.getElementById('search-result-author-change-1');
    const name = document.getElementById('query-page-author-add-book').value;
    const uri = `/api/authors/find-author-by-name/${encodeURIComponent(name)}`;
    fetch(uri)
        .then(response => {
            return response.json();
        })
        .then(data => {
            resultContainer.innerHTML = `<div id="block-find-author-add-book" class="change-author-container" onclick="addAuthor2('${data.slug}')"
                                             onmouseover="this.style.cursor='pointer'; this.classList.add('hovered');"
                                             onmouseout="this.classList.remove('hovered');">
                                            <div id="change-author-description" style="padding-left: 5px; padding-top: 30px;">Имя: ${data.name}</div>
                                            <img src="${data.photo}" width="100px" style="padding-left: 5px; padding-top: 30px;" height="130px">
                                        </div>
                                        <div id="author-link-find-book" style="padding-left: 5px; padding-top: 30px;">
                                                <a href="http://localhost:8081/authors/${data.slug}" target="_blank">Cсылка на автора</a>
                                            </div>`;
            authorSlug = data.slug;
            const changeAuthorContainer = document.getElementById("block-find-author-add-book");

            changeAuthorContainer.addEventListener("mouseover", () => {
                changeAuthorContainer.classList.add("hovered");
            });
            changeAuthorContainer.addEventListener("mouseout", () => {
                changeAuthorContainer.classList.remove("hovered");
            });
        })
        .catch(() => {
            alert(`Ошибка: Автор не найден`); // выводим ошибку в алерте
        });
}
function findAuthorEditBook(){
    const resultContainer = document.getElementById('search-result-author-change-2');
    const name = document.getElementById('query-page-change-book').value;
    const uri = `/api/authors/find-author-by-name/${encodeURIComponent(name)}`;

    fetch(uri)
        .then(response => {
            if (response.status !== 200) {
                let responseError = response.json();
                throw new Error(responseError.error); // выбрасываем ошибку с текстом из поля error
            }
            console.log(response.status)
            return response.json();
        })
        .then(data => {
            resultContainer.innerHTML = `<div id="block-find-author-add-book" class="change-author-container" onclick="addAuthor1('${data.slug}' ,'${data.name}')"></div>)`;
            console.log(resultContainer)
            authorSlug = data.slug;
            const changeAuthorContainer = document.getElementById("block-find-author-add-book");

            changeAuthorContainer.addEventListener("mouseover", () => {
                changeAuthorContainer.classList.add("hovered");
            });
            changeAuthorContainer.addEventListener("mouseout", () => {
                changeAuthorContainer.classList.remove("hovered");
            });

            const dropdown1 = document.querySelector('.dropdown1');
            const content = dropdown1.querySelector('.dropdown-content1');
            console.log(content)
            dropdown1.addEventListener('click', (e) => {
                content.classList.toggle('active');
                content.style.display = content.style.display === 'block' ? 'none' : 'block';
            });
        })
        .catch(() => {
            alert(`Ошибка: Автор не найден`); // выводим ошибку в алерте
        });
}


const authors = [];

function addAuthor(slug) {
    const resultContainer1 = document.getElementById("search-result-author-change-2");
    resultContainer1.innerHTML = ''
    authors.push(slug);
    alert("Авторы: "+ authors)
}

function addAuthor2(slug) {
    const resultContainer1 = document.getElementById("search-result-author-change-1");
    resultContainer1.innerHTML = ''
    authors.push(slug);
    alert("Авторы: "+ authors)
}
function addAuthor1(slug, name) {
    const resultContainer1 = document.getElementById("search-result-author-change-2");
    const listItem = document.createElement('li');
    const textNode = document.createTextNode('Имя: ' + name + ' Slug: ' + slug);
    const deleteButton = document.createElement('span');
    const list1 = document.getElementById('list1');
    resultContainer1.innerHTML = ''
    authors.push(slug);
    alert("Авторы: "+ authors)

    deleteButton.className = 'delete-btn1';
    deleteButton.appendChild(document.createTextNode('-'));
    listItem.appendChild(textNode);
    listItem.appendChild(deleteButton);
    list1.appendChild(listItem);

    const deleteBtns1 = document.querySelectorAll('.delete-btn1');
    deleteBtns1.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.stopPropagation();
            const listItem = e.target.parentNode;
            list1.removeChild(listItem);
        });
    });

    const dropdown1 = document.querySelector('.dropdown1');
    const content = dropdown1.querySelector('.dropdown-content1');
    console.log(content)
    dropdown1.addEventListener('click', (e) => {
        content.classList.toggle('active');
        content.style.display = content.style.display === 'block' ? 'none' : 'block';
    });
}
function findTag(){
    const input = document.getElementById("query-page-tag-add-book-1");
    const result = document.getElementById("search-result-tag-add-book-2");
    const query = input.value;
    const url = `http://localhost:8081/api/tags/find-by-tag-name/${query}`;
    fetch(url, { method: "GET", headers: { "Content-Type": "application/json" } })
        .then((response) => {return response.json()})
        .then((data) => {
            const tagLink = `http://localhost:8081/tags/${data.slug}`;
            result.innerHTML = `<div>
                    <div id="tag-find-name" class="hoverable" onclick="addTagToTagList()">
                        <div style="padding-top: 30px; padding-bottom: 30px">Название: ${data.name}</div>
                    </div>
                    <a href="${tagLink}" target="_blank">Ссылка на тег</a>
                </div>`;
            tagSlug = data.slug;
            const list = document.getElementById('list');

            const listItem = document.createElement('li');
            const textNode = document.createTextNode('Имя: ' + data.name + ' Slug: ' + data.slug);
            const deleteButton = document.createElement('span');
            deleteButton.className = 'delete-btn';
            deleteButton.appendChild(document.createTextNode('-'));
            listItem.appendChild(textNode);
            listItem.appendChild(deleteButton);
            list.appendChild(listItem);

            const deleteBtns = document.querySelectorAll('.delete-btn');
            deleteBtns.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    e.stopPropagation();
                    const listItem = e.target.parentNode;
                    list.removeChild(listItem);
                });
            });
        })

}

function findTag2(){
    const input = document.getElementById("query-page-tag-add-book-2");
    const result = document.getElementById("search-result-tag-add-book-1");
    const query = input.value;
    const url = `http://localhost:8081/api/tags/find-by-tag-name/${query}`;
    fetch(url, { method: "GET", headers: { "Content-Type": "application/json" } })
        .then((response) => {return response.json()})
        .then((data) => {
            const tagLink = `http://localhost:8081/tags/${data.slug}`;
            result.innerHTML = `<div>
                    <div id="tag-find-name" class="hoverable" onclick="addTagToTagList()">
                        <div style="padding-top: 30px; padding-bottom: 30px">Название: ${data.name}</div>
                    </div>
                    <a href="${tagLink}" target="_blank">Ссылка на тег</a>
                </div>`;
            tagSlug = data.slug;
            const list = document.getElementById('list');

            const listItem = document.createElement('li');
            const textNode = document.createTextNode('Имя: ' + data.name + ' Slug: ' + data.slug);
            const deleteButton = document.createElement('span');
            deleteButton.className = 'delete-btn';
            deleteButton.appendChild(document.createTextNode('-'));
            listItem.appendChild(textNode);
            listItem.appendChild(deleteButton);
            list.appendChild(listItem);

            const deleteBtns = document.querySelectorAll('.delete-btn');
            deleteBtns.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    e.stopPropagation();
                    const listItem = e.target.parentNode;
                    list.removeChild(listItem);
                });
            });
        })

}

function findTagSlug(){
    const input = document.getElementById("query-page-tag-add-book-1");
    const result = document.getElementById("search-result-tag-add-book-2");
    const query = input.value;
    const url = `http://localhost:8081/api/tags/find-by-tag-name/${query}`;
    fetch(url, { method: "GET", headers: { "Content-Type": "application/json" } })
        .then((response) => {return response.json()})
        .then((data) => {
            const tagLink = `http://localhost:8081/tags/${data.slug}`;
            result.innerHTML = `<div>
                    <div id="tag-find-name" class="hoverable" onclick="addTagToTagList2()">
                        <div style="padding-top: 30px; padding-bottom: 30px">Название: ${data.name}</div>
                    </div>
                    <a href="${tagLink}" target="_blank">Ссылка на тег</a>
                </div>`;
            tagSlug = data.slug;
            const list = document.getElementById('list');

            const listItem = document.createElement('li');
            const textNode = document.createTextNode('Имя: ' + data.name + ' Slug: ' + data.slug);
            const deleteButton = document.createElement('span');
            deleteButton.className = 'delete-btn';
            deleteButton.appendChild(document.createTextNode('-'));
            listItem.appendChild(textNode);
            listItem.appendChild(deleteButton);
            list.appendChild(listItem);

            const deleteBtns = document.querySelectorAll('.delete-btn');
            deleteBtns.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    e.stopPropagation();
                    const listItem = e.target.parentNode;
                    list.removeChild(listItem);
                });
            });
        })

}

function sendEditAuthor() {
    // Получаем данные из полей формы
    const name = document.getElementById("change-author-name").textContent;
    const description = document.getElementById("change-author-description").textContent;

    // Создаем payload для отправки POST запроса
    const payload = JSON.stringify({ name, description });

    // Отправляем POST запрос на сервер
    fetch(`/cms/authors/edit/${authorSlug}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: payload
    })
        .then(response => {
            if (response.status !== 200) {
                alert(`Произошла внутренняя ошибка сервера!`);
                return response.json();
            }
            const resultContainer = document.getElementById('search-result-author-change');
            resultContainer.innerHTML = ''
            alert(`Автор успешно изменен`);
            return response.json();
        })
}
// Открываем модальное окно при нажатии на кнопку "Добавить книгу"
function addBookBtn2() {
    const modal1 = document.getElementById('modal');
    modal1.style.display = 'block';
    let span = document.getElementById('add-book-close')
    span.onclick = function () {
        modal1.style.display = 'none'
    }
    window.addEventListener('click', (event) => {
        if (event.target === modal1) {
            modal1.style.display = 'none';
        }
    });
}

// Отправляем запрос на сервер при нажатии на кнопку "Сохранить"
function addBookBt2() {
    // Get data from form
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const price = parseInt(document.getElementById("price").value);
    const discount = parseInt(document.getElementById("discount").value);
    const isBestseller = document.getElementById("isBestseller").checked;
    // Create request body
    const formData = new FormData();
    formData.append("isBestseller", isBestseller);
    formData.append("title", title);
    formData.append("description", description);
    formData.append("price", price);
    formData.append("discount", discount);
    authors.forEach((authorId) => {
        formData.append("authorsIds", authorId);
    });
    tagList.forEach((tag) => {
        formData.append("tags", tag);
    });
    for (let i = 0; i < allFiles.length; i++) {
        formData.append("bookFiles", allFiles[i]);
    }

    // Send POST request
    fetch("http://localhost:8081/cms/books/add", {
        method: "POST",
        body: formData,
    })
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            if(data.error){
                throw new Error(data.error)
            }
            alert("Книга добавлена!");
        })
        .catch((error) => {
            alert("Ошибка добавления книги: " + error);
        });
}
const searchButton = document.getElementById('search-page-book-change');



searchButton.addEventListener('click', () => {
    const queryInput = document.getElementById('query-page-book-change');
    const searchResultDiv = document.getElementById('search-result-book-change');
    const form = document.getElementById('book-form');
    const query = queryInput.value;
    const url = `http://localhost:8081/api/books/find-by-name/${query}`;


    fetch(url)
        .then(response => response.json())
        .then(data => {

            searchResultDiv.innerHTML = '<form id="book-form">' +
                            ' <label class="form-input" for="title1">Название:</label>' +
                            ' <input type="text" name="title" id="title1" value="" required>' +
                            ' <br>' +
                            ' <label class="form-input" for="description2">Описание:</label>' +
                            ' <textarea name="description" id="description2" required></textarea>' +
                            ' <br>' +
                            ' <label for="price1" class="form-input">Цена:</label>' +
                            ' <input type="number" name="price" id="price1" required>' +
                            ' <br>' +
                            ' <label for="discount1" class="form-input">Скидка:</label>' +
                            ' <input type="number" name="discount" id="discount1" value="0" required>' +
                            ' <br class="form-input">' +
                            ' <label class="form-input">Авторы:</label>' +
                            ' <br class="form-input">' +
                            ' <form class="form form_search" data-searchlimit="1" style=" display: flex; width: 300px;">' +
                            ' <input class="search-input" id="query-page-change-book" name="query-page-author-change" type="text" placeholder="Найти автора" />' +
                            ' <button class="search-button" type="button" name="search-page-author-change" id="query-page-author-add-book-btn" onclick="findAuthorEditBook()">Поиск</button>' +
                            ' <span id="selected-author-slugs" style="padding-top: 20px"></span>' +
                            ' </form>' +
                            '<div class="dropdown1">\n' +
                            '  <button class="dropbtn1">Авторы</button>\n' +
                            '  <div class="dropdown-content1">\n' +
                            '    <ul id="list1">\n' +
                            '    </ul>\n' +
                            '  </div>\n' +
                            '</div>' +
                            ' <div id="search-result-author-change-2"></div>' +
                            ' <label class="form-input">Теги:</label>' +
                            ' <form class="form form_search" data-searchlimit="1" style="width: 300px;">' +
                            ' <input class="search-input" id="query-page-tag-add-book-1" name="query-page-author-change" type="text" placeholder="Найти тег" />' +
                            ' <button class="search-button" type="button" name="search-page-author-change" id="query-page-tag-add-book-btn" onclick="findTagSlug()">Поиск</button>' +
                            ' </form>' +
                            '<div class="dropdown">\n' +
                            '         <button class="dropbtn">Теги </button>' +
                            '                 <div class="dropdown-content">' +
                            '                      <ul id="list">\n' +
                            '                      </ul>\n' +
                            '                 </div>\n' +
                            '          </div>' +
                            ' <div style="padding-top: 30px" id="search-result-tag-add-book-2"></div>' +
                            ' <br class="form-input">' +
                            '<label for="bookFiles" class="form-input" style="color: red">Форма изменения файлов. Грузите файлы только если хотите заменить существующий на текуще добавленный</label><br class="form-input">' +
                            ' <div><label for="bookFiles" class="form-input">Вставьте PDF:</label><br class="form-input">' +
                            ' <input type="file" id="bookFile1" style="padding-left: 100px" value="Вставьте PDF">' +
                            ' <label for="bookFiles" class="form-input" style="padding-left: 100px" >Вставьте FB2</label><br class="form-input">' +
                            ' <input type="file" id="bookFile2" value="Вставьте FB2">' +
                            ' <label for="bookFiles" class="form-input"  >Вставьте EPUB</label><br class="form-input">' +
                            ' <input type="file" id="bookFile3" value="Вставьте EPUB"> ' +
                            ' <label for="isBestseller1" class="form-input">Бестселлер:</label><br class="form-input">' +
                            '</form><br class="form-input"></div>' +
                            '<input class="form-input" type="checkbox" name="isBestseller" id="isBestseller1" required><div class="form-input"></div>' +
                            '<button id="save-book-btn" class="btn btn_primary btn_outline" onclick="editBookBt2()">Изменить</button>';
            document.getElementById('title1').setAttribute("value", data.title);
            document.getElementById('description2').innerHTML = data.description
            document.getElementById('discount1').setAttribute("value", data.discount);
            document.getElementById('price1').setAttribute( "value", data.price);
            document.getElementById("isBestseller1").checked = data.bestseller;
            bookSlug = data.slug;

            const list = document.getElementById('list');
            const list1 = document.getElementById('list1');
            if(data.tags){
                data.tags.forEach(tag => {
                    const listItem = document.createElement('li');
                    const textNode = document.createTextNode('Имя: ' + tag.name + ' Slug: ' + tag.slug);
                    const deleteButton = document.createElement('span');
                    deleteButton.className = 'delete-btn';
                    deleteButton.appendChild(document.createTextNode('-'));
                    listItem.appendChild(textNode);
                    listItem.appendChild(deleteButton);
                    list.appendChild(listItem);
                });
            }

            const deleteBtns = document.querySelectorAll('.delete-btn');
            deleteBtns.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    e.stopPropagation();
                    const listItem = e.target.parentNode;
                    list.removeChild(listItem);
                });
            });

// Обработчик событий для кнопки списка
            const dropdown = document.querySelector('.dropdown');
            dropdown.addEventListener('click', (e) => {
                dropdown.classList.toggle('active');
                const content = dropdown.querySelector('.dropdown-content');
                content.style.display = content.style.display === 'block' ? 'none' : 'block';
            });

            if(data.authorList){
                data.authorList.forEach(tag => {
                    const listItem = document.createElement('li');
                    const textNode = document.createTextNode('Имя: ' + tag.name + ' Slug: ' + tag.slug);
                    const deleteButton = document.createElement('span');
                    deleteButton.className = 'delete-btn1';
                    deleteButton.appendChild(document.createTextNode('-'));
                    listItem.appendChild(textNode);
                    listItem.appendChild(deleteButton);
                    list1.appendChild(listItem);
                });
            }

            const deleteBtns1 = document.querySelectorAll('.delete-btn1');
            deleteBtns1.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    e.stopPropagation();
                    const listItem = e.target.parentNode;
                    list1.removeChild(listItem);
                });
            });

            const dropdown1 = document.querySelector('.dropdown1');
            const content = dropdown1.querySelector('.dropdown-content1');
            console.log(content)
            dropdown1.addEventListener('click', (e) => {
                content.classList.toggle('active');
                content.style.display = content.style.display === 'block' ? 'none' : 'block';
            });
        })
        .catch(error => {
            console.error(error);
            searchResultDiv.innerText = 'An error occurred';
        });
});
function editBookBt2(){
    let files = []
    let title = document.getElementById('title1').value;
    let description = document.getElementById('description2').value;
    const url = `http://localhost:8081/cms/books/edit/${bookSlug}`;
    let discount = document.getElementById('discount1').value;
    let price = document.getElementById('price1').value;
    let isBestseller = document.getElementById("isBestseller1").value;
    let pdf = document.getElementById("bookFile1");
    let fb2 = document.getElementById("bookFile2");
    let epub = document.getElementById("bookFile3");
    let tagListLocal = ulToList('list');
    let authorListLocal = ulToList('list1');
    const formData = new FormData();
    if(pdf.files[0] != null){
        console.log(pdf.files[0])
        formData.append("bookFiles[]", pdf.files[0]);
    }
    if(fb2.files[0] != null){
        console.log(fb2.files[0])
        formData.append("bookFiles[]", fb2.files[0]);
    }
    if(epub.files[0] != null){
        console.log(epub.files[0])
        formData.append("bookFiles[]", epub.files[0]);
    }
    console.log(discount)
    formData.append("discount", discount)
    formData.append("title", title)
    formData.append("price", price)
    formData.append("description", description)
    console.log(authorListLocal)
    console.log(tagListLocal)
    authorListLocal.forEach(x => formData.append("authorsIds[]", x))
    tagListLocal.forEach(x => formData.append("tags[]", x))
    fetch(url,{
        method: "POST",
        body: formData,
    }).then(response => {
        return response.json();
    }).then(data => {
        if(data.error){
            throw new Error(data.error)
        }
        alert('Книга успешно изменена.')
    }).catch(error => alert(error));
}

function ulToList(tagId){
    let returnList = [];
    const ulElement = document.getElementById(tagId);
    const slug = 'Slug: '

// Получаем все li элементы из ul
    const liElements = ulElement.getElementsByTagName('li');

// Создаем регулярное выражение для поиска текста, начинающегося с "Slug:"
    const regex = /(.+)Slug:(.+)/;
// Проходимся по всем элементам li и получаем текст, начинающийся с "Slug:"
    for (let i = 0; i < liElements.length; i++) {
        const liElement = liElements[i];
        const text = liElement.textContent.trim();
        const match = text.match(regex);
        if (match) {
            const startIndex = text.indexOf(slug);
            const result = text.substring(startIndex+6, text.length - 1);
            returnList.push(result);
            console.log(result); // выводим текст, начинающийся с "Slug:"
        }
    }
    return returnList;
}

function findBookUserAddBook(){
    const input = document.getElementById('query-page-find-user-add-book').value;
    const url = `http://localhost:8081/api/books/find-by-name/${input}`;

    const result = document.getElementById('search-result-add-book-user')
    fetch(url,{
        method: "GET",
    }).then(response => {
        return response.json();
    }).then(data => {
        if(data.error){
            throw new Error(data.error)
        }
        const bookLink = 'http://localhost:8081/books/' + data.slug
        result.innerHTML = `<div>
                                <img src="${data.image}"/>
                                <div id="book-find-author-add">
                                    <div style="padding-top: 30px; padding-bottom: 30px">Название: ${data.title}</div>
                                </div>
                                <a href="${bookLink}" target="_blank">Ссылка на книгу</a>
                                <button id="save-book-btn" class="btn btn_primary btn_outline" onclick="addBookToUser('${data.slug}')">Добавить книгу</button>
                            </div>`;
    }).catch(error => alert(error));
}

function addBookToUser(slug){
    const result = document.getElementById('search-result-add-book-user')
    bookSlug = slug;
    result.innerHTML = ''
}

function addBookToUserPost(){
    const url = `http://localhost:8081/cms/user/add/book`;
    if(userSlug == null || bookSlug == null){
        alert('Укажите книгу и автора')
        return;
    }
    console.log(userSlug)
    console.log(bookSlug)
    const payload = {
        userSlug: userSlug,
        bookSlug: bookSlug
    }
    fetch(url,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    }).then(response => {
        return response.json();
    }).then(data => {
        if(data.error){
            throw new Error(data.error)
        }
        alert('Книга была успешно добавлена!')
    }).catch(error => alert(error));
}
function findReviewBooks() {
    const input = document.getElementById('query-page-find-reviews-book').value;
    const url = `http://localhost:8081/cms/book-review/find-review-by-title-book/${input}`;
    console.log(input)
    if (input === null) {
        alert('Введите полное имя книги в строку поиска.');
        return;
    }
    fetch(url, {
        method: "GET",
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                throw new Error(data.error);
            }
            bookSlug = data.slug;
            const reviews = data.reviews;
            const resultDiv = document.getElementById('result-find-reviews-books');
            resultDiv.innerHTML = ''
            reviews.forEach(review => {
                const inputElement = document.createElement('input');
                const divHidden = document.createElement('div')
                divHidden.innerHTML = review.id
                divHidden.style.display = 'none';
                inputElement.type = 'text';
                inputElement.value = review.text;
                inputElement.style.width = '80%'
                inputElement.style.height = '100px'
                resultDiv.appendChild(inputElement);
                resultDiv.appendChild(divHidden);
                resultDiv.appendChild(document.createElement('br'));
            });

            // Add scrollbar
            resultDiv.style.overflow = 'auto';
            resultDiv.style.height = '200px';
            let buttonSendReq = resultDiv.appendChild(document.createElement('button'));
            buttonSendReq.className= 'btn btn_primary btn_outline'
            buttonSendReq.style.paddingLeft = '15%'
            buttonSendReq.innerHTML = 'Изменить комментарий'
            buttonSendReq.onclick = sendEditReview;
            resultDiv.appendChild(buttonSendReq)
        })
        .catch(error => alert(error));
}

function sendEditReview() {
    // Получите данные из полей ввода
    const inputs = document.querySelectorAll('#result-find-reviews-books input');
    const divHiddens = document.querySelectorAll('#result-find-reviews-books div');

    const reviewData = Array.from(inputs).map((input, index) => ({
        id: divHiddens[index].innerHTML,
        text: input.value
    }));

    // Создайте объект с данными для запроса
    const data = {
        reviews: reviewData
    };
    console.log(reviewData)

    // Отправьте POST-запрос на сервер
    fetch('http://localhost:8081/cms/book-review/editListComment/' + bookSlug, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            // Обработайте ответ от сервера
            console.log(data);
        })
        .catch(error => alert(error));
}

function findBookDelete(){
    const result = document.getElementById('delete-book')
    const input = document.getElementById('query-page-find-delete-book').value
    const url = `http://localhost:8081/api/books/find-by-name/${input}`;
    fetch(url, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {
            const bookLink = 'http://localhost:8081/books/' + data.slug
            result.innerHTML = `<div style="padding-top: 10%; display: block">
                                <img src="${data.image}"/>
                                <div id="book-find-author-add">
                                    <div style="padding-top: 30px; padding-bottom: 30px">Название: ${data.title}</div>
                                </div>
                                <a href="${bookLink}" target="_blank">Ссылка на книгу</a></br>
                                <div style="padding-top: 10%; padding-left: 30%">
                                    <button id="save-book-btn" class="btn btn_primary btn_outline" onclick="deleteBook()" >Удалить книгу</button>
                                </div>
                            </div>`;
            bookSlug = data.slug
        })
        .catch(error => alert(error));

}

function deleteBook(){
    const url = `http://localhost:8081/cms/books/delete/`+ bookSlug;
    fetch(url, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(data => {
            if(data.error){
                throw new Error(data.error)
            }
            alert('Книга была успешно добавлена!')
        })
        .catch(error => alert(error));
}


