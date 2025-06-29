# Java ToDo List API with Local JSON Persistence

This is a minimal and educational Java backend REST API for a ToDo List application, built entirely without frameworks.  
It features a functional HTML frontend and stores data persistently using a local JSON file (`tasks.json`).

The goal is to demonstrate backend skills using raw Java, including RESTful design, JSON handling, and frontend integration.

---

## Technologies Used

- Java 11+
- Maven
- Jackson (for JSON serialization/deserialization)
- com.sun.net.httpserver (built-in Java HTTP server)
- HTML + JavaScript (Vanilla)
- JSON file-based storage (no database)

---

## Features

### Backend (Java API)

- Full CRUD operations: Create, Read, Update, Delete
- Task completion toggle (mark as done/undone)
- JSON persistence using a local file
- REST API hosted at `http://localhost:8000`
- CORS enabled to allow frontend communication

### Frontend (HTML + JavaScript)

- Form for adding new tasks
- Task listing with completion status
- Inline editing of tasks
- Task deletion
- Simple and responsive interface
- Integrated directly with Java API using `fetch`

---

## How to Run the Application

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/java-todo-api.git
```
- Open terminal [Linux/macOS] or CMD [Windows] to run the command above.

### 2. Run the backend server
 
```bash
cd java-todo-api
```
```bash
mvn clean compile exec:java
```

- The server will start on:
```bash
http://localhost:8000
```
### 3. Run the Frontend (HTML Interface)
- Use a local server to avoid CORS issues. With Python installed, run:
```bash
cd frontend
```
```bash
python -m http.server 5500
```

### JSON Format Example

```json
{
  "title": "Learn Java",
  "description": "Study REST APIs with raw Java",
  "completed": false
}
```

### Data Persistence
All tasks are stored in a local tasks.json file located in the root of the project.
This file is automatically created if it does not exist, and is updated on every create, update, or delete operation.

No external database is required.

## Video Demo
[▶ Watch the demo on YouTube](https://youtu.be/F8H28J41K8M)
