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
          <div class="task-actions">
            <button onclick="toggleComplete(${task.id}, ${!task.completed})">
              ${task.completed ? "Desmarcar" : "Concluir"}
            </button>
            <button onclick="showEditForm(${task.id}, '${escapeHtml(task.title)}', '${escapeHtml(task.description)}')">
              ✏️ Editar
            </button>
            <button onclick="deleteTask(${task.id})">🗑 Excluir</button>
          </div>
          <div id="edit-form-${task.id}" style="display:none; margin-top:10px;">
            <input type="text" id="edit-title-${task.id}" value="${escapeHtml(task.title)}" placeholder="Título" />
            <input type="text" id="edit-description-${task.id}" value="${escapeHtml(task.description)}" placeholder="Descrição" />
            <button onclick="updateTask(${task.id})">Salvar</button>
            <button onclick="hideEditForm(${task.id})">Cancelar</button>
          </div>
        `;
        container.appendChild(div);
    });
}

function escapeHtml(text) {
    if (!text)
        return "";
    return text.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;").replace(/'/g, "&#039;");
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
    // Primeiro, buscar a tarefa atual para manter título e descrição
    const res = await fetch(`${API_URL}/${id}`);
    if (!res.ok)
        return alert("Erro ao buscar tarefa");

    const task = await res.json();

    // Atualizar só o campo completed
    await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            title: task.title,
            description: task.description,
            completed: status
        })
    });

    fetchTasks();
}

function showEditForm(id, title, description) {
    document.getElementById(`edit-form-${id}`).style.display = "block";
}

function hideEditForm(id) {
    document.getElementById(`edit-form-${id}`).style.display = "none";
}

async function updateTask(id) {
    const title = document.getElementById(`edit-title-${id}`).value;
    const description = document.getElementById(`edit-description-${id}`).value;

    if (!title.trim())
        return alert("Título obrigatório!");

    await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            title,
            description,
            completed: false
        })
    });

    hideEditForm(id);
    fetchTasks();
}

async function deleteTask(id) {
    await fetch(`${API_URL}/${id}`, {method: "DELETE"});
    fetchTasks();
}

fetchTasks();