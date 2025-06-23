/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


const API_URL = "http://localhost:8000/tasks";

async function fetchTasks() {
    const res = await fetch(API_URL);
    const tasks = await res.json();
    const container = document.getElementById("tasks");
    container.innerHTML = "";

    tasks.forEach(task => {
        const div = document.createElement("div");
        div.className = "task";
        if (task.completed)
            div.classList.add("done");

        div.innerHTML = `
                <strong>${task.title}</strong><br>
                <small>${task.description}</small><br>
                <button onclick="toggleComplete(${task.id}, ${!task.completed})">
                  ${task.completed ? "Desmarcar" : "Concluir"}
                </button>
                <button onclick="deleteTask(${task.id})">🗑 Excluir</button>
              `;
        container.appendChild(div);
    });
}

async function addTask() {
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    if (!title.trim())
        return alert("Título obrigatório!");

    await fetch(API_URL, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({title, description, completed: false})
    });

    document.getElementById("title").value = "";
    document.getElementById("description").value = "";
    fetchTasks();
}

async function toggleComplete(id, status) {
    await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({title: "temp", description: "", completed: status}) // será corrigido abaixo
    });

    fetch(`${API_URL}/${id}`)
            .then(res => res.json())
            .then(task => {
                return fetch(`${API_URL}/${id}`, {
                    method: "PUT",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                        title: task.title,
                        description: task.description,
                        completed: status
                    })
                }).then(fetchTasks);
            });
}

async function deleteTask(id) {
    await fetch(`${API_URL}/${id}`, {method: "DELETE"});
    fetchTasks();
}

fetchTasks();