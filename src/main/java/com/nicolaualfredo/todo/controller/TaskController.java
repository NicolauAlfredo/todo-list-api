/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolaualfredo.todo.model.Task;
import com.nicolaualfredo.todo.repository.TaskRepository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nicolaualfredo
 */
public class TaskController implements HttpHandler {

    private final TaskRepository repository = new TaskRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            URI uri = exchange.getRequestURI();
            String path = uri.getPath();
            String[] pathParts = path.split("/");

            // ✅ Tratamento CORS (preflight OPTIONS)
            if ("OPTIONS".equals(method)) {
                addCorsHeaders(exchange);
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // 🔀 Rotas REST
            if ("GET".equals(method) && pathParts.length == 2) {
                handleGetAll(exchange);
            } else if ("GET".equals(method) && pathParts.length == 3) {
                handleGetById(exchange, pathParts[2]);
            } else if ("POST".equals(method) && pathParts.length == 2) {
                handlePost(exchange);
            } else if ("PUT".equals(method) && pathParts.length == 3) {
                handlePut(exchange, pathParts[2]);
            } else if ("DELETE".equals(method) && pathParts.length == 3) {
                handleDelete(exchange, pathParts[2]);
            } else {
                addCorsHeaders(exchange);
                exchange.sendResponseHeaders(404, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGetAll(HttpExchange exchange) throws Exception {
        List<Task> tasks = repository.findAll();
        respondWithJson(exchange, 200, tasks);
    }

    private void handleGetById(HttpExchange exchange, String idStr) throws Exception {
        int id = Integer.parseInt(idStr);
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            respondWithJson(exchange, 200, task.get());
        } else {
            addCorsHeaders(exchange);
            exchange.sendResponseHeaders(404, -1);
        }
    }

    private void handlePost(HttpExchange exchange) throws Exception {
        InputStream is = exchange.getRequestBody();
        Task task = objectMapper.readValue(is, Task.class);
        Task created = repository.create(task);
        respondWithJson(exchange, 201, created);
    }

    private void handlePut(HttpExchange exchange, String idStr) throws Exception {
        int id = Integer.parseInt(idStr);
        InputStream is = exchange.getRequestBody();
        Task updatedTask = objectMapper.readValue(is, Task.class);
        boolean updated = repository.update(id, updatedTask);
        addCorsHeaders(exchange);
        if (updated) {
            exchange.sendResponseHeaders(204, -1);
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }

    private void handleDelete(HttpExchange exchange, String idStr) throws Exception {
        int id = Integer.parseInt(idStr);
        boolean deleted = repository.delete(id);
        addCorsHeaders(exchange);
        if (deleted) {
            exchange.sendResponseHeaders(204, -1);
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }

    private void respondWithJson(HttpExchange exchange, int statusCode, Object data) throws Exception {
        addCorsHeaders(exchange);
        String response = objectMapper.writeValueAsString(data);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    // ✅ Função para adicionar os headers CORS
    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }
}
