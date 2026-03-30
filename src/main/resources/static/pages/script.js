const SERVER_URL = "http://localhost:2026";
const token = localStorage.getItem("token");


// ================= LOGIN =================
function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Login failed");
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem("token", data.token);
        window.location.href = "/pages/todo.html";
    })
    .catch(error => {
        alert(error.message);
    });
}



// ================= REGISTER =================
function register() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Registration failed");
        }
        alert("Registered successfully! Please login.");
        window.location.href = "login.html";
    })
    .catch(error => {
        alert(error.message);
    });
}



// ================= CREATE TODO CARD =================
function createTodoCard(todo) {

    const card = document.createElement("div");
    card.className = "todo-card";

    const checkBox = document.createElement("input");
    checkBox.type = "checkbox";
    checkBox.checked = todo.completed;

    checkBox.addEventListener("change", function () {
        const updatedTodo = { ...todo, isCompleted: checkBox.checked };
        updateTodo(updatedTodo);
    });


    const span = document.createElement("span");
    span.textContent = todo.name;

    if (todo.completed) {
        span.style.textDecoration = "line-through";
        span.style.color = "#aaa";
    }


    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "X";
    deleteBtn.onclick = function () {
        deleteTodo(todo.id);
    };


    card.append(checkBox);
    card.append(span);
    card.append(deleteBtn);

    return card;
}



// ================= DELETE TODO =================
function deleteTodo(id) {

    fetch(`${SERVER_URL}/todo/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to delete todo");
        }
        return response.text();
    })
    .then(() => loadTodos())
    .catch(error => {
        alert(error.message);
    });
}



// ================= UPDATE TODO =================
function updateTodo(todo) {

    fetch(`${SERVER_URL}/todo/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(todo)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to update todo");
        }
        return response.json();
    })
    .then(() => loadTodos())
    .catch(error => {
        alert(error.message);
    });
}



// ================= ADD TODO =================
function addTodo() {
    const token = localStorage.getItem("token");
    const input = document.getElementById("new-todo");
    const todoText = input.value.trim();

    if (!todoText) return;

    fetch(`${SERVER_URL}/todo/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({
            name: todoText,
            isCompleted: false
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to add todo");
        }
        return response.json();
    })
    .then(() => {
        input.value = "";
        loadTodos();
    })
    .catch(error => {
        alert(error.message);
    });
}



// ================= LOAD TODOS =================
function loadTodos() {

    if (!token) {
        alert("Please login first");
        window.location.href = "/pages/login.html";
        return;
    }

    fetch(`${SERVER_URL}/todo/getall`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to get todos");
        }
        return response.json();
    })
    .then(todos => {

        const todoList = document.getElementById("todo-list");
        todoList.innerHTML = "";

        if (!todos || todos.length === 0) {
            todoList.innerHTML = `<p id="empty-message">No todos yet. Add one below!</p>`;
        }
        else {
            todos.forEach(todo => {
                todoList.appendChild(createTodoCard(todo));
            });
        }

    })
    .catch(() => {
        document.getElementById("todo-list").innerHTML =
            `<p style="color:red">Failed to load Todos!</p>`;
    });
}



// ================= AUTO LOAD =================
document.addEventListener("DOMContentLoaded", function () {

    if (document.getElementById("todo-list")) {
        loadTodos();
    }

});